package ppa.lepp.elgar.test.exercise.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(new HashMap<>() {{
            put("error", exception.getMessage());
        }});
    }
}
