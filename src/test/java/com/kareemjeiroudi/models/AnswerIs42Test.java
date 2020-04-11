package com.kareemjeiroudi.models;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

import com.kareemjeiroudi.models.AnswerIs42;
import com.kareemjeiroudi.models.InvalidQuestionException;
import org.junit.Test;
import org.junit.Before;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AnswerIs42Test {

    private AnswerIs42 ai42;

    @Before
    public void init() {
     ai42 = new AnswerIs42();
    }

    @Test
    public void processInputReturnsOneElementArrayWhenInputHasTrailingOrLeadingWhitespaces() { }


    /**
     * processInput should return an array of type String[] of size equal to n+1,
     * where n is the number of question marks '?' in the string.
     * I.e. when "   great question?b " is input, we expect --> ["great question", "b"]
     */
    @Test
    public void processInputReturnsTrimmedSplittedStringArraysOfCorrespondingSizesAndValues() {
        try {
            /*
            Generally said, it's a bad practice to test private methods. However, for the sake of fun.
             */
            Method processInput = AnswerIs42.class.getDeclaredMethod("processInput", String.class);
            processInput.setAccessible(true);
            assertArrayEquals(
                new String[]{"great question", "b"},
                (String[]) processInput.invoke(ai42, "   great question?b ")
            );
            assertArrayEquals(
                new String[]{"great question", "b"},
                (String[]) processInput.invoke(ai42, "great question?b")
            );
            assertArrayEquals(
                new String[]{"great question", " afds:"},
                (String[]) processInput.invoke(ai42, "great question? afds:")
            );
            assertArrayEquals(
                new String[]{"great question", " \"<answer1>\" \"<answer2>\"  \"<answerX>\""},
                (String[]) processInput.invoke(ai42, "great question? \"<answer1>\" \"<answer2>\"  \"<answerX>\"  ")
            );
            assertArrayEquals(
                new String[]{"great question", " adsf", "fdsa"},
                (String[]) processInput.invoke(ai42, "great question? adsf?fdsa ")
            );
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
    }

//
//    @Test(expected = InvalidQuestionException.class)
//    public void processInputThrowsExceptionIfInputHasNoQuestionMark() throws InvalidQuestionException {
//        ai42.processInput("I am an invalid question ");
//    }
//
//    @Test
//    public void isAddOptionReturnsTrueWhenProcessedInputHasMoreThanOneElement() throws InvalidQuestionException {
//        String[] processedInput = {"great question", " \"<answer1>\" \"<answer2>\"  \"<answerX>\""};
//        assertTrue(AnswerIs42.isAddOption(processedInput));
//        processedInput = new String[]{"great question", " afds:"};
//        assertTrue(AnswerIs42.isAddOption(processedInput));
//    }
//
//    @Test
//    public void isAddOptionReturnsFalseWhenProcessedInputHasOneElementOrLess() throws InvalidQuestionException {
//        assertFalse(AnswerIs42.isAddOption(new String[] {"great question"})); // one element
//        assertFalse(AnswerIs42.isAddOption(new String[0])); // empty array
//    }
//
//    @Test
//    public void isAskOptionReturnsTrueWhenProcessedInputHasExactlyOneElement() throws InvalidQuestionException {
//        assertTrue(AnswerIs42.isAskOption(new String[]{"great question"}));
//    }
//
//    @Test
//    public void isAskOptionReturnsFalseWhenProcessedInputHasZeroElements() throws InvalidQuestionException {
//        assertFalse(AnswerIs42.isAskOption(new String[0]));
//    }
//
//    @Test
//    public void isAskOptionReturnsFalseWhenProcessedInputHasMoreThanOneElement() throws InvalidQuestionException {
//        assertFalse(AnswerIs42.isAskOption(new String[]{"great question", " adsf", "fdsa"}));
//    }
}

