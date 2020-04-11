package com.kareemjeiroudi.models;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;


public class AnswerTest {

    /**
     * Generates a random string of arbitrary length containing only ASCII printable characters (i.e. between the
     * 32nd and 126th character)
     * @param  minLength minimum length of generated length. Negative values are not allowed.
     * @param  maxLength maximum length of generated length.
     * @throws IllegalArgumentException if:
     * <li> <code>maxLength</code> is smaller or equal to <code>minLength</code>
     * <li> <code>minLength</code> is negative
     * @return a random string with ASCII printable characters.
     */
    private String randomString(int minLength, int maxLength) throws IllegalArgumentException {
        // TODO: this method could be tested too
        if (maxLength <= minLength || minLength < 0) {
            throw new IllegalArgumentException("maxLength must be greater than minLength");
        }
        int leftLimit = 32; // space
        int rightLimit = 126; // letter '~'
        Random random = new Random();
        int stringLength = random.ints(minLength, maxLength+1).findFirst().getAsInt();

        return random.ints(leftLimit, rightLimit + 1)
            .limit(stringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void ConstructorThrowsInvalidAnswerExceptionWhenMaxLengthIsExceeded() throws InvalidAnswerException {
        // generate a randomString whose minimum length is the maximum allowed length to an answer
        expectedEx.expect(InvalidAnswerException.class);
        expectedEx.expectMessage(String.format("Answer is too long! max length %d", Answer.MAX_LENGTH));
        String randomString = randomString(Answer.MAX_LENGTH, 1000);
        new Answer(randomString);
        randomString = randomString(Answer.MAX_LENGTH, 1000);
        new Answer(randomString);
    }

    @Test
    public void ConstructorWorksGivenOkayStringLength() throws InvalidAnswerException {
        // generate a randomString whose minimum length is the maximum allowed length to an answer
        String randomString = randomString(0, Answer.MAX_LENGTH);
        Answer answer = new Answer(randomString);
        assertThat(answer, instanceOf(Answer.class)); // more readable, less specific
        assertEquals(Answer.class, answer.getClass()); // less readable, more specific
    }

}
