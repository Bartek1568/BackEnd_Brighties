package com.brighties.userservice.repository;

import com.brighties.userservice.model.StudentProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseUserRepository<StudentProfile> {
}
