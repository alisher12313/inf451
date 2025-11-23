package com.pm.file_uploaddownload.Mapper;

import com.pm.file_uploaddownload.dto.AssignmentRequestDto;
import com.pm.file_uploaddownload.entity.Assignment;
import com.pm.file_uploaddownload.entity.Student;
import com.pm.file_uploaddownload.entity.Teacher;

import java.util.Set;
import java.util.stream.Collectors;

public class AssignmentMapper {
    public static AssignmentRequestDto toDTO(Assignment assignment){
        AssignmentRequestDto dto = new AssignmentRequestDto();

        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        dto.setStudentIds(assignment
                .getStudentSet()
                .stream()
                .map(Student::getId)
                .collect(Collectors.toSet()));

        return dto;
    }

    public static Assignment toEntity(AssignmentRequestDto dto, Set<Student> students, Teacher teacher){
        Assignment assignment = new Assignment();

        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        assignment.setStudentSet(students);
        assignment.setTeacher(teacher);

        return assignment;
    }
}
