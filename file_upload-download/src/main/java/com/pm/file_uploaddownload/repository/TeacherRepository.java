package com.pm.file_uploaddownload.repository;

import com.pm.file_uploaddownload.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
