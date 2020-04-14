package com.kareemjeiroudi.models;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


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
        ai42 = new AnswerIs42();
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
    public void askingUnstoredQuestionPrintsDefaultAnswer() {
        // simulate user input as Byte Array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                ("q1?\n" +                              // first unstored question
                "Is the Coronavirus real?").getBytes()  // second unstored question
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // to print to instead of console
        PrintStream printStream = new PrintStream(outputStream);

        // set a custom System.in and System.out
        System.setIn(inputStream);
        System.setOut(printStream);
        // initialize a new AnswerIs42 object with new System.in and System.out
        AnswerIs42 ai42 = new AnswerIs42();
        ai42.run();  // reads user input from the inputStream with scanner.nextLine()

        assertEquals("Ask a question or add new one:\n" +
                        "* The answer to life, universe and everything is 42\n",
                outputStream.toString()
        );

        outputStream.reset(); // clear output stream
        // run again
        ai42.run();  // reads second user input from the inputStream

        assertEquals("Ask a question or add new one:\n" +
                        "* The answer to life, universe and everything is 42\n",
                outputStream.toString()
        );
    }

    @Test
    public void askingAStoredQuestionPrintsItsAnswersOnSeparateLines() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                ("q? \"\" \"a\" \"b\"\n" +  // adding question q? "" "a" "b"
                "q?").getBytes()            // second input queries the stored question
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // to print to instead of console
        PrintStream printStream = new PrintStream(outputStream);

        System.setIn(inputStream);
        System.setOut(printStream);

        AnswerIs42 ai42 = new AnswerIs42();
        // add the question
        ai42.run();  // reads user input from the inputStream at scanner.nextLine()

        /*
        No specific message should be printed when storing a question. Therefore, current output stream should look
        like
        */
        assertEquals("Ask a question or add new one:\n", outputStream.toString());

        outputStream.reset(); // clear previously "printed" output
        ai42.run(); // query now

        assertEquals("Ask a question or add new one:\n" +
                "* a\n" +
                "* b\n",
                outputStream.toString()
        );
    }

}