package com.brighties.teacherservice;



import com.brighties.teacherservice.controller.TeacherController;
import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeacherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService teacherService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TeacherService teacherService() {
            return Mockito.mock(TeacherService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private TeacherResponseDTO teacherResponseDTO;
    private TeacherRequestDTO teacherRequestDTO;

    @BeforeEach
    public void setup() {
        teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("John");
        teacherRequestDTO.setSurname("Cena");
        teacherRequestDTO.setEmail("john@gmail.com");
        teacherRequestDTO.setAge(30);
        teacherRequestDTO.setPhoneNumber(123456789);
        teacherRequestDTO.setDescription("Math Teacher");

        teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setId("1");
        teacherResponseDTO.setName("John");
        teacherResponseDTO.setSurname("Cena");
        teacherResponseDTO.setEmail("john@gmail.com");
        teacherResponseDTO.setAge(30);
        teacherResponseDTO.setPhoneNumber(123456789);
        teacherResponseDTO.setDescription("Math Teacher");
    }

    @Test
    @Order(1)
    public void createTeacherTest() throws Exception {
        given(teacherService.createTeacher(any(TeacherRequestDTO.class))).willReturn(teacherResponseDTO);

        ResultActions response = mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherRequestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(teacherResponseDTO.getName())))
                .andExpect(jsonPath("$.surname", is(teacherResponseDTO.getSurname())))
                .andExpect(jsonPath("$.email", is(teacherResponseDTO.getEmail())));
    }

    @Test
    @Order(2)
    public void getAllTeachersTest() throws Exception {
        List<TeacherResponseDTO> teachers = List.of(teacherResponseDTO);
        given(teacherService.getTeachers()).willReturn(teachers);

        ResultActions response = mockMvc.perform(get("/teachers"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(teachers.size())));
    }

    @Test
    @Order(3)
    public void getTeacherByIdTest() throws Exception {
        given(teacherService.getTeacherById(1L)).willReturn(teacherResponseDTO);

        ResultActions response = mockMvc.perform(get("/teachers/{id}", 1L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(teacherResponseDTO.getName())))
                .andExpect(jsonPath("$.surname", is(teacherResponseDTO.getSurname())))
                .andExpect(jsonPath("$.email", is(teacherResponseDTO.getEmail())));
    }

    @Test
    @Order(4)
    public void updateTeacherTest() throws Exception {
        teacherRequestDTO.setName("Max");
        teacherResponseDTO.setName("Max");

        given(teacherService.updateTeacher(eq(1L), any(TeacherRequestDTO.class)))
                .willReturn(teacherResponseDTO);

        ResultActions response = mockMvc.perform(put("/teachers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherRequestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("Max")));
    }

    @Test
    @Order(5)
    public void deleteTeacherTest() throws Exception {
        willDoNothing().given(teacherService).deleteTeacher(1L);

        ResultActions response = mockMvc.perform(delete("/teachers/{id}", 1L));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}

