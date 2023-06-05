INSERT INTO departments (number, name) VALUES ('470', 'Энерго-механический отдел');
INSERT INTO departments (number, name) VALUES ('476', 'Сантехнический отдел');
INSERT INTO departments (number, name) VALUES ('480', 'Ремонтно-строительный участок');
INSERT INTO departments (number, name) VALUES ('820', 'Отдел охраны труда');

INSERT INTO employees (firstname, lastname, patronymic, birthdate, telephone_number, email)
VALUES ('Щукина', 'Нонна', 'Лаврентьевна', '1978-04-10', '+7 (918) 527-83-38', 'nonna.ukina@outlook.com');

INSERT INTO employees (firstname, lastname, patronymic, birthdate, telephone_number, email)
VALUES ('Жириновская', 'Ася', 'Прокловна', '1980-05-01', '+7 (944) 475-90-98', 'asya1980@rambler.ru');

INSERT INTO vacancies (position, salary, department_id, employee_personnel_number)
VALUES ('Начальник отдела', 100, 1, 1);
INSERT INTO vacancies (position, salary, department_id)
VALUES ('Старший мастер', 80, 1);
INSERT INTO vacancies (position, salary, department_id) VALUES ('Мастер', 60, 1);

INSERT INTO vacancies (position, salary, department_id) VALUES ('Начальник отдела', 100, 2);
INSERT INTO vacancies (position, salary, department_id) VALUES ('Старший мастер', 80, 2);
INSERT INTO vacancies (position, salary, department_id) VALUES ('Мастер', 60, 2);

INSERT INTO vacancies (position, salary, department_id) VALUES ('Начальник отдела', 100, 3);
INSERT INTO vacancies (position, salary, department_id) VALUES ('Старший мастер', 80, 3);
INSERT INTO vacancies (position, salary, department_id) VALUES ('Мастер', 60, 3);

