package com.beyblade.scoreboard.controller;

import com.beyblade.scoreboard.dto.BeyBladeResponse;
import com.beyblade.scoreboard.dto.ScoreRequest;
import com.beyblade.scoreboard.dto.ScoreResponse;
import com.beyblade.scoreboard.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @Autowired
    private ScoreService scoreService;

    @PostMapping("/record")
    ResponseEntity<ScoreResponse> recordScore(@RequestBody ScoreRequest request) {
        scoreService.recordScore(request.getPlayerA(),request.getPlayerB());
        return new ResponseEntity<>(new ScoreResponse("Success"), HttpStatus.OK);
    }

    @GetMapping("/beyblades")
    ResponseEntity<BeyBladeResponse> getBeyblades() {
        return new ResponseEntity<>(new BeyBladeResponse("Success",scoreService.getBeyblades()), HttpStatus.OK);
    }
}
