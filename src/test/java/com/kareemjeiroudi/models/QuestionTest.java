package com.kareemjeiroudi.models;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Random;


public class QuestionTest {

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

    @Test(expected = InvalidQuestionException.class)
    public void ConstructorThrowsInvalidQuestionExceptionWhenMaxLengthIsExceeded() throws InvalidQuestionException {
        // generate a randomString whose minimum length is the maximum allowed length to a question
        String randomString = randomString(Question.MAX_LENGTH, 1000);
        new Question(randomString);
    }

    @Test
    public void ConstructorWorksGivenOkayStringLength() throws InvalidQuestionException {
        // generate a randomString whose minimum length is the maximum allowed length to a question
        String randomString = randomString(0, Question.MAX_LENGTH);
        Question question = new Question(randomString);
        assertThat(question, instanceOf(Question.class)); // more readable, less specific
        assertEquals(Question.class, question.getClass()); // less readable, more specific
    }

}
