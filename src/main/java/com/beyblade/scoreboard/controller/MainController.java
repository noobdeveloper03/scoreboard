package com.beyblade.scoreboard.controller;

import com.beyblade.scoreboard.dto.*;
import com.beyblade.scoreboard.exception.BeyBladeNotFoundException;
import com.beyblade.scoreboard.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//null is only allowed during local, remove if this is going to be upload in the internet
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

    @GetMapping("/beyblade/{code}")
    ResponseEntity<BeybladeProfileResponse> getBeybladeStats(@PathVariable("code") String code) {
        List<Beyblade> beybladeList = scoreService.getBeyblades();
        Beyblade searchedBeyblade = null;
        for(Beyblade beyblade:beybladeList) {
            if(beyblade.getCode().equalsIgnoreCase(code)) {
                searchedBeyblade = beyblade;
            }
        }
        if (searchedBeyblade==null) {
            throw new BeyBladeNotFoundException("No Beyblade with this code is found");
        }
        return new ResponseEntity<>(new BeybladeProfileResponse("Success",searchedBeyblade), HttpStatus.OK);
    }
}
