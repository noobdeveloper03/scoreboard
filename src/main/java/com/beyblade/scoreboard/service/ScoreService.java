package com.beyblade.scoreboard.service;

import com.beyblade.scoreboard.dto.Player;

public interface ScoreService {

    void recordScore(Player playerA, Player playerB);
}
