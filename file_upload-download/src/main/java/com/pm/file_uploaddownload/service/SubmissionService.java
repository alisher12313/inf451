package com.pm.file_uploaddownload.service;

import com.pm.file_uploaddownload.dto.AssignmentStatus;
import com.pm.file_uploaddownload.dto.SubmissionRequestDto;
import com.pm.file_uploaddownload.dto.SubmissionResponseDto;
import com.pm.file_uploaddownload.entity.Assignment;
import com.pm.file_uploaddownload.entity.Student;
import com.pm.file_uploaddownload.entity.Submission;
import com.pm.file_uploaddownload.repository.AssignmentRepository;
import com.pm.file_uploaddownload.repository.StudentRepository;
import com.pm.file_uploaddownload.repository.SubmissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final FileService fileService;

    @Transactional
    public Map<String, String> submitAssignment(SubmissionRequestDto dto, MultipartFile file) throws IOException {
        System.out.println("Assignment ID: " + dto.getAssignmentId());
        System.out.println("Student ID: " + dto.getStudentId());

        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow();
        Assignment assignment = assignmentRepository.findById(dto.getAssignmentId()).orElseThrow();

        boolean exists = submissionRepository.existsByStudentAndAssignment(student, assignment);
        if(exists){
            throw new RuntimeException();
        }

        String fileId = fileService.uploadFile(file, student.getId(), assignment.getTeacher().getId());
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setFileName(file.getOriginalFilename());
        submission.setFilePath(fileId);
        submission.setSubmittedTime(LocalDateTime.now());

        if(LocalDateTime.now().isAfter(assignment.getDueDate())){
            submission.setLate(true);
        }
        else{
            submission.setLate(false);
        }

        submissionRepository.save(submission);

        updateAssignmentStatus(assignment);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Submission uploaded successfully");
        response.put("student", student.getName() + " " + student.getSurname());
        response.put("assignmentTitle", assignment.getTitle());
        return response;
    }

    private void updateAssignmentStatus(Assignment assignment) {
        int totalStudents = assignment.getStudentSet().size();
        int submittedCount = submissionRepository.countByAssignment(assignment);

        if (submittedCount == 0) {
            assignment.setStatus(AssignmentStatus.PENDING);
        } else if (submittedCount < totalStudents) {
            assignment.setStatus(AssignmentStatus.SUBMITTED);
        } else if (submittedCount == totalStudents) {
            assignment.setStatus(AssignmentStatus.COMPLETED);
        }

        assignmentRepository.save(assignment);
    }

    public List<SubmissionResponseDto> getSubmissionsForAssignment(UUID assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        List<Submission> submissions = submissionRepository.findByAssignment(assignment);

        return submissions.stream().map(s ->
                SubmissionResponseDto.builder()
                        .submissionId(s.getId())
                        .studentName(s.getStudent().getName() + " " + s.getStudent().getSurname())
                        .studentEmail(s.getStudent().getEmail())
                        .submittedTime(s.getSubmittedTime())
                        .fileName(s.getFileName())
                        .fileUrl("/submissions/" + s.getId() + "/file")
                        .late(s.isLate())
                        .build()
        ).toList();
    }

    public ResponseEntity<Resource> downloadSubmissionFile(UUID submissionId) throws IOException {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        GridFsResource resource = fileService.downloadFile(submission.getFilePath());

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + submission.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .body(resource);

        return body;
    }
}
