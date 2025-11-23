package com.example.demo.controller;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Course;
import com.example.demo.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/session/{sessionId}")
    public List<Attendance> getAttendanceForSession(
            @PathVariable UUID sessionId,
            @RequestParam List<UUID> studentIds) {
        return attendanceService.getStudentsForSession(sessionId, studentIds);
    }

    @PostMapping
    public Attendance markStudent(@RequestBody AttendanceDto dto) {
        attendanceService.markStudent(dto.getStudentId(), dto.getSessionId(), dto.getStatus());
        return attendanceService.getStudentsForSession(dto.getSessionId(), List.of(dto.getStudentId()))
                .get(0);
    }

    @GetMapping(path = "/getCourse/{id}")
    public ResponseEntity<?> getCourse(@PathVariable("id") UUID uuid){
        Course course = attendanceService.getCourse(uuid);
        System.out.println("Course fetched: " + course);
        return ResponseEntity.ok(course);
    }
}

