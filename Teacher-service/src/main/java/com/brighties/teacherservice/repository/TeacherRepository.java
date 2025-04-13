package com.brighties.teacherservice.repository;

import com.brighties.teacherservice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository  extends JpaRepository<Teacher, Long> {

    boolean existsByEmail(String email);
}
