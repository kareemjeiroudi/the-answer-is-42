package com.kareemjeiroudi;

/*
A note of the author:

I know that both InvalidQuestionException and InvalidAnswerException are kind
of an overkill for this simple program, but I am trying to tell how I would go about this
problem, had it been something more serious. The same goes for both Question and
Answer models. I imagine, for this simple program it would suffice to use a String
representation simply. However, the idea again is: should the Question or Answer model
become significantly larger later in development, one would be able to edit or even extend
them easily.
 */

import com.kareemjeiroudi.models.AnswerIs42;

public class Main {
  public static void main(String[] args) {
    AnswerIs42 ai42 = new AnswerIs42(System.in, System.out);
    while (true) { // no terminal condition
      ai42.run();
    }
  }
}
