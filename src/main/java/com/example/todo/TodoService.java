package com.example.todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {
    List<Todo> findAllTodos();

    Todo findTodo(Integer id);

    Todo createTodo(String caption, String text, LocalDate date);

    void updateTodo(Integer id, String caption, String text, LocalDate date);

    void deleteTodo(Integer id);
}
