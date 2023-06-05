package ru.ddc.SpringDataJDBCDemo.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.ddc.SpringDataJDBCDemo.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface EmployeeRepository extends Repository<Employee, Long> {

    @Query("SELECT * FROM employees")
    List<Employee> findAll();

    @Query("SELECT * FROM employees WHERE personnel_number = :personnel_number")
    Optional<Employee> findById(@Param("personnel_number") Long personnel_number);

    @Query("SELECT COUNT(*) FROM employees")
    long count();

    @Modifying
    @Query("INSERT INTO employees (firstname, lastname, patronymic, birthdate, email, telephone_number)" +
            "VALUES (:firstname, :lastname, :patronymic, :birthdate, :email, :telephone_number)")
    int save(@Param("firstname") String firstname,
             @Param("lastname") String lastname,
             @Param("patronymic") String patronymic,
             @Param("birthdate")LocalDate birthdate,
             @Param("email") String email,
             @Param("telephone_number") String telephoneNumber);

    default int save(Employee employee) {
        return save(employee.getFirstname(),
                employee.getLastname(),
                employee.getPatronymic(),
                employee.getBirthdate(),
                employee.getEmail(),
                employee.getTelephoneNumber());
    }

    @Modifying
    @Query("UPDATE employees " +
            "SET firstname = :firstname, " +
            "lastname = :lastname," +
            "patronymic = :patronymic," +
            "birthdate = :birthdate," +
            "email = :email," +
            "telephone_number = :telephone_number " +
            "WHERE personnel_number = :personnel_number")
    int update(@Param("personnel_number") Long personnelNumber,
               @Param("firstname") String firstname,
               @Param("lastname") String lastname,
               @Param("patronymic") String patronymic,
               @Param("birthdate") LocalDate birthdate,
               @Param("email") String email,
               @Param("telephone_number") String telephone_number);

    default int update(Employee employee) {
        return update(employee.getPersonnelNumber(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getPatronymic(),
                employee.getBirthdate(),
                employee.getEmail(),
                employee.getTelephoneNumber());
    }

    @Modifying
    @Query("DELETE FROM employees WHERE personnel_number = :personnel_number")
    int deleteById(@Param("personnel_number") Long personnelNumber);
}
