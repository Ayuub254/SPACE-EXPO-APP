package com.example.finalproject;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String question, List<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }
    public String getCorrectOption() {
        return options.get(correctOptionIndex);
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

}
