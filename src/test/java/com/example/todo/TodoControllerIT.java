package com.example.todo;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void findTodos_ReturnsTodosList() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/");
        LocalDate now = LocalDate.now();

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {"id": 1, "caption": "Caption", "text": "Text", "date": "%s"},
                                    {"id": 2, "caption": "Caption2", "text": "Text2"},
                                    {"id": 3, "caption": "Caption3", "text": "Text3"}
                                ]""".formatted(now))
                );
    }

    @Test
    void createTodo_RequestIsValid_ReturnsNewTodo() throws Exception {
        // given
        LocalDate now = LocalDate.now();

        var requestBuilder = MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("caption",  "Caption 555")
                .param("text", "text 555")
                .param("date", now.toString());


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, Matchers.matchesPattern("http://localhost/\\d+")),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 4, "caption": "Caption 555", "text": "text 555", "date":  "%s"
                                }""".formatted(now)));
    }

    @Test
    void updateTodo_RequestIsValid_ReturnsNoContent() throws Exception {
        // given
        LocalDate now = LocalDate.now();

        var requestBuilder = MockMvcRequestBuilders.put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id",  "1")
                .param("caption",  "Caption 555")
                .param("text", "text 555")
                .param("date", now.toString());


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent());

    }

    @Test
    void deleteTodo_RequestIsValid_ReturnsNoContent() throws Exception {
        // given
        LocalDate now = LocalDate.now();

        var requestBuilder = MockMvcRequestBuilders.delete("/1")
                .contentType(MediaType.APPLICATION_JSON);


        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent());

    }


}