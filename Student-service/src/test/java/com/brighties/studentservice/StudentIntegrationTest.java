package com.brighties.studentservice;

import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long createdStudentId;

    @Test
    @Order(1)
    public void shouldCreateStudent() throws Exception {
        StudentRequestDTO request = new StudentRequestDTO();
        request.setName("Kacper");
        request.setSurname("Nowak");
        request.setEmail("kacperTest@example.com");
        request.setAge(14);
        request.setPhoneNumber(500123456);
        request.setGoal("Zdać egzamin");
        request.setCourse("Fizyka");
        request.setGrade(8);
        request.setSchoolType("Podstawowa");

        ResultActions response = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Kacper")))
                .andExpect(jsonPath("$.surname", is("Nowak")));

        String content = response.andReturn().getResponse().getContentAsString();
        StudentResponseDTO responseDTO = objectMapper.readValue(content, StudentResponseDTO.class);
        createdStudentId = responseDTO.getId();
    }

    @Test
    @Order(2)
    public void shouldGetStudentById() throws Exception {
        mockMvc.perform(get("/students/{id}", createdStudentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Kacper")));
    }

    @Test
    @Order(3)
    public void shouldGetAllStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Order(4)
    public void shouldUpdateStudent() throws Exception {
        StudentRequestDTO update = new StudentRequestDTO();
        update.setName("Michał");
        update.setSurname("Kowalski");
        update.setEmail("michal@example.com");
        update.setAge(15);
        update.setPhoneNumber(511222333);
        update.setGoal("Dostać 5");
        update.setCourse("Chemia");
        update.setGrade(8);
        update.setSchoolType("Podstawowa");

        mockMvc.perform(put("/students/{id}", createdStudentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Michał")));
    }

    @Test
    @Order(5)
    public void shouldDeleteStudent() throws Exception {
        // Sprawdzenie, czy student istnieje przed usunięciem
        mockMvc.perform(get("/students/{id}", createdStudentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(createdStudentId.intValue())));

        // Usunięcie studenta
        mockMvc.perform(delete("/students/{id}", createdStudentId))
                .andExpect(status().isNoContent());

        // Sprawdzenie, czy student został usunięty
        mockMvc.perform(get("/students/{id}", createdStudentId))
                .andExpect(status().isNotFound());
    }
}
