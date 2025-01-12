
package org.example.todo.service;

import org.example.todo.model.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl service;

    @Test
    void getTodosByUserIdWithNegativeUserIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.getTodosByUserId(-1));
    }

    @Test
    void getTodosByUserIdWithNullUserIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.getTodosByUserId(0));
    }

    @Test
    void getTodosByUserIdWhenListIsAlreadyDownloaded() {
        Todo todo = new Todo(1, 1, "title1", true);
        Todo todo2 = new Todo(2, 2, "title2", false);
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);
        todos.add(todo2);
        service.setTodoList(todos);
        List<Todo> todosByUserId = service.getTodosByUserId(1);
        assertEquals(1, todosByUserId.size());
        assertEquals(todo, todosByUserId.get(0));
    }

    @Test
    void removeTodoByUserIdAndTodoIdThrowsIllegalArgumentExceptionWithUserIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeTodoByUserIdAndTodoId(0, 1));
    }

    @Test
    void removeTodoByUserIdAndTodoIdThrowsIllegalArgumentExceptionWithIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeTodoByUserIdAndTodoId(1, 0));
    }

    @Test
    void removeTodoByUserIdAndTodoIdThrowsNoSuchElementExceptionWhenListIsNull() {
        assertThrows(NoSuchElementException.class, () -> service.removeTodoByUserIdAndTodoId(1,1));
    }

    @Test
    void removeTodoByUserIdAndTodoIdThrowsNoSuchElementExceptionWhenListIsEmpty() {
        List<Todo> todos = new ArrayList<>();
        service.setTodoList(todos);
        assertThrows(NoSuchElementException.class, () -> service.removeTodoByUserIdAndTodoId(1,1));
    }

    @Test
    void removeTodoByUserIdAndTodoIdSuccess () {
        Todo todo = new Todo(1, 1, "title1", true);
        Todo todo2 = new Todo(2, 2, "title2", false);
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);
        todos.add(todo2);
        service.setTodoList(todos);
        List<Todo> todosByUserId = service.getTodosByUserId(1);
        assertEquals(1, todosByUserId.size());
        assertEquals(todo, todosByUserId.get(0));
        service.removeTodoByUserIdAndTodoId(1,1);
        assertEquals(1, service.getTodoList().size());
        assertEquals(service.getTodoList().get(0), todo2);
    }

    @Test
    void removeTodoByUserIdAndTodoIdRemovingNotExistingTodo () {
        Todo todo = new Todo(1, 1, "title1", true);
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);
        service.setTodoList(todos);
        assertThrows(NoSuchElementException.class, () -> service.removeTodoByUserIdAndTodoId(2, 1));
    }
}
