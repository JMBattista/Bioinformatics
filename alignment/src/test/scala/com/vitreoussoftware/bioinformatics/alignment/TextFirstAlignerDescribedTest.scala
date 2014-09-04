package com.vitreoussoftware.bioinformatics.alignment

import org.scalatest.{Outcome, fixture}
import com.vitreoussoftware.test.BehaviorSpec
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection
import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util


/**
 * Created by John on 8/31/14.
 */
@RunWith(classOf[JUnitRunner])
abstract class TextFirstAlignerDescribedTest(anAligner: String) extends BehaviorSpec with AlignerTestData with AlignerHelpers {
  type FixtureParam = TextFirstAligner

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
