package com.pm.file_uploaddownload.Controller;

import com.pm.file_uploaddownload.dto.AssignmentRequestDto;
import com.pm.file_uploaddownload.dto.AssignmentResponseDto;
import com.pm.file_uploaddownload.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/uploadAssignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService service;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestParam UUID teacherId, @RequestBody AssignmentRequestDto dto){
        Map<String, String> body = service.createAssignment(teacherId, dto);

        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAll(){
        List<AssignmentResponseDto> body = service.getAllAssignments();

        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/getByTeacher")
    public ResponseEntity<?> getAssignmentsByTeacher(@RequestParam UUID teacherId){
        List<AssignmentResponseDto> body = service.getAssignmentsByTeacher(teacherId);

        return ResponseEntity.ok(body);
    }
}
