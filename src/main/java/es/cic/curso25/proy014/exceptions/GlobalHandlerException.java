package es.cic.curso25.proy014.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e){
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Error: " + e.getMessage());
    }
}
