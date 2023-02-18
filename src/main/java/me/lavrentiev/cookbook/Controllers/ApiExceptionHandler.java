package me.lavrentiev.cookbook.Controllers;

import me.lavrentiev.cookbook.exeptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{
    @ExceptionHandler
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } //выбрасывает статус ошибки 400 (BAD_REQUEST)
}
