package com.example.todo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping
    public List<Todo> findAll() {
        log.info("findAll");
        System.out.println("findAll");
        return todoService.findAllTodos();
    }

    @GetMapping("/{todoId}")
    public Todo findTodo(@PathVariable Integer todoId) {
        log.info("findTodo by  " + todoId);
        return todoService.findTodo(todoId);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestParam String caption, @RequestParam String text,
                                        @RequestParam LocalDate date, UriComponentsBuilder uriBuilder) {

        Todo todo = todoService.createTodo(caption, text, date);
        return ResponseEntity
                .created(uriBuilder.replacePath("/{todoId}")
                        .build(Map.of("todoId", todo.getId())))
                .body(todo);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestParam Integer id, @RequestParam String caption,
                                        @RequestParam String text, @RequestParam LocalDate date) {

        todoService.updateTodo(id, caption, text, date);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Integer todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
}
