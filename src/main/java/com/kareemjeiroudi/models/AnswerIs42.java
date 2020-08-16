package com.kareemjeiroudi.models;

import com.kareemjeiroudi.processes.InputProcessor;
import com.kareemjeiroudi.processes.Path;

import java.util.*;

public class AnswerIs42 {

    private Map<Question, List<Answer>> storedQuestions = new HashMap<>();

    private InputProcessor inputProcessor;

    public AnswerIs42() {
         inputProcessor = new InputProcessor();
    }

    public List<Answer> newTurn(String userInput) throws IllegalFormatException, InvalidQuestionException {
        List<Answer> answers = null;
        inputProcessor.setValue(userInput);
        String[] processedInput = inputProcessor.processInput();
        if (inputProcessor.getPath() == Path.ADDING_QUESTION) {
            answers = inputProcessor.extractAnswers("?");
            if (answers.isEmpty()) {
                throw new InvalidFormattingException("Answers were not properly formatted or not provided at all");
            }
            Question question = new Question(processedInput[0]);
            storedQuestions.put(question, answers);
        } else if (inputProcessor.getPath() == Path.ASKING_QUESTION) {
            Question question = new Question(processedInput[0]);
            answers = storedQuestions.get(question);

            if (answers == null) {
                answers = new ArrayList<>();
                try {
                    answers.add(new Answer("The answer to life, universe and everything is 42"));
                } catch (InvalidAnswerException e) {
                    e.printStackTrace();
                }
            }

        }

        return answers;
    }

    public Path getPath() {
        return inputProcessor.getPath();
    }
}
