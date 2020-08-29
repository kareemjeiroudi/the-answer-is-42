package com.kareemjeiroudi.response;

import com.kareemjeiroudi.model.Answer;
import com.kareemjeiroudi.model.InvalidAnswerException;

import java.util.List;

public class FullResponse implements Response {

  private List<Answer> answers;

  public FullResponse(List<Answer> answers) {
    this.answers = answers;
  }

  @Override
  public List<Answer> respond() {
    if (answers.isEmpty()) {
      try {
        answers.add(new Answer("the answer to life, universe and everything is 42"));
      } catch (InvalidAnswerException e) {
      }
    }

    return answers;
  }
}
