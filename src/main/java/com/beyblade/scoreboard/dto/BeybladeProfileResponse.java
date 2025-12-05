package com.beyblade.scoreboard.dto;

public class BeybladeProfileResponse {

    private String message;
    private Beyblade beyblade;

    public BeybladeProfileResponse(String message,Beyblade beyblade) {
        this.message = message;
        this.beyblade = beyblade;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Beyblade getBeyblade() {
        return beyblade;
    }

    public void setBeyblade(Beyblade beyblade) {
        this.beyblade = beyblade;
    }
}
