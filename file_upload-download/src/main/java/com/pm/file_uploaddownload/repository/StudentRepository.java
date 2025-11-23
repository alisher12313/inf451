package com.pm.file_uploaddownload.repository;

import com.pm.file_uploaddownload.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
