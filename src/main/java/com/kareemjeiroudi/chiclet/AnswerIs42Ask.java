package com.kareemjeiroudi.chiclet;

import com.kareemjeiroudi.model.Answer;
import com.kareemjeiroudi.model.InvalidQuestionException;
import com.kareemjeiroudi.model.Question;
import com.kareemjeiroudi.response.FullResponse;
import com.kareemjeiroudi.response.Response;
import com.kareemjeiroudi.storage.Memory;
import com.kareemjeiroudi.tokens.InputProcessor;

import java.util.Collections;
import java.util.List;

public class AnswerIs42Ask implements AnswerIs42 {

    private Memory memory;
    private InputProcessor inputProcessor;

    public AnswerIs42Ask() {
         inputProcessor = new InputProcessor();
    }

    /**
     * Extracts tokens enclosed by quotes from the latent string, and returns a list containing answers of values
     * equal to
     * the matched tokens.
     * @return answers - List of containing answers of values matching the found tokens
     */
    @Override
    public Response handle() {
        String questionStatement = inputProcessor.splitAt("\\?")[0];
        List<Answer> answers = Collections.emptyList();
        try {
            Question question = new Question(questionStatement);
            answers = memory.fetchAnswers(question);
        } catch (InvalidQuestionException e) {
            e.printStackTrace();
        }
        return new FullResponse(answers);
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
