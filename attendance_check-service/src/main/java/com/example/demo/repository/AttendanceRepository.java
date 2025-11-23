package com.example.demo.repository;

import com.example.demo.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByStudentIdAndSessionId(UUID studentId, UUID sessionId);
    List<Attendance> findBySessionIdAndStudentIdIn(UUID sessionId, List<UUID> studentIds);
}
