package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class DefaultTodoService implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo findTodo(Integer id) {
        return todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Todo createTodo(String caption, String text, LocalDate date) {
        Todo todo = new Todo(null, caption, text, date);
        return todoRepository.save(todo);
    }

    @Override
    public void updateTodo(Integer id, String caption, String text, LocalDate date) {
        todoRepository.findById(id).ifPresentOrElse(todo -> {
            todo.setCaption(caption);
            todo.setText(text);
            todo.setDate(date);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    @Override
    public void deleteTodo(Integer id) {
        todoRepository.deleteById(id);
    }
}
