package com.reliableplugins.oregenerator.util;

public enum Message {
    ERROR_PERMISSION("No permissions"),
    ERROR_NOT_PLAYER("Only players may execute this command");


    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }
}
