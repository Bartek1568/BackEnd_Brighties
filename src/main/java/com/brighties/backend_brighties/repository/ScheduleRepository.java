package com.brighties.backend_brighties.repository;

import com.brighties.backend_brighties.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
