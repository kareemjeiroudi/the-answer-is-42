package com.kareemjeiroudi;

import com.kareemjeiroudi.models.AnswerIs42;

public class Main {
  public static void main(String[] args) {
    AnswerIs42 ai42 = new AnswerIs42();
    while (true) { // no terminal condition
      ai42.run();
    }
  }
}
