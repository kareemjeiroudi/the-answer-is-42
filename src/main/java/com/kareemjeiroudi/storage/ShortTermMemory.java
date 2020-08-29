package com.kareemjeiroudi.storage;

import com.kareemjeiroudi.model.Answer;
import com.kareemjeiroudi.model.Question;

import java.util.List;
import java.util.Map;

public class ShortTermMemory implements Memory {

  private Map<Question, List<Answer>> record;


  @Override
  public List<Answer> fetchAnswers(final Question question) {
    return record.get(question);
  }

  @Override
  public void save(final Question question, final List<Answer> answer) {
    record.put(question, answer);
  }

}
