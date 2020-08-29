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

import com.kareemjeiroudi.model.InvalidQuestionException;
import com.kareemjeiroudi.navigator.GameNavigator;
import com.kareemjeiroudi.response.Response;
import com.kareemjeiroudi.storage.ShortTermMemory;

import java.util.Scanner;

public class Main {

  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    GameNavigator navigator = new GameNavigator(new ShortTermMemory());

    while (true) { // no terminal condition
      System.out.println("Ask a question or add new one:");
      String userInput = scanner.nextLine();

      try {
        Response response = navigator.newTurn(userInput);
        navigator.printAnswers(response);
      } catch (InvalidQuestionException e) {
        e.printStackTrace();
      }
    }
  }


}
