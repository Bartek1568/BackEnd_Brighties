package com.brighties.teacherservice;

import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.repository.TeacherRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeacherRepositoryTests {

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher createSampleTeacher() {
        return Teacher.builder()
                .name("Bob")
                .surname("Doe")
                .email("bob@example.com")
                .age(30)
                .phoneNumber(123456789)
                .description("Test description")
                .build();
    }

    @Test
    @Order(1)
    public void saveTeacher_shouldPersistTeacher() {
        // given
        Teacher teacher = createSampleTeacher();

        // when
        Teacher savedTeacher = teacherRepository.save(teacher);

        // then
        assertThat(savedTeacher).isNotNull();
        assertThat(savedTeacher.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void getTeacherById_shouldReturnTeacher() {
        // given
        Teacher teacher = teacherRepository.save(createSampleTeacher());

        // when
        Optional<Teacher> foundTeacher = teacherRepository.findById(teacher.getId());

        // then
        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    @Order(3)
    public void getAllTeachers_shouldReturnNonEmptyList() {
        // given
        teacherRepository.save(createSampleTeacher());
        teacherRepository.save(createSampleTeacher());

        // when
        List<Teacher> teachers = teacherRepository.findAll();

        // then
        assertThat(teachers).isNotEmpty();
        assertThat(teachers.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(4)
    public void updateTeacher_shouldPersistChanges() {
        // given
        Teacher teacher = teacherRepository.save(createSampleTeacher());

        // when
        teacher.setAge(45);
        Teacher updated = teacherRepository.save(teacher);

        // then
        assertThat(updated.getAge()).isEqualTo(45);
    }

    @Test
    @Order(5)
    public void deleteTeacher_shouldRemoveTeacher() {
        // given
        Teacher teacher = teacherRepository.save(createSampleTeacher());
        Long id = teacher.getId();

        // when
        teacherRepository.deleteById(id);

        // then
        Optional<Teacher> deleted = teacherRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}
