package com.brighties.teacherservice;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
public class TeacherIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long createdTeacherId;

    @Test
    @Order(1)
    public void shouldCreateTeacher() throws Exception {
        TeacherRequestDTO request = new TeacherRequestDTO();
        request.setName("John");
        request.setSurname("Cena");
        request.setEmail("johnTest@gmail.com");
        request.setAge(30);
        request.setPhoneNumber(123456789);
        request.setDescription("Math Teacher");

        ResultActions response = mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.surname", is("Cena")));

        // wyciągamy ID utworzonego nauczyciela
        String content = response.andReturn().getResponse().getContentAsString();
        TeacherResponseDTO responseDTO = objectMapper.readValue(content, TeacherResponseDTO.class);
        createdTeacherId = Long.parseLong(responseDTO.getId());
    }

    @Test
    @Order(2)
    public void shouldGetTeacherById() throws Exception {
        mockMvc.perform(get("/teachers/{id}", createdTeacherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    @Order(3)
    public void shouldGetAllTeachers() throws Exception {
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }


    @Test
    @Order(4)
    public void shouldUpdateTeacher() throws Exception {
        TeacherRequestDTO update = new TeacherRequestDTO();
        update.setName("Updated");
        update.setSurname("Name");
        update.setEmail("updated@gmail.com");
        update.setAge(35);
        update.setPhoneNumber(111222333);
        update.setDescription("Updated desc");

        mockMvc.perform(put("/teachers/{id}", createdTeacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated")));
    }

    @Test
    @Order(5)
    public void shouldDeleteTeacher() throws Exception {
        // Sprawdzenie, czy nauczyciel istnieje przed usunięciem
        mockMvc.perform(get("/teachers/{id}", createdTeacherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdTeacherId.toString())));

        // Usunięcie nauczyciela
        mockMvc.perform(delete("/teachers/{id}", createdTeacherId))
                .andExpect(status().isNoContent());

        // Sprawdzenie, czy nauczyciel został usunięty
        mockMvc.perform(get("/teachers/{id}", createdTeacherId))
                .andExpect(status().isNotFound());
    }


}

