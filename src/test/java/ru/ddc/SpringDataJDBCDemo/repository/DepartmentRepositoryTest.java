package ru.ddc.SpringDataJDBCDemo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import ru.ddc.SpringDataJDBCDemo.model.Department;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = {"/create_em_schema.sql", "/import_em.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        scripts = {"/drop_em_schema.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@SpringBootTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @DisplayName("Метод findAll() должен получить 4 отдела, добавленные перед запуском теста")
    @Test
    public void whenFindAllThenListSizeEqualsFour() {
        List<Department> departmentList = departmentRepository.findAll();
        assertEquals(4, departmentList.size());
    }

    @DisplayName("Метод findById(id) должен вернуть отдел с тем же id")
    @Test
    public void whenFindByIdThenIdEqualsDepartmentId() {
        long id = 1L;
        Department department = departmentRepository.findById(id).orElseThrow();
        assertEquals(id, department.getId());
    }

    @DisplayName("Метод findById(id) должен вернуть пустой Optional если строки с таким id не существует")
    @Test
    public void whenIdNotExistThenThrowException() {
        long id = Long.MAX_VALUE;
        assertEquals(Optional.empty(), departmentRepository.findById(id));
    }

    @DisplayName("Метод count() должен вернуть 4 - количество отделов, добавленных перед запуском")
    @Test
    public void whenCountThenEqualsFour() {
        long count = departmentRepository.count();
        assertEquals(4, count);
    }

    @DisplayName("После выполнения метода save() отделов должно стать на 1 больше")
    @Test
    public void whenSaveThenShouldBeOneMoreDepartment() {
        long departmentsCountBeforeSave = departmentRepository.count();
        Department department = Department.builder().build();
        departmentRepository.save(department);
        long departmentsCountAfterSave = departmentRepository.count();
        assertEquals(1, departmentsCountAfterSave - departmentsCountBeforeSave);
    }

    @DisplayName("После выполнения метода save() все поля должны сохраниться")
    @Test
    public void whenSaveThenAllFieldsEqualsSpecifiedOnes() {
        Department department = Department.builder().name("Новый отдел").number("900").build();
        departmentRepository.save(department);
        long count = departmentRepository.count();
        Department department1 = departmentRepository.findById(count).orElseThrow();
        assertTrue(new ReflectionEquals(department, "id").matches(department1));
    }

    @DisplayName("После update() поля должны сохраниться")
    @Test
    public void whenUpdateThenAllFieldsEqualsSpecifiedOnes() {
        Long id = 1L;
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName("Новое имя отдела");
        department.setNumber("Новый номер отдела");
        departmentRepository.update(department);
        Department department1 = departmentRepository.findById(id).orElseThrow();
        assertTrue(new ReflectionEquals(department, "id").matches(department1));
    }

    @DisplayName("При удалении отдела без вакансий отделов должно стать на один меньше")
    @Test
    public void whenDeleteThenShouldBeOneLessDepartment() {
        long countBeforeDelete = departmentRepository.count();
        Long id = 4L;
        departmentRepository.deleteById(id);
        long countAfterDelete = departmentRepository.count();
        assertEquals(1, countBeforeDelete - countAfterDelete);
    }

    @DisplayName("При удалении отдела с вакансиями должно выбросить исключение")
    @Test
    public void whenDeleteDepartmentWithVacanciesThenThrownException() {
        Long id = 3L;
        assertThrows(DataIntegrityViolationException.class, () -> departmentRepository.deleteById(id));
    }
}

