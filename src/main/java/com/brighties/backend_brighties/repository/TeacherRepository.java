package com.brighties.backend_brighties.repository;

import com.brighties.backend_brighties.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository  extends JpaRepository<Teacher, Integer> {
}
