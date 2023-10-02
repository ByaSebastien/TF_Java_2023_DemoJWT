package be.btorm.tf_java_2023_demojwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlePrecondition(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
