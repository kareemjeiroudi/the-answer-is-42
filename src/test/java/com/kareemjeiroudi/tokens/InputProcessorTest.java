package com.kareemjeiroudi.tokens;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputProcessorTest {

  private InputProcessor inputProcessor;

  @Before
  public void setUp() {
    inputProcessor = new InputProcessor();
  }

  @Test
  public void testGetValue() {
    assertNull(inputProcessor.getValue());
    String newValue = "hello world";
    inputProcessor.parsePattern("\\w+", newValue);
    assertEquals(inputProcessor.getValue(), newValue);
  }

  @Test
  public void testParsePattern() {
    String value = "dce jfa 3 music nmf kareem c cgm ";
    String[] actual = inputProcessor.parsePattern("\\w{3}\\s", value);
    String[] expected = new String[] {"ce", "fa", "ic", "mf", "em", "gm"};
    assertArrayEquals(expected, actual);

    actual = inputProcessor.parsePattern("\\w{5,6}");
    expected = new String[]{"usi", "aree"};
    assertArrayEquals(expected, actual);

    assertEquals(value, inputProcessor.getValue());
  }

  @Test
  public void testStripValue() {
    String actual = inputProcessor.stripValue(" <>", "<>>> stripped message >><>");
    String expected = "stripped message";
    assertEquals(expected, actual);

    assertEquals(actual, inputProcessor.getValue());

    actual = inputProcessor.stripValue("es");
    expected = "tripped messag";
    assertEquals(expected, actual);

    assertEquals(actual, inputProcessor.getValue());
  }

  @Test
  public void testSplitAt() {
    String value = "Part I | Part II | Part3";
    String[] actual = inputProcessor.splitAt("\\|", value);
    String[] expected = new String[] {"Part I ", " Part II ", " Part3"};
    assertArrayEquals(expected, actual);

    assertEquals(value, inputProcessor.getValue());

    actual = inputProcessor.splitAt(" Part II ");
    expected = new String[] {"Part I |", "| Part3"};
    assertArrayEquals(expected, actual);

    assertEquals(value, inputProcessor.getValue());
  }

  /*
   * TODO: provide more tests to test exceptions and more corner cases
   */
}
