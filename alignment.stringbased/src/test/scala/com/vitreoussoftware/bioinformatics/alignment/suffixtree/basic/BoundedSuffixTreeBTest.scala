package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic

import com.vitreoussoftware.bioinformatics.alignment.{TextFirstAligner, TextFirstAlignerBaseTest}

/**
  * Created by John on 8/24/14.
  */
class BoundedSuffixTreeBTest extends TextFirstAlignerBaseTest("BoundedSuffixTree") {
   def getAligner() = new BoundedSuffixTreeFactory(0, 200).create()
}
