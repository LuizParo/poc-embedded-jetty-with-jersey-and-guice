package com.moiz.model;

public class Hello {
    private String message;
    private Long instant;

    public Hello(String message, Long instant) {
        this.message = message;
        this.instant = instant;
    }

    public String getMessage() {
        return message;
    }

    public Long getInstant() {
        return instant;
    }
}
