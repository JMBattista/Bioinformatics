package com.vitreoussoftware.bioinformatics.alignment

import com.vitreoussoftware.test.BehaviorSpec
import htsjdk.samtools.SAMRecord
import org.scalatest.Outcome

/**
 * Created by John on 10/5/14.
 */
public abstract class AlignmentTest extends BehaviorSpec {
  describe("Alignment") {
    it("can be converted to a SAMRecord") {
      () => {
        0 should be(0);
      }
    }
  }
}
