package com.brighties.userservice.repository;

import com.brighties.userservice.model.TeacherProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends BaseUserRepository<TeacherProfile> {
}
