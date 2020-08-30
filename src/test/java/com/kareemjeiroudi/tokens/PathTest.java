package com.kareemjeiroudi.tokens;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PathTest {

  @Test
  public void testPathValues() {
    Path[] expected = new Path[] {Path.ADDING_QUESTION, Path.ASKING_QUESTION, Path.UNDEFINED};
    Path[] actual = Path.values();
    assertArrayEquals(expected, actual);
  }

}
