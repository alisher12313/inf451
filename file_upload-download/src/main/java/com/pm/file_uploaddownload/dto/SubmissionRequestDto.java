package com.pm.file_uploaddownload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequestDto {
    private UUID assignmentId;
    private UUID studentId;
}
