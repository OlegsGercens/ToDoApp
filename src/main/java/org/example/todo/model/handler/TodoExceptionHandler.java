package org.example.todo.model.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.example.todo.api.response.TodoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class TodoExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TodoResponse<String>> handleIllegalArgumentException (IllegalArgumentException exception,
                                                                                  HttpServletRequest request) {
        return new ResponseEntity<>(TodoResponse.error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<TodoResponse<String>> handleNoSuchElementException (NoSuchElementException exception,
                                                          HttpServletRequest request) {
        return new ResponseEntity<>(TodoResponse.error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<TodoResponse<String>> handleRuntimeException (RuntimeException exception, HttpServletRequest request) {
        return new ResponseEntity(TodoResponse.error(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
