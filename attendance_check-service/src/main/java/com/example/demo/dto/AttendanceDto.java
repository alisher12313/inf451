package com.example.demo.dto;

import com.example.demo.entity.enumeration.PresentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class AttendanceDto {
    private UUID studentId;
    private UUID sessionId;
    private PresentStatus status;
}

