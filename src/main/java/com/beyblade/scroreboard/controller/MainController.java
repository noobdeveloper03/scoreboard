package com.beyblade.scroreboard.controller;

import com.beyblade.scroreboard.dto.ScoreRequest;
import com.beyblade.scroreboard.dto.ScoreResponse;
import com.beyblade.scroreboard.service.ScoreService;
import com.beyblade.scroreboard.service.impl.ScoreServiceImpl;
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
}
