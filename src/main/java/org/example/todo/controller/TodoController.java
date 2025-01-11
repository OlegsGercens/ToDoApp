package org.example.todo.controller;

import org.example.todo.api.response.TodoResponse;
import org.example.todo.model.Todo;
import org.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TodoController {

    public final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos/{userId}")
    public ResponseEntity<TodoResponse<Todo>> getTodoList(@PathVariable int userId) {
        List<Todo> todos = todoService.getTodosByUserId(userId);
        TodoResponse<Todo> response = TodoResponse.success(todos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/todos/{userId}/refresh")
    public ResponseEntity<TodoResponse<Todo>> refreshTodoList(@PathVariable int userId) {
        List<Todo> refreshed = todoService.refresh(userId);
        TodoResponse<Todo> response = TodoResponse.success(refreshed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/todos/{userId}/remove/{id}")
    public ResponseEntity<TodoResponse<Todo>> removeTodo(@PathVariable int userId, @PathVariable int id) {
        List<Todo> todos = todoService.removeTodoByUserIdAndTodoId(userId, id);
        TodoResponse<Todo> response = TodoResponse.success(todos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}