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

import com.kareemjeiroudi.models.Answer;
import com.kareemjeiroudi.models.AnswerIs42;
import com.kareemjeiroudi.models.InvalidQuestionException;
import com.kareemjeiroudi.processes.Path;

import java.util.List;
import java.util.Scanner;

public class Main {

  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    AnswerIs42 ai42 = new AnswerIs42();

    while (true) { // no terminal condition
      System.out.println("Ask a question or add new one:");
      String input = scanner.nextLine();
      try {
        List<Answer> answers = ai42.newTurn(input);
        if (ai42.getPath() == Path.ASKING_QUESTION) {
          printAnswers(answers);
        }
      } catch (InvalidQuestionException iqe) {
        System.err.println(iqe.getMessage());
      }
    }
  }

  private static void printAnswers(final List<Answer> answers) {
    for (Answer answer: answers) {
      System.out.println("* " + answer);
    }
  }
}
