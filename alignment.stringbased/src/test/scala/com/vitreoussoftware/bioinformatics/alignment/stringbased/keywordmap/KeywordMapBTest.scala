package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap

import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAlignerBaseTest

/**
  * Created by John on 8/24/14.
  */
class KeywordMapBTest extends PatternFirstAlignerBaseTest("KeywordMap") {
   def getAligner() = new KeywordMapFactory create
}
