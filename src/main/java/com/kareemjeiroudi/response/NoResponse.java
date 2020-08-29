package com.kareemjeiroudi.response;

import com.kareemjeiroudi.model.Answer;

import java.util.Collections;
import java.util.List;

public class NoResponse implements Response {
  @Override
  public List<Answer> respond() {
    return Collections.emptyList();
  }
}
