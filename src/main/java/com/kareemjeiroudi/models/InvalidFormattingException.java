package com.kareemjeiroudi.models;

public class InvalidFormattingException extends IllegalArgumentException {
  InvalidFormattingException(String message) {
    super(message);
  }
}
