package com.kareemjeiroudi.navigator;

import com.kareemjeiroudi.chiclet.AnswerIs42;
import com.kareemjeiroudi.factory.AnswerIs42ConstructionException;
import com.kareemjeiroudi.factory.AnswerIs42Factory;
import com.kareemjeiroudi.model.Answer;
import com.kareemjeiroudi.model.InvalidQuestionException;
import com.kareemjeiroudi.response.Response;
import com.kareemjeiroudi.storage.Memory;
import com.kareemjeiroudi.tokens.InputProcessor;
import com.kareemjeiroudi.tokens.Path;

import java.util.IllegalFormatException;
import java.util.List;

public class GameNavigator {

  private Memory memory;
  private InputProcessor inputProcessor;


  public GameNavigator(Memory memory) {
    this.memory = memory;
    this.inputProcessor = new InputProcessor();
  }

  /**
   * Trims and strips both leading and trailing whitespaces and question marks from input string.
   * And splits the string into String array using '?' as delimiter. The returned array is of size <code>n+1</code>,
   * where <code>n</code> is the number of non-adjacent questions marks in the stripped string.
   * <p>e.g. " ? Hello world   ??" --> "Hello world"
   * @return String array whose elements are substrings starting and ending with a '?'
   */
  public Response newTurn(String userInput) throws IllegalFormatException, InvalidQuestionException {
    inputProcessor.stripValue(" ?", userInput);

    String[] split = inputProcessor.splitAtChar("\\?");
    Path path = determinePath(split);
    AnswerIs42Factory factory = new AnswerIs42Factory();
    AnswerIs42 ai42 = null;
    try {
      ai42 = factory.construct(path)
              .withInputProcessor(inputProcessor)
              .withMemory(memory)
              .done();
    } catch (AnswerIs42ConstructionException e) {
      e.printStackTrace();
    }
    return ai42.handle();
  }

  public Path determinePath(final String[] processedInput) {
    if (processedInput.length > 1) {
      return Path.ADDING_QUESTION;
    } else if (processedInput.length == 1) {
      return Path.ASKING_QUESTION;
    } else {
      return Path.UNDEFINED;
    }
  }

  public void printAnswers(Response response) {
    List<Answer> answers = response.respond();
    if (!response.respond().isEmpty()) {
      for (Answer answer: response.respond()) {
        System.out.println("* " + answer);
      }
    }
  }

}
