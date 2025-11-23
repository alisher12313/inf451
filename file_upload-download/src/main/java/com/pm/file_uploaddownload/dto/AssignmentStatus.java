package com.pm.file_uploaddownload.dto;

public enum AssignmentStatus {
    PENDING,      // Teacher created, not yet due
    SUBMITTED,    // At least one student submitted
    COMPLETED,    // All students submitted
    CLOSED        // Teacher closed grading
}


