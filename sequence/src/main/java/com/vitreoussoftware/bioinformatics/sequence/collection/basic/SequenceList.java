package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import java.util.ArrayList;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;

/**
 * A List based SequenceCollection intended for testing and prototyping.
 * This collection is not safe for use with large data sets.
 * @author John
 *
 */
public class SequenceList extends ArrayList<Sequence> implements SequenceCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9119483062067567584L;
	
	SequenceList() {
		super();
	}
}
