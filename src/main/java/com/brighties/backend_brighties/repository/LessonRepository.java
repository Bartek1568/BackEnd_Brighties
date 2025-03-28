package com.brighties.backend_brighties.repository;

import com.brighties.backend_brighties.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
