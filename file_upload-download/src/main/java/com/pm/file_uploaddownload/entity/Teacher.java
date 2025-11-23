package com.pm.file_uploaddownload.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String surname;
    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Assignment> assignments;
}

