package com.vitreoussoftware.bioinformatics.alignment

import org.scalatest.{Outcome, fixture}
import com.vitreoussoftware.test.BehaviorSpec
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection
import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util
import org.scalatest.prop.PropertyChecks
import com.vitreoussoftware.bioinformatics.sequence.{BasePair, Sequence}
import org.scalacheck.{Gen, Arbitrary}
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme


/**
 * Created by John on 8/31/14.
 */
@RunWith(classOf[JUnitRunner])
abstract class TextFirstAlignerDescribedTest(anAligner: String) extends BehaviorSpec with PropertyChecks
  with AlignerTestData with AlignerHelpers {
  type FixtureParam = TextFirstAligner

  def genSequence(): Gen[Sequence] = {
    val genBasePair = for(bp <- Gen.frequency(
      (1, "A"),
      (1, "U"),
      (1, "C"),
      (1, "G")
    )) yield bp

    def makeSeq(seq: List[String]) = {
      var builder = new StringBuilder
      seq.foreach(bp => builder = builder append bp)
      BasicSequence.create(builder.toString(), AcceptUnknownDnaEncodingScheme.instance).get()
    }

    def genSeq(len: Int): Gen[Sequence] = {
      for {seq <- Gen.listOfN(len, genBasePair)} yield makeSeq(seq)
    }

    def sizedSequence(len: Int) = {
      var length = len % 20
      if (len < 0)
        length = -1 * len
      if (length == 0)
        length = 1
      genSeq(length)
    }

    Gen.sized(sz => sizedSequence(sz))
  }

  val genBasePair = for(bp <- Gen.frequency(
    (1, 'A'),
    (1, 'U'),
    (1, 'C'),
    (1, 'G')
  )) yield BasePair.create(bp, AcceptUnknownDnaEncodingScheme.instance)

  implicit lazy val arbBasePair: Arbitrary[BasePair] = Arbitrary(genBasePair)
  implicit lazy val arbSequence: Arbitrary[Sequence] = Arbitrary(genSequence)

  override def withFixture(test: OneArgTest) = {
    // create the fixture
    val aligner = getAligner()
    val outcome: Outcome = try {
      withFixture(test.toNoArgTest(aligner)) // "loan" the fixture to the test
    }
    destroyAligner(aligner)
    outcome
  }

  describe(anAligner) {
    describe("that is empty") {
      it("should not contains any patterns") {
        aligner => {
          forAll(baseSeqs) { (seq) => {
            aligner contains seq shouldBe false
          }}

          forAll(aligner contains SequenceCollection.from(baseSeqs)) { result => {
            result.getValue1 shouldBe false
          }}
        }
      }

      it("should not have any alignments") {
        aligner => {
          def assert(x: util.Collection[Alignment]) = {
            x should have size 0
          }

          forAll(baseSeqs) { (seq) => {
            assert(aligner getAlignments seq)
          }}

          forAll(aligner getAlignments SequenceCollection.from(baseSeqs)) { result => {
            assert(result.getValue1)
          }}
        }
      }
    }

    describe("populated with") {

      // Tests for Sequence Simple
      describe("seqSimple") {
        describe("when asked about containment") {
          it("should find the single base sequences") {
            aligner => {
              aligner addSequence seqSimple
              forAll(baseSeqs) { (seq) => {
                aligner contains seq shouldBe true
              }}

              forAll(aligner contains SequenceCollection.from(baseSeqs)) { result => {
                result.getValue1 shouldBe true
              }}
            }
          }
        }
        describe("when asked for shortest distance") {
          it("should work for sequences off by a value of N") {
            aligner => {
              aligner addSequence seqSimple
              def assert(x: util.Collection[Alignment]) = {
                x.map(a => a.getDistance).forall(d => d == 1) shouldBe true
              }

              forAll(offByNSeqs) { (seq) => {
                val alignments = aligner shortestDistance seq
                assert(alignments)
              }}

              forAll(aligner shortestDistance SequenceCollection.from(offByNSeqs)) { result => {
                assert(result.getValue1)
              }}

            }
          }

          it("should work for long sequences off by a value of N") {
            aligner => {
              aligner addSequence seqSimple
              def assert(x: util.Collection[Alignment]) = {
                x.map(a => a.getDistance()).toList should contain only 6
              }

              forAll(offByNSeqsLong) { (seq) => {
                val alignments = aligner shortestDistance seq
                assert(alignments)
              }}

              forAll(aligner shortestDistance SequenceCollection.from(offByNSeqsLong)) { result => {
                assert(result.getValue1)
              }}

            }
          }

          it("should work for GCUUAGGUAAGACCCAUA") {
            aligner => {
              aligner addSequence seqSimple

              aligner.shortestDistance(BasicSequence.create("GCUUAGGUAAGACCCAUA", AcceptUnknownDnaEncodingScheme.instance).get()).size should be >0
            }
          }

          it("should work for generated sequence") {
            aligner => {
              aligner addSequence seqSimple

              forAll { (seq: Sequence) =>
                whenever (seq.length() > 0 && seq.length() < seqSimple.length()) {
                  val contained = aligner contains seq
                  val alignments = aligner getAlignments seq
                  val shortestDistanceAlignments = aligner shortestDistance seq

                  if (contained) {
                    alignments.size should be > 0
                    alignments.map(a => a.getDistance) should contain only 0
                    alignments.map(a => a.getSequence) should contain only seqSimple
                    val numAlignments = alignments.map(a => a.getPosition).toSet.size
                    alignments.map(a => a.getPosition).size should be (numAlignments)

                    shortestDistanceAlignments should contain theSameElementsAs alignments
                  }
                  else
                  {
                    alignments.size should be(0)
                    shortestDistanceAlignments.size should be >0

                    val dist = shortestDistanceAlignments.head.getDistance
                    shortestDistanceAlignments.map(a => a.getDistance) should contain only dist
                    shortestDistanceAlignments.map(a => a.getSequence) should contain only seqSimple
                    shortestDistanceAlignments.size should be (shortestDistanceAlignments.toSet.size)
                  }

                  forAll(alignments) { alignment => {
                    checkDistance(seq, alignment) should be(alignment.getDistance)
                  }}

                  forAll(shortestDistanceAlignments) { alignment => {
                    checkDistance(seq, alignment) should be(alignment.getDistance)
                  }}
                }
              }
            }
          }
        }
      }

      // Tests for Sequence Record1
      describe("seqRecord1") {

      }

      // Tests for Sequence Record1 and Simple
      describe("seqSimple and seqRecord1") {

      }
    }
  }
}
