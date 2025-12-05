package com.beyblade.scoreboard.dto;

public class Player {

    private String code;

    private int score;

    private int extremeFinish;

    private int overFinish;

    private int spinFinish;

    private int burstFinish;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getExtremeFinish() {
        return extremeFinish;
    }

    public void setExtremeFinish(int extremeFinish) {
        this.extremeFinish = extremeFinish;
    }

    public int getOverFinish() {
        return overFinish;
    }

    public void setOverFinish(int overFinish) {
        this.overFinish = overFinish;
    }

    public int getSpinFinish() {
        return spinFinish;
    }

    public void setSpinFinish(int spinFinish) {
        this.spinFinish = spinFinish;
    }

    public int getBurstFinish() {
        return burstFinish;
    }

    public void setBurstFinish(int burstFinish) {
        this.burstFinish = burstFinish;
    }
}
