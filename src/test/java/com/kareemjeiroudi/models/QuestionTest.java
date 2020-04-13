package com.kareemjeiroudi.models;


import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void constructorThrowsInvalidQuestionExceptionWhenMaxLengthIsExceeded() throws InvalidQuestionException {
        // generate a randomString whose length is between min and max allowed length (255) to a question
        expectedEx.expect(InvalidQuestionException.class);
        expectedEx.expectMessage(String.format("Question is too long! Max length is %d", Question.MAX_LENGTH));
        String randomString = randomString(Question.MAX_LENGTH, 1000);
        new Question(randomString);
        randomString = randomString(Answer.MAX_LENGTH, 1000);
        new Question(randomString);
    }

    @Test
    public void constructorThrowsInvalidQuestionExceptionWhenQuestionStatementIsBlank() throws InvalidQuestionException {
        expectedEx.expect(InvalidQuestionException.class);
        expectedEx.expectMessage("Questions must contain at least one character!");
        String questionStatement = "";
        new Question(questionStatement);
    }

    @Test
    public void constructorThrowsInvalidQuestionExceptionWhenQuestionStatementIsEmpty() throws InvalidQuestionException {
        expectedEx.expect(InvalidQuestionException.class);
        expectedEx.expectMessage("Questions must contain at least one character!");
        String questionStatement = "   ";
        new Question(questionStatement);
    }

    @Test
    public void constructorWorksGivenStringOfArbitraryLengthBetweenMinAndMax() throws InvalidQuestionException {
        // generate a randomString whose length is between 1 and max allowed length (255)
        String randomString = randomString(1, Question.MAX_LENGTH);
        Question question = new Question(randomString);
        assertEquals(Question.class, question.getClass());
        assertEquals(question.toString(), randomString);

        randomString = randomString(1, Answer.MAX_LENGTH);
        question = new Question(randomString);
        assertEquals(Question.class, question.getClass());
        assertEquals(question.toString(), randomString);
    }

}
