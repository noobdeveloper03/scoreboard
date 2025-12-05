package com.beyblade.scoreboard.dto;

public class Beyblade {

    private String code;

    private String name;
    private int rank;

    private int matches;

    private int win;

    private int lose;

    private String winRate;

    private String loseRate;

    private int burst;

    private int spin;

    private int extreme;

    private int overfinish;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public String getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(String loseRate) {
        this.loseRate = loseRate;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getSpin() {
        return spin;
    }

    public void setSpin(int spin) {
        this.spin = spin;
    }

    public int getExtreme() {
        return extreme;
    }

    public void setExtreme(int extreme) {
        this.extreme = extreme;
    }

    public int getOverfinish() {
        return overfinish;
    }

    public void setOverfinish(int overfinish) {
        this.overfinish = overfinish;
    }
}
