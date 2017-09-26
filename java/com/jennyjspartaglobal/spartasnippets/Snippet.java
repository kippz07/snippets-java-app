package com.jennyjspartaglobal.spartasnippets;

/**
 * Created by Tech-A42 on 26/09/2017.
 */

public class Snippet {

    private String mQuestion;
    private String mAnswer;

    public Snippet (String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }

    public String getQuestion() { return mQuestion; }

    public String getAnswer() { return mAnswer; }
}
