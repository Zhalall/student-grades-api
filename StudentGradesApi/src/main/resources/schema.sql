CREATE TABLE student_groups (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE students (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    group_id  INT NOT NULL,
    CONSTRAINT fk_students_group FOREIGN KEY (group_id) REFERENCES student_groups(id)
);

CREATE TABLE teachers (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL
);

CREATE TABLE subjects (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    credits    INT NOT NULL CHECK (credits BETWEEN 1 AND 10),
    teacher_id INT NOT NULL,
    CONSTRAINT fk_subjects_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

CREATE TABLE grade_types (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE grades (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    type_id    INT NOT NULL,
    grade      INT NOT NULL CHECK (grade BETWEEN 0 AND 100),
    grade_date DATE NOT NULL,
    CONSTRAINT fk_grades_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_grades_subject FOREIGN KEY (subject_id) REFERENCES subjects(id),
    CONSTRAINT fk_grades_type    FOREIGN KEY (type_id)    REFERENCES grade_types(id)
);
