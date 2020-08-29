package com.kareemjeiroudi.factory;

import com.kareemjeiroudi.chiclet.AnswerIs42;
import com.kareemjeiroudi.chiclet.AnswerIs42Add;
import com.kareemjeiroudi.chiclet.AnswerIs42Ask;
import com.kareemjeiroudi.storage.Memory;
import com.kareemjeiroudi.tokens.InputProcessor;
import com.kareemjeiroudi.tokens.Path;

public class AnswerIs42Factory {

  private AnswerIs42 ai42;


  public AnswerIs42Factory construct(final Path path) {

    switch(path) {
      case ASKING_QUESTION:
        ai42 = new AnswerIs42Ask();
        break;
      case ADDING_QUESTION:
        ai42 = new AnswerIs42Add();
        break;
    }
    return this;
  }

  public AnswerIs42Factory withInputProcessor(final InputProcessor inputProcessor) {
    if (ai42 != null) {
      ai42.setInputProcessor(inputProcessor);
    }
    return this;
  }

  public AnswerIs42Factory withMemory(final Memory memory) {
    if (ai42 != null) {
      ai42.setMemory(memory);
    }
    return this;
  }

  public AnswerIs42 done() throws AnswerIs42ConstructionException {
    if (ai42 == null) {
      throw new AnswerIs42ConstructionException("AnswerIs42 was either null or not properly constructed");
    }
    return ai42;
  }
}