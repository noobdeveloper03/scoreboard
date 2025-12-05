package com.beyblade.scoreboard.dto;

public class ScoreResponse {

    private String message;

    public ScoreResponse (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
