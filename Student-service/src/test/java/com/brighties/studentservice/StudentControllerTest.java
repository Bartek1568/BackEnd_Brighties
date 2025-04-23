package com.brighties.studentservice;

import com.brighties.studentservice.controller.StudentController;
import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private StudentRequestDTO studentRequestDTO;
    private StudentResponseDTO studentResponseDTO;

    @BeforeEach
    public void setup() {
        studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Kacper");
        studentRequestDTO.setSurname("Zieliński");
        studentRequestDTO.setEmail("kacper@example.com");
        studentRequestDTO.setAge(14);
        studentRequestDTO.setPhoneNumber(500123456);
        studentRequestDTO.setGoal("Poprawa ocen");
        studentRequestDTO.setCourse("Matematyka");
        studentRequestDTO.setGrade(8);
        studentRequestDTO.setSchoolType("Podstawowa");

        studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(1L);
        studentResponseDTO.setName("Kacper");
        studentResponseDTO.setSurname("Zieliński");
        studentResponseDTO.setEmail("kacper@example.com");
        studentResponseDTO.setAge(14);
        studentResponseDTO.setPhoneNumber(500123456);
        studentResponseDTO.setGoal("Poprawa ocen");
        studentResponseDTO.setCourse("Matematyka");
        studentResponseDTO.setGrade(8);
        studentResponseDTO.setSchoolType("Podstawowa");
    }

    @Test
    @Order(1)
    public void createStudentTest() throws Exception {
        given(studentService.createStudent(any(StudentRequestDTO.class))).willReturn(studentResponseDTO);

        ResultActions response = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(studentResponseDTO.getName())))
                .andExpect(jsonPath("$.surname", is(studentResponseDTO.getSurname())))
                .andExpect(jsonPath("$.email", is(studentResponseDTO.getEmail())));
    }

    @Test
    @Order(2)
    public void getAllStudentsTest() throws Exception {
        List<StudentResponseDTO> students = List.of(studentResponseDTO);
        given(studentService.getAllStudents()).willReturn(students);

        ResultActions response = mockMvc.perform(get("/students"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(students.size())));
    }

    @Test
    @Order(3)
    public void getStudentByIdTest() throws Exception {
        given(studentService.getStudentById(1L)).willReturn(studentResponseDTO);

        ResultActions response = mockMvc.perform(get("/students/{id}", 1L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(studentResponseDTO.getName())))
                .andExpect(jsonPath("$.surname", is(studentResponseDTO.getSurname())))
                .andExpect(jsonPath("$.email", is(studentResponseDTO.getEmail())));
    }

    @Test
    @Order(4)
    public void updateStudentTest() throws Exception {
        studentRequestDTO.setName("Michał");
        studentResponseDTO.setName("Michał");

        given(studentService.updateStudent(eq(1L), any(StudentRequestDTO.class)))
                .willReturn(studentResponseDTO);

        ResultActions response = mockMvc.perform(put("/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("Michał")));
    }

    @Test
    @Order(5)
    public void deleteStudentTest() throws Exception {
        willDoNothing().given(studentService).deleteStudent(1L);

        ResultActions response = mockMvc.perform(delete("/students/{id}", 1L));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
