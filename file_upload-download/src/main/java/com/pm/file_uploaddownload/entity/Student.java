package com.pm.file_uploaddownload.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String surname;
    private String email;

    @ManyToMany(mappedBy = "studentSet")
    private Set<Assignment> assignments;
}
