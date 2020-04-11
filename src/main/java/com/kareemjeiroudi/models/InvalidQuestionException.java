package com.kareemjeiroudi.models;

public class InvalidQuestionException extends Exception {

    public InvalidQuestionException(String message) {
        super(message);
    }

    public InvalidQuestionException(String message, Throwable err) {
        super(message, err);
    }
}
