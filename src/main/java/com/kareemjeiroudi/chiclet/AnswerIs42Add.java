package com.kareemjeiroudi.chiclet;

import com.kareemjeiroudi.model.*;
import com.kareemjeiroudi.response.NoResponse;
import com.kareemjeiroudi.response.Response;
import com.kareemjeiroudi.storage.Memory;
import com.kareemjeiroudi.tokens.InputProcessor;

import java.util.ArrayList;
import java.util.List;

public class AnswerIs42Add implements AnswerIs42 {

  private InputProcessor inputProcessor;
  private Memory memory;

  public AnswerIs42Add() {
    this.inputProcessor = new InputProcessor();
  }


  @Override
  public Response handle() throws InvalidQuestionException {
    String[] split = inputProcessor.splitAt("\\?");
    Question question = new Question(split[0]);

    String[] tokens = inputProcessor.parsePattern("\"[^\"]*\"", split[1]);
    List<Answer> answers = tokensToAnswers(tokens);
    if (answers.isEmpty()) {
      throw new InvalidFormattingException("Answers were not properly formatted or not provided at all");
    }
    memory.save(question, answers);

    return new NoResponse();
  }

  public List<Answer> tokensToAnswers(final String[] tokens) {
    List<Answer> answers = new ArrayList<>();
    for (String token : tokens) {
      try {
        Answer answer = new Answer(token);
        answers.add(answer);
      } catch (InvalidAnswerException e) {
        // ignore unsuccessful answers
      }
    }
    return answers;
  }

  @Override
  public void setInputProcessor(final InputProcessor inputProcessor) {
    if (inputProcessor != null) {
      this.inputProcessor = inputProcessor;
    }
  }

  @Override
  public void setMemory(final Memory memory) {
    if (memory != null) {
      this.memory = memory;
    }
  }
}
