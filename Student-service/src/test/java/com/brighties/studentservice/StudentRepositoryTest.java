package com.brighties.studentservice;

import com.brighties.studentservice.model.Student;
import com.brighties.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student createSampleStudent() {
        return Student.builder()
                .name("Alice")
                .surname("Smith")
                .email("alice@example.com")
                .age(18)
                .phoneNumber(555123456)
                .goal("Pass final exam")
                .course("Mathematics")
                .grade(3)
                .schoolType("High School")
                .build();
    }

    @Test
    @Order(1)
    public void saveStudent_shouldPersistStudent() {
        // given
        Student student = createSampleStudent();

        // when
        Student savedStudent = studentRepository.save(student);

        // then
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void getStudentById_shouldReturnStudent() {
        // given
        Student student = studentRepository.save(createSampleStudent());

        // when
        Optional<Student> foundStudent = studentRepository.findById(student.getId());

        // then
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    @Order(3)
    public void getAllStudents_shouldReturnNonEmptyList() {
        // given
        studentRepository.save(createSampleStudent());
        studentRepository.save(createSampleStudent());

        // when
        List<Student> students = studentRepository.findAll();

        // then
        assertThat(students).isNotEmpty();
        assertThat(students.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(4)
    public void updateStudent_shouldPersistChanges() {
        // given
        Student student = studentRepository.save(createSampleStudent());

        // when
        student.setGrade(3);
        Student updated = studentRepository.save(student);

        // then
        assertThat(updated.getGrade()).isEqualTo(3);
    }

    @Test
    @Order(5)
    public void deleteStudent_shouldRemoveStudent() {
        // given
        Student student = studentRepository.save(createSampleStudent());
        Long id = student.getId();

        // when
        studentRepository.deleteById(id);

        // then
        Optional<Student> deleted = studentRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}
