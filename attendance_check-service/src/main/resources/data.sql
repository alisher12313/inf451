CREATE TABLE student (
                         ID UUID PRIMARY KEY,
                         NAME VARCHAR(255)
);
INSERT INTO course (ID, NAME) VALUES ('11111111-1111-1111-1111-111111111111', 'Machine Learning');
INSERT INTO course (ID, NAME) VALUES ('22222222-2222-2222-2222-222222222222', 'Artificial Intelligence');

INSERT INTO course_session (ID, DAY_OF_WEEK, DATE, START_TIME, END_TIME, COURSE_ID) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'MONDAY', '2025-11-04', '08:30:00', '09:20:00', '11111111-1111-1111-1111-111111111111');

INSERT INTO course_session (ID, DAY_OF_WEEK, DATE, START_TIME, END_TIME, COURSE_ID) VALUES
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'SATURDAY', '2025-11-05', '08:30:00', '09:20:00', '11111111-1111-1111-1111-111111111111');

INSERT INTO course_session (ID, DAY_OF_WEEK, DATE, START_TIME, END_TIME, COURSE_ID) VALUES
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'SATURDAY', '2025-11-04', '09:30:00', '10:20:00', '22222222-2222-2222-2222-222222222222');

INSERT INTO student (ID, NAME) VALUES ('aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa', 'Alice');
INSERT INTO student (ID, NAME) VALUES ('bbbbbbbb-2222-2222-2222-bbbbbbbbbbbb', 'Bob');
INSERT INTO student (ID, NAME) VALUES ('cccccccc-3333-3333-3333-cccccccccccc', 'Charlie');

