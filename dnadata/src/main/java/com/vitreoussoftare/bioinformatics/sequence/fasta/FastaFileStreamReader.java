package com.vitreoussoftare.bioinformatics.sequence.fasta;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * File stream reader for FASTA data files
 * @author John
 *
 */
public final class FastaFileStreamReader implements AutoCloseable 
{
	private static final int BUFFER_SIZE = 64 * 1024; // 64 KB read size
	
	/**
	 * The FASTA file
	 */
	private FileReader file;
	private char[] buffer;
	private int length;
	private int index;
	private boolean readingSequence;

	/**
	 * Create a FASTA File Stream Reader for the given file
	 * @param file the file to run on
	 */
	private FastaFileStreamReader(FileReader file)
	{
		this.file = file;
		
		buffer = new char[BUFFER_SIZE];
		length = 0;  // we don't have anything in the buffer
		index = 0;   // index starts at 0
		readingSequence = false; // we start with reading meta data, not sequence

		try (FileReader f = new FileReader("bob")){
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Reads a record from the file
	 * @return the record
	 * @throws IOException something went wrong reading from the file 
	 */
	public String readRecord() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		do {
			// if we are out of buffered data read more
			if (index >= length)
			{
				bufferData();
			}
			
			// if we are not reading a sequence we are reading metadata. This is necessary in case we wrap around because the buffer empties.
			if (!readingSequence)
			{
				// spin until we find the end of the heading row
				while (index < length && buffer[index] != ';')
					index++;
				// move cursor past the end of the heading row ';'
				index++;
				readingSequence = true;
			}
			
			// spin until we are out of white space
			while (index < length && Character.isWhitespace(buffer[index]))
				index++;
			
			// once we find the start of the sequence read it into the buffer
			while (index < length && buffer[index] != '>')
			{
				if (! Character.isWhitespace(buffer[index]))
					sb.append(buffer[index]);
				index++;
			}
			
			if (index < length && buffer[index] == '>')
			{
				readingSequence = false;
			}
		} while (length > 0 && (readingSequence || index >= length));
		
		return  sb.toString();
	}

	private void bufferData() throws IOException {
		length = file.read(buffer);
		index = 0;
	}
	
	/**
	 * Create an input stream for FASTA file format
	 * @param fileName the FASTA file
	 * @return the input stream
	 * @throws FileNotFoundException the specified file was not found
	 */
	public static FastaFileStreamReader create(String fileName) throws FileNotFoundException
	{
		FileReader file = new FileReader(fileName);
		return new FastaFileStreamReader(file);
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.file.close();
		super.finalize();
	}

	/** 
	 * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws IOException If the file cannot be accessed it may fail
	 */
	public boolean hasRecord() throws IOException {
		// if we know we have data remaining say so
		if (length > 0 && index < length)
			return true;
		// if we don't know find out
		bufferData();
		
		return length > 0 && index < length;
	}

	@Override
	public void close() throws IOException {
		this.file.close();
	}
}
