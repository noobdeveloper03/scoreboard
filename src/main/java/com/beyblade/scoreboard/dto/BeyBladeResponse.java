package com.beyblade.scoreboard.dto;

import java.util.List;

public class BeyBladeResponse {

    private String message;

    private List<Beyblade> beybladeList;

    public BeyBladeResponse(String message,List<Beyblade> beybladeList) {
        this.message = message;
        this.beybladeList = beybladeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Beyblade> getBeybladeList() {
        return beybladeList;
    }

    public void setBeybladeList(List<Beyblade> beybladeList) {
        this.beybladeList = beybladeList;
    }
}
