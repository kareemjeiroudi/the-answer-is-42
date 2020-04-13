package com.kareemjeiroudi.models;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class AnswerIs42Test {

    private AnswerIs42 ai42;
    private static Method processInput;
    private static Method extractAnswers;

    private PrintStream printStream;


    @BeforeClass
    public static void setup() {
        try {
            /*
            Generally said, it's a bad practice to test private methods. However, for the sake of fun.
             */
            processInput = AnswerIs42.class.getDeclaredMethod("processInput", String.class);
            processInput.setAccessible(true);
            extractAnswers = AnswerIs42.class.getDeclaredMethod("extractAnswers", String.class);
            extractAnswers.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void init() {
        ByteArrayInputStream inStream = new ByteArrayInputStream("q? \"a\" \"b\"".getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        printStream = new PrintStream(outStream);
        ai42 = new AnswerIs42(inStream, printStream);
    }

    @Test
    public void processInputReturnsOneElementArrayWhenInputHasTrailingOrLeadingWhitespaces() {
        try {
            assertArrayEquals(
                    new String[]{"great question"},
                    (String[]) processInput.invoke(ai42, "   great question ")
            );
            assertArrayEquals(
                    new String[]{"who's there"},
                    (String[]) processInput.invoke(ai42, "   who's there ")
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processInputWorksWithQuestionsContainingLeadingAndTrailingQuestionMarks() {
        try {
            assertArrayEquals(
                    new String[]{"great question"},
                    (String[]) processInput.invoke(ai42, "   great question? ")
            );
            assertArrayEquals(
                    new String[]{"who's there"},
                    (String[]) processInput.invoke(ai42, "??   who's there? ")
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * processInput should return an array of type String[] of size equal to n+1,
     * where n is the number of question marks '?' in the trimmed string.
     * I.e. when "   great question?b " is input, we expect --> ["great question", "b"]
     */
    @Test
    public void processInputReturnsTrimmedSplittedStringArraysOfCorrespondingSizesAndValues() {
        try {
            assertArrayEquals(
                    new String[]{"great question", "b"},
                    (String[]) processInput.invoke(ai42, "   great question?b ")
            );

            assertArrayEquals(
                    new String[]{"great question", "b"},
                    (String[]) processInput.invoke(ai42, "???great question?b?? ")
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
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void extractAnswersIgnoresEmptyAnswers() {
        try {
            List<Answer> actual = (List<Answer>) extractAnswers.invoke(ai42, "\"a\"\"b\" \"\"");
            List<Answer> expected = new ArrayList<>();
            expected.add(new Answer("a"));
            expected.add(new Answer("b"));
            assertEquals(expected, actual);
        } catch (IllegalAccessException | InvocationTargetException | InvalidAnswerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void extractAnswersIgnoresBlankAnswers() {
        try {
            List<Answer> actual = (List<Answer>) extractAnswers.invoke(ai42, "\"a\"\"b\" \"   \"");
            List<Answer> expected = new ArrayList<>();
            expected.add(new Answer("a"));
            expected.add(new Answer("b"));
            assertEquals(expected, actual);

            actual = (List<Answer>) extractAnswers.invoke(ai42, "\"   \"\"a \"\"b \" \"   \"");
            expected = new ArrayList<>();
            expected.add(new Answer("a "));
            expected.add(new Answer("b "));
            assertEquals(expected, actual);
        } catch (IllegalAccessException | InvocationTargetException | InvalidAnswerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void printStreamCarriesExpectedOutput() {
        ai42.run();
        System.out.println(printStream.toString());
    }

}

