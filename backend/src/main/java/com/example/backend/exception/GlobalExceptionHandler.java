package com.example.backend.exception;

import com.example.backend.controller.AIController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String exceptionHandler(Exception e){
        log.info(e.getMessage());
        return e.getMessage();
    }

}
