package org.example.todo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todo.model.Todo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Value("${todos-source-url-path}")
    private String url;

    public List<Todo> todoList = null;

    @Override
    public List<Todo> getTodosByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        if (todoList == null) {
            downloadTodosFromUrl();
        }

        return todoList.stream()
                .filter(todo -> todo.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public void downloadTodosFromUrl() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String todoTemplate = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            todoList = mapper.readValue(todoTemplate, mapper.getTypeFactory().constructCollectionType(List.class, Todo.class));
            if (todoList == null) {
                throw new RuntimeException("Failed to download todos from URL. Please check the connection.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to download todos from URL. Please check the connection.");

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse the response from the URL.");
        }
    }

    @Override
    public List<Todo> removeTodoByUserIdAndTodoId(int userId, int id) {
        if (userId <= 0 || id <= 0) {
            throw new IllegalArgumentException("User ID and Todo ID must be greater than 0.");
        }
        if (todoList == null) {
            throw new NoSuchElementException("No todos available to remove.");
        }

        List<Todo> todoToRemoveInList = todoList.stream()
                .filter(todo -> todo.getUserId() == userId && todo.getId() == id)
                .toList();

        todoList.remove(todoToRemoveInList.get(0));
        return getTodosByUserId(userId);
    }
}
