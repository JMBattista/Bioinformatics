package com.vitreoussoftware.bioinformatics.alignment.suffixtree

import com.vitreoussoftware.bioinformatics.alignment.TextFirstAlignerDescribedTest

/**
  * Created by John on 8/24/14.
  */
abstract class SuffixTreeTest(val anAligner: String) extends TextFirstAlignerDescribedTest(anAligner) {
  describe("A SuffixTree") {
    describe("of type" + anAligner) {
      describe("when used with walkers") {
        it("will behave the same between reference contains and walkers provided contains") {
          aligner => {

          }
        }

        it("will behave the same between reference alignments and walkers provided alignments") {
          aligner => {

          }
        }

        it("will behave the same between reference shortestDistance and walkers provided shortestDistance") {
          aligner => {

          }
        }

        it("will behave the same between reference distances and walkers provided distances") {
          aligner => {

          }
        }
      }
    }
  }
}
