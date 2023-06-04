package ru.ddc.SpringDataJDBCDemo.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.ddc.SpringDataJDBCDemo.model.Department;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface DepartmentRepository extends Repository<Department, Long> {

    @Query("SELECT id, number, name FROM departments")
    List<Department> findAll();

    @Query("SELECT id, number, name FROM departments WHERE id = :id")
    Optional<Department> findById(@Param("id") Long id);

    @Query("SELECT COUNT(*) FROM departments")
    long count();

    @Modifying
    @Query("INSERT INTO departments (name, number) VALUES (:name, :number)")
    int save(@Param("number") String number, @Param("name") String name);

    default int save (Department department) {
        return save(department.getNumber(), department.getName());
    }

    @Modifying
    @Query("UPDATE departments SET number = :number, name = :name WHERE id = :id")
    int update(@Param("id") Long id, @Param("number") String number, @Param("name") String name);

    default int update(Department department) {
        return update(department.getId(), department.getNumber(), department.getName());
    }

    @Modifying
    @Query("DELETE FROM departments WHERE id = :id")
    int deleteById(@Param("id") Long id);
}
