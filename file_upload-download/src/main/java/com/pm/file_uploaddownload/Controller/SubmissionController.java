package com.pm.file_uploaddownload.Controller;

import com.pm.file_uploaddownload.dto.SubmissionRequestDto;
import com.pm.file_uploaddownload.dto.SubmissionResponseDto;
import com.pm.file_uploaddownload.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/submit")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping(path = "/assignment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> submit(@RequestParam("studentId") UUID studentId, @RequestParam("assignmentId") UUID assignmentId, @RequestParam("file") MultipartFile file) throws IOException {
        SubmissionRequestDto dto = new SubmissionRequestDto();
        dto.setStudentId(studentId);
        dto.setAssignmentId(assignmentId);
        return ResponseEntity.ok(submissionService.submitAssignment(dto, file));
    }

    @GetMapping("/{submissionId}/file")
    public ResponseEntity<Resource> downloadSubmissionFile(@PathVariable UUID submissionId) throws IOException {
        return submissionService.downloadSubmissionFile(submissionId);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsForAssignment(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(submissionService.getSubmissionsForAssignment(assignmentId));
    }
}
