package com.beyblade.scoreboard.service;

import com.beyblade.scoreboard.dto.Beyblade;
import com.beyblade.scoreboard.dto.Player;

import java.util.List;

public interface ScoreService {

    void recordScore(Player playerA, Player playerB);

    List<Beyblade> getBeyblades();


}
