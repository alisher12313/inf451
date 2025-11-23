package com.example.demo.service;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseSession;
import com.example.demo.entity.enumeration.PresentStatus;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.CourseSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final AttendanceRepository attendanceRepository;

    public Course getCourse(UUID id){
        log.info("Fetching course with id={}", id);

        return courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found: {}", id);
                    return new IllegalArgumentException("Course not found");
                });
    }

    public List<Course> getCourses(){
        log.info("Fetching all courses");
        return courseRepository.findAll();
    }

    public List<Attendance> getStudentsForSession(UUID sessionId, List<UUID> allStudentIds) {

        log.info("Fetching attendance for session {} with {} students",
                sessionId, allStudentIds.size());

        CourseSession session = courseSessionRepository.findById(sessionId)
                .orElseThrow(() -> {
                    log.warn("Session not found: {}", sessionId);
                    return new IllegalArgumentException("Session not found");
                });

        validateSessionDate(session);

        List<Attendance> existing = attendanceRepository
                .findBySessionIdAndStudentIdIn(sessionId, allStudentIds);

        Set<UUID> alreadyMarked = existing.stream()
                .map(Attendance::getStudentId)
                .collect(Collectors.toSet());

        List<Attendance> newAttendances = allStudentIds.stream()
                .filter(id -> !alreadyMarked.contains(id))
                .map(studentId -> {
                    log.info("Auto-creating attendance for student {} in session {}", studentId, sessionId);

                    Attendance a = new Attendance();
                    a.setStudentId(studentId);
                    a.setSession(session);
                    a.setStatus(PresentStatus.PRESENT);
                    return a;
                })
                .toList();

        if (!newAttendances.isEmpty()) {
            log.info("Saving {} new attendance entries", newAttendances.size());
            attendanceRepository.saveAll(newAttendances);
        }

        existing.addAll(newAttendances);
        return existing;
    }

    public void markStudent(UUID studentId, UUID sessionId, PresentStatus status) {

        log.info("Marking student {} in session {} as {}", studentId, sessionId, status);

        CourseSession session = courseSessionRepository.findById(sessionId)
                .orElseThrow(() -> {
                    log.warn("Session not found: {}", sessionId);
                    return new IllegalArgumentException("Session not found");
                });

        validateSessionDate(session);

        Attendance attendance = attendanceRepository
                .findByStudentIdAndSessionId(studentId, sessionId)
                .orElseGet(() -> {
                    log.info("Creating new attendance for student {} in session {}", studentId, sessionId);
                    Attendance a = new Attendance();
                    a.setStudentId(studentId);
                    a.setSession(session);
                    return a;
                });

        attendance.setStatus(status);
        attendanceRepository.save(attendance);

        log.info("Attendance saved (student={}, session={})", studentId, sessionId);
    }

    private void validateSessionDate(CourseSession session) {
        LocalDate sessionDate = session.getDate();
        LocalDate today = LocalDate.now();

        if (sessionDate.isAfter(today)) {
            log.warn("Refused marking future session: {}", sessionDate);
            throw new IllegalArgumentException(
                    "Cannot mark attendance for future sessions. Session date: " + sessionDate
            );
        }

        LocalDate sevenDaysAgo = today.minusDays(7);
        if (sessionDate.isBefore(sevenDaysAgo)) {

            log.warn("Refused marking session older than 7 days: {}", sessionDate);

            throw new IllegalArgumentException(
                    "Cannot mark attendance for sessions older than 7 days. Session date: " + sessionDate
            );
        }
    }
}
