package com.kareemjeiroudi.models;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class AnswerIs42 {
    private Map<Question, List<Answer>> storedQuestions = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);


    public void run() throws IllegalFormatException {
        System.out.println("Ask a question or add new one:");
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
                    answers.add(new Answer("The answer to life, universe and everything is 42"));
                }
                printAnswers(answers);
            }
        } catch (IllegalArgumentException | InvalidQuestionException | InvalidAnswerException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printAnswers(final List<Answer> answers) {
        for (Answer answer: answers) {
            System.out.println("* " + answer);
        }
    }

    /**
     * Extracts tokens enclosed by quotes from the latent string, and returns a list containing answers of values
     * equal to
     * the matched tokens.
     * @param input - latent string where tokens can be extracted from
     * @return answers - List of containing answers of values matching the found tokens
     */
    private List<Answer> extractAnswers(final String input) {
        List<Answer> answers = new ArrayList<>();
        final String pattern = "\"[^\"]*\"";
        String[] tokens = Pattern.compile(pattern)
                .matcher(input)
                .results()
                .map(MatchResult::group)
                .map(s -> s.substring(1, s.length()-1)) // strip leading & trailing quotes
                .toArray(String[]::new);
        for (String token : tokens) {
            try {
                Answer answer = new Answer(token);
                answers.add(answer);
            } catch (InvalidAnswerException ignored) {
            }
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
     */
    private String[] processInput(final String input) {
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
