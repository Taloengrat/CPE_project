package com.example.projectcpe.ViewModel;

public class Sumary {
    String word;
    String wordtran;
    String scoreStep,scoreWrongStep;

    public Sumary() {
    }

    public Sumary(String word, String wordtran) {
        this.word = word;
        this.wordtran = wordtran;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getScoreStep() {
        return scoreStep;
    }

    public void setScoreStep(String scoreStep) {
        this.scoreStep = scoreStep;
    }


    public String getScoreWrongStep() {
        return scoreWrongStep;
    }

    public void setScoreWrongStep(String scoreWrongStep) {
        this.scoreWrongStep = scoreWrongStep;
    }


    public String getWordtran() {
        return wordtran;
    }

    public void setWordtran(String wordtran) {
        this.wordtran = wordtran;
    }
}
