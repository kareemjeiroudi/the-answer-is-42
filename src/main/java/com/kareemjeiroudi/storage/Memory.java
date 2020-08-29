package com.kareemjeiroudi.storage;

import com.kareemjeiroudi.model.Answer;
import com.kareemjeiroudi.model.Question;

import java.util.List;

public interface Memory {

  List<Answer> fetchAnswers(Question question);

  void save(Question question, List<Answer> answer);

}
