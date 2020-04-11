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
        this.questionStatement = questionStatement;
        answers = new ArrayList<Answer>();
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
