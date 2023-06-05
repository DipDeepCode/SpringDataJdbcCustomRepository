package ru.ddc.SpringDataJDBCDemo.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.ddc.SpringDataJDBCDemo.model.Vacancy;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface VacancyRepository extends Repository<Vacancy, Long> {

    @Query("SELECT " +
            "v.id AS id, " +
            "v.position AS position, " +
            "v.salary AS salary, " +
            "e.personnel_number AS employee_personnel_number, " +
            "e.firstname AS employee_firstname, " +
            "e.lastname AS employee_lastname, " +
            "e.patronymic AS employee_patronymic, " +
            "e.birthdate AS employee_birthdate, " +
            "e.email AS employee_email, " +
            "e.telephone_number AS employee_telephone_number, " +
            "d.id AS department_id, " +
            "d.number AS department_number, " +
            "d.name AS department_name " +
            "FROM vacancies v " +
            "LEFT OUTER JOIN employees e ON e.personnel_number = v.employee_personnel_number " +
            "LEFT OUTER JOIN departments d ON d.id = v.department_id")
    List<Vacancy> findAll();

    @Query("SELECT " +
            "v.id AS id, " +
            "v.position AS position, " +
            "v.salary AS salary, " +
            "e.personnel_number AS employee_personnel_number, " +
            "e.firstname AS employee_firstname, " +
            "e.lastname AS employee_lastname, " +
            "e.patronymic AS employee_patronymic, " +
            "e.birthdate AS employee_birthdate, " +
            "e.email AS employee_email, " +
            "e.telephone_number AS employee_telephone_number, " +
            "d.id AS department_id, " +
            "d.number AS department_number, " +
            "d.name AS department_name " +
            "FROM vacancies v " +
            "LEFT OUTER JOIN employees e ON e.personnel_number = v.employee_personnel_number " +
            "LEFT OUTER JOIN departments d ON d.id = v.department_id " +
            "WHERE v.id = :id")
    Optional<Vacancy> findById(@Param("id") Long id);

    @Query("SELECT " +
            "v.id AS id, " +
            "v.position AS position, " +
            "v.salary AS salary, " +
            "e.personnel_number AS employee_personnel_number, " +
            "e.firstname AS employee_firstname, " +
            "e.lastname AS employee_lastname, " +
            "e.patronymic AS employee_patronymic, " +
            "e.birthdate AS employee_birthdate, " +
            "e.email AS employee_email, " +
            "e.telephone_number AS employee_telephone_number, " +
            "d.id AS department_id, " +
            "d.number AS department_number, " +
            "d.name AS department_name " +
            "FROM vacancies v " +
            "LEFT OUTER JOIN employees e ON e.personnel_number = v.employee_personnel_number " +
            "LEFT OUTER JOIN departments d ON d.id = v.department_id " +
            "WHERE d.id = :department_id")
    List<Vacancy> findByDepartment_Id(@Param("department_id") Long department_id);

    @Query("SELECT " +
            "v.id AS id, " +
            "v.position AS position, " +
            "v.salary AS salary, " +
            "e.personnel_number AS employee_personnel_number, " +
            "e.firstname AS employee_firstname, " +
            "e.lastname AS employee_lastname, " +
            "e.patronymic AS employee_patronymic, " +
            "e.birthdate AS employee_birthdate, " +
            "e.email AS employee_email, " +
            "e.telephone_number AS employee_telephone_number, " +
            "d.id AS department_id, " +
            "d.number AS department_number, " +
            "d.name AS department_name " +
            "FROM vacancies v " +
            "LEFT OUTER JOIN employees e ON e.personnel_number = v.employee_personnel_number " +
            "LEFT OUTER JOIN departments d ON d.id = v.department_id " +
            "WHERE d.number = :department_number")
    List<Vacancy> findByDepartment_Number(@Param("department_number") String department_number);

    @Query("SELECT " +
            "v.id AS id, " +
            "v.position AS position, " +
            "v.salary AS salary, " +
            "e.personnel_number AS employee_personnel_number, " +
            "e.firstname AS employee_firstname, " +
            "e.lastname AS employee_lastname, " +
            "e.patronymic AS employee_patronymic, " +
            "e.birthdate AS employee_birthdate, " +
            "e.email AS employee_email, " +
            "e.telephone_number AS employee_telephone_number, " +
            "d.id AS department_id, " +
            "d.number AS department_number, " +
            "d.name AS department_name " +
            "FROM vacancies v " +
            "LEFT OUTER JOIN employees e ON e.personnel_number = v.employee_personnel_number " +
            "LEFT OUTER JOIN departments d ON d.id = v.department_id " +
            "WHERE e.personnel_number = :personnel_number")
    List<Vacancy> findByEmployeePersonnelNumber(@Param("personnel_number") Long personnelNumber);

    @Query("SELECT COUNT(*) FROM vacancies")
    long count();

    @Modifying
    @Query("UPDATE vacancies " +
            "SET employee_personnel_number = null " +
            "WHERE id = :id AND employee_personnel_number = :employee_personnel_number")
    int detachEmployeeByVacancyIdAndPersonnelNumber(@Param("id") Long id,
                                                    @Param("employee_personnel_number") Long employeePersonnelNumber);

    @Modifying
    @Query("INSERT INTO vacancies (position, salary, department_id) VALUES (:position, :salary, :department_id)")
    int save(@Param("position") String position,
             @Param("salary") float salary,
             @Param("department_id") Long departmentId);

    default int save(Vacancy vacancy) {
        return save(vacancy.getPosition(), vacancy.getSalary(), null);
    }

    default int save(Vacancy vacancy, Long departmentId) {
        return save(vacancy.getPosition(), vacancy.getSalary(), departmentId);
    }

    @Modifying
    @Query("UPDATE vacancies " +
            "SET position = :position, " +
            "salary = :salary, " +
            "department_id = :department_id, " +
            "employee_personnel_number = :employee_personnel_number " +
            "WHERE vacancies.id = :id")
    int update(@Param("id") Long id,
               @Param("position") String position,
               @Param("salary") float salary,
               @Param("department_id") Long departmentId,
               @Param("employee_personnel_number") Long employeePersonnelNumber);

    default int update(Vacancy vacancy) {
        return update(vacancy.getId(),
                vacancy.getPosition(),
                vacancy.getSalary(),
                vacancy.getDepartment() == null ? null : vacancy.getDepartment().getId(),
                vacancy.getEmployee() == null ? null : vacancy.getEmployee().getPersonnelNumber());
    }

    @Modifying
    @Query("DELETE FROM vacancies WHERE id = :id")
    int deleteById(@Param("id") Long id);
}
