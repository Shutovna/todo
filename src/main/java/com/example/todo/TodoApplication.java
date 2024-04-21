package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {
    @Autowired
    private TodoService todoService;

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        todoService.createTodo("Caption", "Text", LocalDate.now());
        todoService.createTodo("Caption2", "Text2", LocalDate.now());
        todoService.createTodo("Caption3", "Text3", LocalDate.now());
    }
}
