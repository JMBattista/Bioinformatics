package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.NonNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * File stream reader for EMBL data files
 * @author John
 *
 */
public final class BufferFileStreamReader implements AutoCloseable
{
	private static final int DEFAULT_BUFFER_SIZE = 64 * 1024; // 64 KB read size

	private FileReader file;
	private char[] buffer;
	private int length;
	private int index;

	/**
	 * Create a {@link BufferFileStreamReader} for a given file with the specified
	 * @param filePath The path to the file to read
     * @param bufferSize Optionally set the size of the buffer
	 */
	@Builder
	private BufferFileStreamReader(@NonNull String filePath, int bufferSize) throws FileNotFoundException {
        Preconditions.checkArgument(bufferSize > 0, "bufferSize must be a positive integer");

        file = new FileReader(filePath);
		buffer = new char[bufferSize];
		length = 0;  // we don't have anything in the buffer
		index = 0;   // index starts at 0
	}

    /**
     * Builder instance with default value for buffer size
     */
    @SuppressWarnings("unused") // Lombok @Builder
    public static class BufferFileStreamReaderBuilder {
        private int bufferSize = DEFAULT_BUFFER_SIZE;
    }



    /**
     * Returns if the End of File has been reached, indicating no more data can be read
     *
     * @return true if End of File reached, false otherwise
     */
    public boolean isEof() {
        if (length == -1) // We've reached EoF
            return true;
        if (index < length) // We haven't reached EoF
            return false;

        // We don't know yet
        return bufferData() == -1;
    }

    /**
     * Does the current position satisfy the predicate?
     *
     * @param predicate The {@link Predicate} to test
     *
     * @return true if the predicate matches, false if it does not OR eof {@see isEof}
     */
    public boolean is(@NonNull final Predicate<Character> predicate) {
        if (index >= length)
            bufferData();
        return index < length && predicate.test(buffer[index]);
    }

    /**
     * Does the current position match the {@see character}
     * @param character the character to compare with
     *
     * @return true if they match, false if they do not OR eof {@see isEof}
     */
    public boolean is(char character) {
        if (index >= length)
            bufferData();
        return index < length && character == buffer[index];
    }

    /**
     * Ignore characters in the buffer until the {@link String} {@see flag} is found
     *
     * @param flag The {@link String} to search for
     */
    public void dropUntil(String flag) {
        final int maxPosition = flag.length();
        int position = 0;

        while (position != maxPosition) {
            if (index >= length)
                bufferData();
            if (length == -1)
                return;
            if (buffer[index] == flag.charAt(position)) {
                position++;
            }
            else {
                position = 0;
            }
            index++;
        }
    }

    /**
     * Ignore characters in the buffer until the {@link Predicate} {@see predicate} evaluates to true
     * @param predicate The {@link Predicate} to test
     */
    public void dropUntil(Predicate<Character> predicate) {
        while (true) {
            if (index >= length)
                bufferData();
            if (length == -1 || predicate.test(buffer[index]))
                return;

            index++;
        }
    }

    /**
     * Ignore characters in the buffer while they are whitespace
     */
    public void dropWhileWhitespace() {
        while (true) {
            if (index >= length)
                bufferData();
            if (length == -1 || !Character.isWhitespace(buffer[index]))
                return;

            index++;
        }
    }

    /**
     * Ignore characters in the buffer until they are whitespace
     */
    public void dropUntilWhiteSpace() {
        while (true) {
            if (index >= length)
                bufferData();
            if (length == -1 || Character.isWhitespace(buffer[index]))
                return;

            index++;
        }
    }

    /**
     * Ignore the next character in the buffer
     * @return How many characters were dropped
     */
    public int drop() {
        return drop(1);
    }

    /**
     * Ignore the next {@see characters} characters in the buffer
     * @param characters The number of characters to drop
     * @return How many characters were dropped
     */
    public int drop(int characters) {
        index += characters;

        return characters;
    }

    /**
     * Take characters from the buffer until the {@link Predicate} {@see predicate} is found [start, predicate)
     * @param predicate The {@link Predicate} to test
     *
     * @return The characters taken from the buffer
     */
    public String takeUntil(Predicate<Character> predicate) {
        StringBuilder sb = new StringBuilder();

        boolean found = false;
        while (!found) {
            if (index >= length)
                bufferData();
            if (length == -1 || predicate.test(buffer[index]))
                found = true;
            else
                sb.append(buffer[index]);
            index++;
        }

        return sb.toString();
    }

    /**
     * Take characters from the buffer until a whitespace character is found
     *
     * @return The characters taken from the buffer
     */
    public String takeUntilWhitespace() {
        StringBuilder sb = new StringBuilder();

        boolean found = false;
        while (!found) {
            if (index >= length)
                bufferData();
            if (length == -1 || Character.isWhitespace(buffer[index]))
                found = true;
            else
                sb.append(buffer[index]);
            index++;
        }

        return sb.toString();
    }

    /**
     * Refresh the buffer with new data from the source file. This method will block until some input
     * is available, an I/O error occurs, or the end of the stream is reached.
     *
     * @return The number of characters read, or -1 if EOF
     */
    private int bufferData() {
        try {
            index = 0;
            length = file.read(buffer);
            return length;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to read from file");
        }
    }

    @Override
    public void finalize() throws Throwable {
        this.file.close();
        super.finalize();
    }

    @Override
    public void close() throws IOException {
        this.file.close();
    }
}
