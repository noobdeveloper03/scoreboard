package com.beyblade.scoreboard.handlers;

import com.beyblade.scoreboard.constant.ErrorCodes;
import com.beyblade.scoreboard.dto.ExceptionResponse;
import com.beyblade.scoreboard.exception.BeyBladeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BeyBladeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(BeyBladeNotFoundException beyException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ErrorCodes.BEY_NOT_FOUND,beyException.getMessage()));
    }
}
