package com.omar;

public class BadFormatException extends Exception {
    private int badLine;

    public BadFormatException(int badLine) {
        this.badLine = badLine;
    }

    public int getBadLine() {
        return badLine;
    }

}
