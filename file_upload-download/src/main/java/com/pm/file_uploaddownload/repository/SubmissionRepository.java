package com.pm.file_uploaddownload.repository;

import com.pm.file_uploaddownload.entity.Assignment;
import com.pm.file_uploaddownload.entity.Student;
import com.pm.file_uploaddownload.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    boolean existsByStudentAndAssignment(Student student, Assignment assignment);

    int countByAssignment(Assignment assignment);

    List<Submission> findByAssignment(Assignment assignment);
}