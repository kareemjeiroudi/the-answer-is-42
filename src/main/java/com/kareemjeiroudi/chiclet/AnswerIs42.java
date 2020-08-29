package com.kareemjeiroudi.chiclet;

import com.kareemjeiroudi.model.InvalidQuestionException;
import com.kareemjeiroudi.response.Response;
import com.kareemjeiroudi.storage.Memory;
import com.kareemjeiroudi.tokens.InputProcessor;

public interface AnswerIs42 {

  Response handle() throws InvalidQuestionException;

  void setInputProcessor(InputProcessor inputProcessor);

  void setMemory(Memory memory);

}
