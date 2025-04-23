package com.brighties.teacherservice;
import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.repository.TeacherRepository;
import com.brighties.teacherservice.service.TeacherService;
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
public class TeacherServiceTests {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher;

    private TeacherRequestDTO teacherRequestDTO;

    @BeforeEach
    public void setup() {
        teacher = Teacher.builder()
                .id(1L)
                .name("Anna")
                .surname("Kowalska")
                .email("anna@example.com")
                .age(30)
                .phoneNumber(123456789)
                .description("Math teacher")
                .build();

        teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setName("Anna");
        teacherRequestDTO.setSurname("Kowalska");
        teacherRequestDTO.setEmail("anna@example.com");
        teacherRequestDTO.setAge(30);
        teacherRequestDTO.setPhoneNumber(123456789);
        teacherRequestDTO.setDescription("Math teacher");
    }

    @Test
    @Order(1)
    public void createTeacherTest() {
        // given
        given(teacherRepository.existsByEmail(teacherRequestDTO.getEmail())).willReturn(false);
        given(teacherRepository.save(any(Teacher.class))).willReturn(teacher);

        // when
        TeacherResponseDTO result = teacherService.createTeacher(teacherRequestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("anna@example.com");
    }

    @Test
    @Order(2)
    public void getTeacherByIdTest() {
        // given
        given(teacherRepository.findById(1L)).willReturn(Optional.of(teacher));

        // when
        TeacherResponseDTO result = teacherService.getTeacherById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Anna");
    }

    @Test
    @Order(3)
    public void getTeacherById_shouldThrowIfNotFound() {
        // given
        given(teacherRepository.findById(1L)).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> teacherService.getTeacherById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Order(4)
    public void getAllTeachers_shouldReturnList() {
        // given
        Teacher teacher2 = Teacher.builder()
                .id(2L)
                .name("Tomasz")
                .surname("Nowak")
                .email("tomasz@example.com")
                .age(40)
                .phoneNumber(987654321)
                .description("Physics teacher")
                .build();

        given(teacherRepository.findAll()).willReturn(List.of(teacher, teacher2));

        // when
        List<TeacherResponseDTO> teachers = teacherService.getTeachers();

        // then
        assertThat(teachers).isNotNull();
        assertThat(teachers.size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    public void updateTeacher_shouldReturnUpdatedDTO() {
        // given
        given(teacherRepository.findById(1L)).willReturn(Optional.of(teacher));
        teacherRequestDTO.setName("Maria");
        teacherRequestDTO.setEmail("maria@example.com");

        teacher.setName("Maria");
        teacher.setEmail("maria@example.com");

        given(teacherRepository.save(teacher)).willReturn(teacher);

        // when
        TeacherResponseDTO updated = teacherService.updateTeacher(1L, teacherRequestDTO);

        // then
        assertThat(updated.getName()).isEqualTo("Maria");
        assertThat(updated.getEmail()).isEqualTo("maria@example.com");
    }

    @Test
    @Order(6)
    public void deleteTeacher_shouldCallRepository() {
        // given
        willDoNothing().given(teacherRepository).deleteById(1L);

        // when
        teacherService.deleteTeacher(1L);

        // then
        verify(teacherRepository, times(1)).deleteById(1L);
    }
}
