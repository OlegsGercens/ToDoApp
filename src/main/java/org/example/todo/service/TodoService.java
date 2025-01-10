package org.example.todo.service;

import org.example.todo.model.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getTodosByUserId (int userId);

    void downloadTodosFromUrl();

    List<Todo> removeTodoByUserIdAndTodoId (int userId, int id);
}
