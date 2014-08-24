package com.vitreoussoftware.bioinformatics.alginment.suffixtree.basic

import com.vitreoussoftware.bioinformatics.alignment.TextFirstAlignerBaseTest
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic.BoundedSuffixTreeFactory

/**
 * Created by John on 8/24/14.
 */
class BoundedSuffixTreeBTest extends TextFirstAlignerBaseTest("BoundedSuffixTree") {
  def getAligner() = new BoundedSuffixTreeFactory(0, 200).create()
}
