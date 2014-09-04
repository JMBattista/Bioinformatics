package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic

import com.vitreoussoftware.bioinformatics.alignment.TextFirstAlignerDescribedTest

/**
  * Created by John on 8/24/14.
  */
class BoundedSuffixTreeFTest extends TextFirstAlignerDescribedTest("BoundedSuffixTree") {
   def getAligner() = new BoundedSuffixTreeFactory(0, 200) create
}
