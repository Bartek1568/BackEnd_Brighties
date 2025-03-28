package com.brighties.backend_brighties.repository;

import com.brighties.backend_brighties.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Student, Long> {
}
