package com.kareemjeiroudi.models;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerIs42 {
    private Map<Question, List<Answer>> storedQuestions = new HashMap();
    private Scanner scanner = new Scanner(System.in);

    public void run() throws IllegalFormatException{
        System.out.println("Ask a question or add new one: ");
        // get next input string
        String input = scanner.nextLine();
        try{
            String[] processedInput = processInput(input);
            if (isAddQuestionOption(processedInput)) {
                List<Answer> answers = extractAnswers(
                        // cut the question off the input string
                        input.substring(input.indexOf('?'))
                );
                if (answers.isEmpty()) {
                    throw new InvalidFormattingException("Answers must be enclosed in quotation marks!");
                }
                Question question = new Question(processedInput[0]);
                storedQuestions.put(question, answers);
            } else if (isAskQuestionOption(processedInput)) {
                Question question = new Question(processedInput[0]);
                List<Answer> answers = storedQuestions.get(question);
                if (answers == null) {
                    answers = new ArrayList<>();
                    answers.add(new Answer("the answer to life, universe and everything is 42"));
                }
                printAnswers(answers);
            }
        // TODO: remove all other types of exceptions make both Question and Answer throw IllegalArgumentException
        } catch (IllegalArgumentException | InvalidQuestionException | InvalidAnswerException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printAnswers(final List<Answer> answers) {
        for (Answer answer: answers) {
            System.out.println("* " + answer);
        }
    }

    private List<Answer> extractAnswers(final String input) {
        List<Answer> answers = new ArrayList<>();
        String rawPattern = "(?<=\")[^\"]*(?=\")"; // any character that's preceded and followed by "
        Matcher matcher = Pattern.compile(rawPattern).matcher(input);
        // TODO: while loop could be written in a better way
        int i = 0;
        while (matcher.find()) {
            if (i%2 == 0) { // get only every second match
                try {
                    Answer answer = new Answer(matcher.group());
                    answers.add(answer);
                } catch (InvalidAnswerException e) {
                }
            }
            i++;
        }
        return answers;
    }

    /**
     * Trims and strips both leading and trailing whitespaces and question marks from input string.
     * And splits the string into String array using '?' as delimiter. The returned array is of size <code>n+1</code>,
     * where <code>n</code> is the number of non-adjacent questions marks in the stripped string.
     * <p>e.g. " ? Hello world   ??" --> "Hello world"
     * @param input input string passed from the command line
     * @return String array whose elements are substrings starting and ending with a '?'
     * @throws IllegalArgumentException if stripped string is empty.
     */
    private String[] processInput(final String input) throws IllegalArgumentException {
        String stripped = StringUtils.strip(input, "? ");
        return stripped.split("\\?"); // split by '?'
    }

    private Boolean isAddQuestionOption(final String[] processedInput) {
        return processedInput.length > 1;
    }

    private Boolean isAskQuestionOption(final String[] processedInput) {
        return processedInput.length == 1;
    }
}
