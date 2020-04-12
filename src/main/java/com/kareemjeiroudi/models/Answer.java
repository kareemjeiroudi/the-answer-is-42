package com.kareemjeiroudi.models;

public class Answer {
    public static final int MAX_LENGTH = 255;

    private String s;

    public Answer(String s) throws InvalidAnswerException {
        if (s.length() > MAX_LENGTH) {
            throw new InvalidAnswerException(String.format("Answer is too long! max length %d", MAX_LENGTH));
        }
        if (s.isEmpty() | s.isBlank()) {
            throw new InvalidAnswerException("Answer must contain at least one character!");
        }
        this.s = s;
    }

    @Override
    public String toString() {
      return this.s;
    }
}
