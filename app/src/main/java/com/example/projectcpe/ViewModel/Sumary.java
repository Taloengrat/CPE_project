package com.example.projectcpe.ViewModel;

public class Sumary {
    String word;
    String wordtran;

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

    public String getWordtran() {
        return wordtran;
    }

    public void setWordtran(String wordtran) {
        this.wordtran = wordtran;
    }
}
