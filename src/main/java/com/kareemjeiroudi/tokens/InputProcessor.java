package com.kareemjeiroudi.tokens;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class InputProcessor {

  private String value;

  public String getValue() {
    return value;
  }

  /**
   * Parses the original value stored by the <code>InputProcessor</code>. Parsing is applied the original value.
   * @param pattern RegEx pattern that the parser should match in order for it to find the desired tokens.
   * @throws NullPointerException if the <code>value</code> stored by the parser happens to be null.
   * @return an array of String containing the tokens of interest
   */
  public String[] parsePattern(final String pattern) {
    return parsePattern(pattern, value);
  }

  /**
   * Parses the original value stored by the <code>InputProcessor</code>.
   * @param pattern RegEx pattern that the parser should match in order for it to find the desired tokens.
   * @param value a custom value that can be provided to overwrite the original one.
   * @return an array of String containing the tokens of interest
   */
  public String[] parsePattern(final String pattern, final String value) {
    this.value = value; // overwrite the recent value;

    return Pattern.compile(pattern)
            .matcher(value)
            .results()
            .map(MatchResult::group)
            .map(s -> s.substring(1, s.length()-1)) // strip leading & trailing quotes
            .toArray(String[]::new);
  }

  /**
   * strips any string input from a desired set of characters on both ends (left and right). E.g. you can strip
   * <code>"! ! Hello world ! ?"</code>
   * to <code>"Hello world"</code> by providing <code>" !?"</code> in the first argument.
   * @param stripChars a string containing all those characters to be stripped from both ends
   * @throws NullPointerException if the <code>value</code> stored by the parser happens to be null.
   * @return a new string missing the <code>stripChars</code> on both ends
   */
  public String stripValue(final String stripChars) {
    return stripValue(stripChars, value);
  }

  /**
   * strips any string input from a desired set of characters on both ends (left and right). E.g. you can strip
   * <code>"! ! Hello world ! ?"</code>
   * to <code>"Hello world"</code> by providing <code>" !?"</code> in the first argument.
   * @param stripChars a string containing all those characters to be stripped from both ends
   * @param value the desired string value to be stripped
   * @return a new string missing the <code>stripChars</code> on both ends
   */
  public String stripValue(final String stripChars, final String value) {
    this.value = value;
    return StringUtils.strip(value, stripChars);
  }

  public String[] splitAtChar(final String delimeter) {
    return splitAtChar(delimeter, value);
  }

  public String[] splitAtChar(final String delimeter, final String value) {
    this.value = value;
    return value.split(delimeter);
  }

}
