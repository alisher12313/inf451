package com.pm.file_uploaddownload.service;

import com.pm.file_uploaddownload.dto.AssignmentRequestDto;
import com.pm.file_uploaddownload.dto.AssignmentResponseDto;
import com.pm.file_uploaddownload.entity.Assignment;
import com.pm.file_uploaddownload.entity.Student;
import com.pm.file_uploaddownload.entity.Teacher;
import com.pm.file_uploaddownload.repository.AssignmentRepository;
import com.pm.file_uploaddownload.repository.StudentRepository;
import com.pm.file_uploaddownload.repository.SubmissionRepository;
import com.pm.file_uploaddownload.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.pm.file_uploaddownload.Mapper.AssignmentMapper.toEntity;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    public Map<String, String> createAssignment(UUID teacherUUID, AssignmentRequestDto requestDto){
        Teacher teacher = teacherRepository.findById(teacherUUID)
                .orElseThrow();
        Set<Student> students = requestDto.getStudentIds()
                .stream()
                .map(ids ->
                        studentRepository
                                .findById(ids)
                                .orElseThrow())
                .collect(Collectors.toSet());


        Assignment assignment = toEntity(requestDto, students, teacher);

        assignmentRepository.save(assignment);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Assignment uploaded successfully");
        response.put("info", getAssignment(assignment.getId()).toString());
        return response;
    }

    public List<AssignmentResponseDto> getAllAssignments(){
        List<Assignment> list = assignmentRepository.findAll();
        List<AssignmentResponseDto> dtos = new ArrayList<>();
        for(Assignment a : list){
            int count = submissionRepository.countByAssignment(a);
            AssignmentResponseDto dto = AssignmentResponseDto.builder().id(a.getId())
                    .title(a.getTitle())
                    .dueDate(LocalDate.from(a.getDueDate()))
                    .status(a.getStatus().name())
                    .description(a.getDescription())
                    .totalStudents(a.getStudentSet().size())
                    .submittedCount(count)
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    private AssignmentResponseDto getAssignment(UUID assignmentId){
        Assignment a = assignmentRepository.findById(assignmentId).orElse(null);

        int count = submissionRepository.countByAssignment(a);

        return AssignmentResponseDto.builder().id(a.getId())
                .title(a.getTitle())
                .dueDate(LocalDate.from(a.getDueDate()))
                .status(a.getStatus().name())
                .description(a.getDescription())
                .totalStudents(a.getStudentSet().size())
                .submittedCount(count)
                .build();
    }

    public List<AssignmentResponseDto> getAssignmentsByTeacher(UUID uuid){
        List<Assignment> list = assignmentRepository.findByTeacherId(uuid);
        List<AssignmentResponseDto> dtos = new ArrayList<>();
        for(Assignment a : list){
            int count = submissionRepository.countByAssignment(a);
            AssignmentResponseDto dto = AssignmentResponseDto.builder().id(a.getId())
                    .title(a.getTitle())
                    .dueDate(LocalDate.from(a.getDueDate()))
                    .status(a.getStatus().name())
                    .description(a.getDescription())
                    .totalStudents(a.getStudentSet().size())
                    .submittedCount(count)
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

}
