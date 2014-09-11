package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap;

import com.vitreoussoftware.bioinformatics.alignment.stringbased.StringBasedAlignerFactory;

/**
 * Created by John on 8/27/14.
 */
public class KeywordMapFactory implements StringBasedAlignerFactory {
    public KeywordMap create() {
        return new KeywordMap();
    }
}
