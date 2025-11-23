package com.beyblade.scroreboard.dto;

import lombok.Getter;
import lombok.Setter;

public class ScoreRequest {


    private Player playerA;

    private Player playerB;

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Player playerB) {
        this.playerB = playerB;
    }
}
