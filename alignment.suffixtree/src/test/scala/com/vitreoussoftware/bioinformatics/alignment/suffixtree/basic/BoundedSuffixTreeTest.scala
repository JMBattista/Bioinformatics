package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeTest

/**
  * Created by John on 8/24/14.
  */
class BoundedSuffixTreeTest extends SuffixTreeTest("BoundedSuffixTree") {
  def getAligner() = new BoundedSuffixTreeFactory(0, 200) create
}
