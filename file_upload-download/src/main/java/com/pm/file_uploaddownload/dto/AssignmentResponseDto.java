package com.pm.file_uploaddownload.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class AssignmentResponseDto {
    private UUID id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private int totalStudents;
    private int submittedCount;
}
