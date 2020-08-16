package com.kareemjeiroudi.processes;

import com.kareemjeiroudi.models.Answer;
import com.kareemjeiroudi.models.InvalidAnswerException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class InputProcessor {

  private String value;
  private Path path;

  public InputProcessor() {
  }

  public InputProcessor(final String value) {
    this.value = value;
  }

  /**
   * Trims and strips both leading and trailing whitespaces and question marks from input string.
   * And splits the string into String array using '?' as delimiter. The returned array is of size <code>n+1</code>,
   * where <code>n</code> is the number of non-adjacent questions marks in the stripped string.
   * <p>e.g. " ? Hello world   ??" --> "Hello world"
   * @return String array whose elements are substrings starting and ending with a '?'
   */
  public String[] processInput() {
    String stripped = StringUtils.strip(value, "? ");
    String[] processedInput = stripped.split("\\?");
    determinePath(processedInput);
    return processedInput;
  }

  /**
   * Extracts tokens enclosed by quotes from the latent string, and returns a list containing answers of values
   * equal to
   * the matched tokens.
   * @return answers - List of containing answers of values matching the found tokens
   */

  public List<Answer> extractAnswers(String anchor) {
    final String pattern = "\"[^\"]*\"";
    String[] tokens = extractTokens(anchor, pattern);
    return answersFromTokens(tokens);
  }

  public String[] extractTokens(final String anchor, final String pattern) {
    String newValue = value.substring(value.indexOf(anchor));
    return Pattern.compile(pattern)
            .matcher(newValue)
            .results()
            .map(MatchResult::group)
            .map(s -> s.substring(1, s.length()-1)) // strip leading & trailing quotes
            .toArray(String[]::new);
  }

  private List<Answer> answersFromTokens(final String[] tokens) {
    List<Answer> answers = new ArrayList<>();
    for (String token : tokens) {
      try {
        Answer answer = new Answer(token);
        answers.add(answer);
      } catch (InvalidAnswerException ignoredException) {
        // do nothing, just ignore the answer
      }
    }
    return answers;
  }


  public void setValue(final String value) {
    this.value = value;
  }

  private void determinePath(final String[] processedInput) {
    if (processedInput.length > 1) {
      path = Path.ADDING_QUESTION;
    } else if (processedInput.length == 1) {
      path = Path.ASKING_QUESTION;
    } else {
      path = Path.UNDEFINED;
    }
  }

  public Path getPath() {
    return path;
  }
}
