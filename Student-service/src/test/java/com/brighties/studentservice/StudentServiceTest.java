package com.brighties.studentservice;

import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.exception.StudentNotFoundException;
import com.brighties.studentservice.model.Student;
import com.brighties.studentservice.repository.StudentRepository;
import com.brighties.studentservice.service.StudentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    private StudentRequestDTO studentRequestDTO;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .id(1L)
                .name("Kacper")
                .surname("Zieliński")
                .email("kacper.z@example.com")
                .age(14)
                .phoneNumber(506789012)
                .goal("Rozwój zainteresowań")
                .course("Informatyka")
                .grade(5)
                .schoolType("Szkoła podstawowa")
                .build();

        studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName("Kacper");
        studentRequestDTO.setSurname("Zieliński");
        studentRequestDTO.setEmail("kacper.z@example.com");
        studentRequestDTO.setAge(14);
        studentRequestDTO.setPhoneNumber(506789012);
        studentRequestDTO.setGoal("Rozwój zainteresowań");
        studentRequestDTO.setCourse("Informatyka");
        studentRequestDTO.setGrade(5);
        studentRequestDTO.setSchoolType("Szkoła podstawowa");
    }

    @Test
    @Order(1)
    public void createStudentTest() {
        // given
        given(studentRepository.existsByEmail(studentRequestDTO.getEmail())).willReturn(false);
        given(studentRepository.save(any(Student.class))).willReturn(student);

        // when
        StudentResponseDTO result = studentService.createStudent(studentRequestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("kacper.z@example.com");
    }

    @Test
    @Order(2)
    public void getStudentByIdTest() {
        // given
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));

        // when
        StudentResponseDTO result = studentService.getStudentById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Kacper");
    }

    @Test
    @Order(3)
    public void getStudentById_shouldThrowIfNotFound() {
        // given
        given(studentRepository.findById(1L)).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(StudentNotFoundException.class);
    }

    @Test
    @Order(4)
    public void getAllStudents_shouldReturnList() {
        // given
        Student student2 = Student.builder()
                .id(2L)
                .name("Natalia")
                .surname("Szymańska")
                .email("natalia.s@example.com")
                .age(16)
                .phoneNumber(507890123)
                .goal("Poprawa umiejętności")
                .course("Język angielski")
                .grade(3)
                .schoolType("Liceum")
                .build();

        given(studentRepository.findAll()).willReturn(List.of(student, student2));

        // when
        List<StudentResponseDTO> students = studentService.getAllStudents();

        // then
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    public void updateStudent_shouldReturnUpdatedDTO() {
        // given
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        studentRequestDTO.setName("Michał");
        studentRequestDTO.setEmail("michal.z@example.com");

        student.setName("Michał");
        student.setEmail("michal.z@example.com");

        given(studentRepository.save(student)).willReturn(student);

        // when
        StudentResponseDTO updated = studentService.updateStudent(1L, studentRequestDTO);

        // then
        assertThat(updated.getName()).isEqualTo("Michał");
        assertThat(updated.getEmail()).isEqualTo("michal.z@example.com");
    }

    @Test
    @Order(6)
    public void deleteStudent_shouldCallRepository() {
        // given
        willDoNothing().given(studentRepository).deleteById(1L);

        // when
        studentService.deleteStudent(1L);

        // then
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
