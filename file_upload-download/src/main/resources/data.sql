CREATE TABLE teacher (
                         id UUID PRIMARY KEY,
                         name VARCHAR(255),
                         surname VARCHAR(255),
                         email VARCHAR(255)
);

CREATE TABLE student (
                         id UUID PRIMARY KEY,
                         name VARCHAR(255),
                         surname VARCHAR(255),
                         email VARCHAR(255)
);

CREATE TABLE assignment (
                            id UUID PRIMARY KEY,
                            title VARCHAR(255),
                            description VARCHAR(1000),
                            due_date TIMESTAMP,
                            teacher_id UUID,
                            FOREIGN KEY (teacher_id) REFERENCES teacher(id)
);

CREATE TABLE assignment_student (
                                    assignment_id UUID,
                                    student_id UUID,
                                    PRIMARY KEY (assignment_id, student_id),
                                    FOREIGN KEY (assignment_id) REFERENCES assignment(id),
                                    FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Teachers
INSERT INTO teacher (id, name, surname, email) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Alice', 'Smith', 'alice.smith@example.com');

-- Students
INSERT INTO student (id, name, surname, email) VALUES
                                                   ('22222222-2222-2222-2222-222222222222', 'Charlie', 'Brown', 'charlie.brown@example.com'),
                                                   ('33333333-3333-3333-3333-333333333333', 'Diana', 'White', 'diana.white@example.com');

