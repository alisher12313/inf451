package com.pm.file_uploaddownload.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String fileName;
    private String filePath;
    private boolean isLate;

    @CreatedDate
    private LocalDateTime submittedTime;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Assignment assignment;
}

