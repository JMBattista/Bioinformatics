package com.vitreoussoftware.bioinformatics.alignment.suffixtree

import com.vitreoussoftware.bioinformatics.alignment.{Alignment, TextFirstAlignerDescribedTest}
import com.vitreoussoftware.bioinformatics.sequence.Sequence
import scala.collection.JavaConversions._

/**
  * Created by John on 8/24/14.
  */
abstract class SuffixTreeTest(val anAligner: String) extends TextFirstAlignerDescribedTest(anAligner) {

  describe("A SuffixTree") {
    describe("of type " + anAligner) {
      describe("when used with walkers") {
        it("will behave the same between reference contains and walkers provided contains") {
          aligner => {
            aligner addText seqSimple
            val tree: SuffixTree = aligner.asInstanceOf[SuffixTree]

            forAll { (pattern: Sequence) =>
              whenever (pattern.length() > 0 && pattern.length() < seqSimple.length()) {
                val result: Boolean = tree.walk(Walkers.contains(pattern))
                val expected: Boolean = aligner.contains(pattern)
                result should be(expected)
              }
            }
          }
        }

        it("will behave the same between reference shortestDistance and walkers provided shortestDistance") {
          aligner => {
            aligner addText seqSimple
            val tree: SuffixTree = aligner.asInstanceOf[SuffixTree]

            forAll { (pattern: Sequence) =>
              whenever (pattern.length() > 0 && pattern.length() < seqSimple.length()) {
                val result = tree.walk(Walkers.shortestDistances(pattern))
                val expected = aligner.shortestDistance(pattern)

                result should contain theSameElementsAs(expected)
              }
            }
          }
        }
      }
    }
  }
}
