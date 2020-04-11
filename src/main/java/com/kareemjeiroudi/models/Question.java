package com.kareemjeiroudi.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    // should be private. protected for testing
    protected static final int MAX_LENGTH = 255;

    private String questionStatement;
    private List<Answer> answers;

    public Question(String questionStatement) throws InvalidQuestionException {
        if (questionStatement.length() > MAX_LENGTH) {
            throw new InvalidQuestionException(String.format("Question is too long! Max length is %d", MAX_LENGTH));
        }
        if (questionStatement.isEmpty()) {
            throw new InvalidQuestionException("Questions must contain at least one character");
        }
        this.questionStatement = questionStatement;
        this.answers = new ArrayList<Answer>();
    }

    public Question(String questionStatement, List<Answer> answers) throws InvalidQuestionException {
        this(questionStatement);
        if (answers.isEmpty()) {
            throw new IllegalArgumentException("Questions must have at least one answers");
        }
        this.answers = answers;
    }

    @Override
    public String toString() {
        return this.questionStatement;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
