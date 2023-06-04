CREATE TABLE IF NOT EXISTS departments (
  id BIGSERIAL,
  number VARCHAR UNIQUE,
  name VARCHAR UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employees (
  personnel_number BIGSERIAL,
  firstname VARCHAR,
  lastname VARCHAR,
  patronymic VARCHAR,
  birthdate DATE,
  email VARCHAR,
  telephone_number VARCHAR,
  PRIMARY KEY (personnel_number)
);

CREATE TABLE IF NOT EXISTS vacancies (
  id BIGSERIAL,
  position VARCHAR,
  salary REAL,
  department_id BIGINT,
  employee_personnel_number BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (department_id) REFERENCES departments(id),
  FOREIGN KEY (employee_personnel_number) REFERENCES employees(personnel_number)
);



