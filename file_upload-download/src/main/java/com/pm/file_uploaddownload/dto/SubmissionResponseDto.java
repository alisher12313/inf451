package com.pm.file_uploaddownload.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SubmissionResponseDto {
    private UUID submissionId;
    private String studentName;
    private String studentEmail;
    private LocalDateTime submittedTime;
    private String fileName;
    private String fileUrl;
    private boolean late;
}

