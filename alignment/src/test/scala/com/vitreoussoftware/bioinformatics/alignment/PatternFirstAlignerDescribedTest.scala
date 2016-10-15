package com.vitreoussoftware.bioinformatics.alignment

import org.scalatest.Outcome
import com.vitreoussoftware.test.BehaviorSpec
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection
import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util
import org.scalatest.prop.PropertyChecks
import com.vitreoussoftware.bioinformatics.sequence.Sequence
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme

/**
 * Created by John on 8/31/14.
 */
@RunWith(classOf[JUnitRunner])
abstract class PatternFirstAlignerDescribedTest(anAligner: String) extends BehaviorSpec with PropertyChecks
  with AlignerTestData {
  type FixtureParam = PatternFirstAligner

  /**
   * Perform any setup necessary to create and return the aligner for use in tests
   * @return the aligner to use for the tests
   */
  def  getAligner() : PatternFirstAligner

  def destroyAligner(aligner: PatternFirstAligner) = None

  override def withFixture(test: OneArgTest) = {
    // create the fixture
    val aligner = getAligner()
    val outcome: Outcome = withFixture(test.toNoArgTest(aligner)) // "loan" the fixture to the test
    destroyAligner(aligner)
    outcome
  }

  describe(anAligner) {
    describe("that is empty") {
      it("should not be contained by a text") {
        aligner => {
          forAll(baseSeqs) { (seq) => {
            aligner contained seq shouldBe false
          }}

          forAll(aligner contained SequenceCollection.from(baseSeqs)) { result => {
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

    describe("tested with") {

      // Tests for Sequence Simple
      describe("seqSimple") {
        describe("when asked about containment") {
          it("should report true when build with singe base pair patterns") {
            aligner => {

              forAll(baseSeqs) { (seq) => {
                aligner addPattern seq
              }}

              aligner contained seqSimple shouldBe true
            }
          }
        }
        describe("when asked for shortest distance") {
          it("should work for sequences off by a value of N") {
            aligner => {
              forAll(offByNSeqs) { (seq) => {
                aligner addPattern seq
              }}

              val alignments = aligner shortestDistance seqSimple
              alignments.map(a => a.getText) should contain only seqSimple
              alignments.map(a => a.getDistance) should contain only 1
              alignments.map(a => a.getPattern).toSet should contain theSameElementsAs offByNSeqs

              forAll(alignments) { alignment => {
                checkDistance(alignment) should be(alignment.getDistance)
              }}
            }
          }

          it("should work for long sequences off by a value of N") {
            aligner => {
              forAll(offByNSeqsLong) { (seq) => {
                aligner addPattern seq
              }}

              val alignments = aligner shortestDistance seqSimple
              alignments.map(a => a.getText) should contain only seqSimple
              alignments.map(a => a.getDistance) should contain only 6
              alignments.map(a => a.getPattern).toSet should contain theSameElementsAs offByNSeqsLong

              forAll(alignments) { alignment => {
                checkDistance(alignment) should be(alignment.getDistance)
              }}
            }
          }
        }

        it("should work for generated sequence") {
          unused => {
            forAll { (pattern: Sequence) =>
              whenever (pattern.length() > 0 && pattern.length() < seqSimple.length()) {
                val aligner = getAligner()
                aligner addPattern pattern

                val contained = aligner contained seqSimple
                val alignments = aligner getAlignments seqSimple
                val shortDistanceAlignments = aligner shortestDistance seqSimple
                val allAlignments = aligner distances seqSimple

                if (contained) {
                  alignments.size should be > 0
                  alignments.map(a => a.getDistance) should contain only 0
                  alignments.map(a => a.getText) should contain only  seqSimple
                  alignments.map(a => a.getPattern) should contain only pattern

                  val numAlignments = alignments.map(a => a.getPosition).toSet.size
                  alignments.map(a => a.getPosition).size should be (numAlignments)

                  shortDistanceAlignments should contain theSameElementsAs alignments
                  allAlignments.filter(a => a.getDistance == 0) should contain theSameElementsAs alignments
                }
                else
                {
                  alignments.size should be (0)
                  shortDistanceAlignments.size should be >0
                  allAlignments.size should be >0

                  val dist = shortDistanceAlignments.head.getDistance
                  shortDistanceAlignments.map(a => a.getDistance) should contain only dist
                  shortDistanceAlignments.map(a => a.getText) should contain only seqSimple
                  shortDistanceAlignments.map(a => a.getPattern) should contain only pattern
                  shortDistanceAlignments.size should be (shortDistanceAlignments.toSet.size)
                  allAlignments.filter(a => a.getDistance == dist) should contain theSameElementsAs shortDistanceAlignments

                  allAlignments.map(a => a.getText) should contain only seqSimple
                  allAlignments.map(a => a.getPattern) should contain only pattern
                }

                forAll(alignments) { alignment => {
                  checkDistance(alignment) should be(alignment.getDistance)
                }}

                forAll(shortDistanceAlignments) { alignment => {
                  checkDistance(alignment) should be(alignment.getDistance)
                }}

                forAll(allAlignments) { alignment => {
                  checkDistance(alignment) should be(alignment.getDistance)
                }}
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
