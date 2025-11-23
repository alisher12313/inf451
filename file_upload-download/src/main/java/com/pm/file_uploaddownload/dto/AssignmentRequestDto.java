package com.pm.file_uploaddownload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequestDto {
    private String title;

    private String description;

    private LocalDateTime dueDate;

    private Set<UUID> studentIds;
}

