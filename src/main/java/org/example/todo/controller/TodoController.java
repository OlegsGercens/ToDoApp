package org.example.todo.controller;

import org.example.todo.model.Todo;
import org.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TodoController {

    public final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos/{userId}")
    public ResponseEntity<?> getTodoList(@PathVariable int userId) {
        try {
            List<Todo> todosByUserId = todoService.getTodosByUserId(userId);
            return new ResponseEntity<>(todosByUserId, HttpStatus.OK);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{userId}/refresh")
    public ResponseEntity<?> refreshTodoList(@PathVariable int userId) {
        try {
            todoService.downloadTodosFromUrl();
            List<Todo> todosByUserId = todoService.getTodosByUserId(userId);
            if (todosByUserId == null) {
                return new ResponseEntity<>("No todos found for User ID: " + userId, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todosByUserId, HttpStatus.OK);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Failed to refresh todo list. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{userId}/remove/{id}")
    public ResponseEntity<?> removeTodo(@PathVariable int userId, @PathVariable int id) {
        try {
            List<Todo> updatedTodos = todoService.removeTodoByUserIdAndTodoId(userId, id);
            if (updatedTodos == null) {
                return new ResponseEntity<>("No todo found with ID: " + id + " for User ID: " + userId, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedTodos, HttpStatus.OK);
        } catch (IllegalArgumentException | NoSuchElementException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}