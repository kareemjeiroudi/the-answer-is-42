package com.kareemjeiroudi.models;


public class Question {

    // should be private. protected for testing
    protected static final int MAX_LENGTH = 255;

    private String questionStatement;

    public Question(String questionStatement) throws InvalidQuestionException {
        if (questionStatement.length() > MAX_LENGTH) {
            throw new InvalidQuestionException(String.format("Question is too long! Max length is %d", MAX_LENGTH));
        }
        if (questionStatement.isEmpty()) {
            throw new InvalidQuestionException("Questions must contain at least one character!");
        }
        if (questionStatement == null) {
            throw new NullPointerException();
        }
        this.questionStatement = questionStatement;
    }


    @Override
    public String toString() {
        return questionStatement;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return questionStatement.equals(question.questionStatement);
    }

    @Override
    public int hashCode() {
        return questionStatement.hashCode();
    }

    public String getQuestionStatement() {
        return questionStatement;
    }

}
