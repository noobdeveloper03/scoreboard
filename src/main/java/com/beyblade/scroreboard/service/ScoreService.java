package com.beyblade.scroreboard.service;

import com.beyblade.scroreboard.dto.Player;

public interface ScoreService {

    void recordScore(Player playerA, Player playerB);
}
