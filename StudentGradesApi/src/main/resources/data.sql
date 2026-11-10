INSERT INTO student_groups(name) VALUES ('IT-21'), ('IT-22'), ('MN-21');

INSERT INTO teachers(full_name) VALUES ('Асанов Бакыт'), ('Иванова Мария'), ('Токтогулов Эрлан');

INSERT INTO subjects(name, credits, teacher_id) VALUES
    ('Java', 6, 1), ('Базы данных', 5, 2), ('Менеджмент', 4, 3);

INSERT INTO grade_types(name) VALUES ('Экзамен'), ('Лабораторная'), ('Контрольная');

INSERT INTO students(full_name, email, group_id) VALUES
    ('Эрназар уулу Жалал', 'jalal@alatoo.edu.kg', 1),
    ('Бекова Айгерим', 'aigerim@alatoo.edu.kg', 1),
    ('Сыдыков Нурлан', 'nurlan@alatoo.edu.kg', 2),
    ('Петров Иван', 'ivan@alatoo.edu.kg', 3);

INSERT INTO grades(student_id, subject_id, type_id, grade, grade_date) VALUES
    (1, 1, 1, 90, '2026-10-10'), (1, 2, 2, 85, '2026-10-12'),
    (2, 1, 1, 70, '2026-10-10'), (2, 3, 3, 65, '2026-10-15'),
    (3, 1, 2, 40, '2026-10-11'), (3, 2, 1, 35, '2026-10-20'),
    (4, 3, 1, 55, '2026-10-21');
