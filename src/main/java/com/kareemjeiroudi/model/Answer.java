package com.kareemjeiroudi.model;

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
      return s;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return s != null ? s.equals(answer.s) : answer.s == null;
    }

    @Override
    public int hashCode() {
        return s != null ? s.hashCode() : 0;
    }
}
