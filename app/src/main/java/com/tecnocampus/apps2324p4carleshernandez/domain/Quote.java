package com.tecnocampus.apps2324p4carleshernandez.domain;

public class Quote {
    private final String text;
    private final String author;

    public Quote(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }
}
