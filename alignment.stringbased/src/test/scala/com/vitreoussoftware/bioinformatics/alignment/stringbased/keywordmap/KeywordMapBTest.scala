package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap

import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAlignerDescribedTest


/**
  * Created by John on 8/24/14.
  */
class KeywordMapBTest extends PatternFirstAlignerDescribedTest("KeywordMap") {
  def getAligner() = new KeywordMapFactory create
}
