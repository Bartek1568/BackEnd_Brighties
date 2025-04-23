package com.brighties.studentservice.repository;

import com.brighties.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(Integer phoneNumber);

}
