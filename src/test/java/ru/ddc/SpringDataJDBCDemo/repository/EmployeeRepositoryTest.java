package ru.ddc.SpringDataJDBCDemo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import ru.ddc.SpringDataJDBCDemo.model.Employee;

import java.time.LocalDate;
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
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @DisplayName("Метод findAll() должен получить 2 сотрудника, добавленных перед запуском теста")
    @Test
    public void whenFindAllThenListSizeEqualsTwo() {
        List<Employee> employeeList = employeeRepository.findAll();
        assertEquals(2, employeeList.size());
    }

    @DisplayName("Метод findById(personnelNumber) должен вернуть сотрудника с тем же personnelNumber")
    @Test
    public void whenFindByIdThenIdEqualsEmployeeId() {
        Long personnelNumber = 1L;
        Employee employee = employeeRepository.findById(personnelNumber).orElseThrow();
        assertEquals(personnelNumber, employee.getPersonnelNumber());
    }

    @DisplayName("Метод findById(personnelNumber) должен вернуть пустой Optional если строки с таким personnelNumber не существует")
    @Test
    public void whenIdNotExistThenEmptyOptional() {
        Long personnelNumber = Long.MAX_VALUE;
        Optional<Employee> optionalEmployee = employeeRepository.findById(personnelNumber);
        assertEquals(Optional.empty(), optionalEmployee);
    }

    @DisplayName("Метод count() должен вернуть 2 - количество сотрудников, добавленных перед запуском")
    @Test
    public void whenCountThenEqualsTwo() {
        long count = employeeRepository.count();
        assertEquals(2, count);
    }

    @DisplayName("После выполнения метода save() сотрудников должно стать на 1 больше")
    @Test
    public void whenSaveThenShouldBeOneMoreEmployees() {
        long countBeforeSave = employeeRepository.count();
        Employee employee = Employee.builder().build();
        employeeRepository.save(employee);
        long countAfterSave = employeeRepository.count();
        assertEquals(1, countAfterSave - countBeforeSave);
    }

    @DisplayName("После выполнения метода save() сотрудников должно стать на 1 больше")
    @Test
    public void whenSaveThenAllFieldsEqualsSpecifiedOnes() {
        Employee employee = Employee.builder()
                .firstname("firstname")
                .lastname("lastname")
                .patronymic("patronymic")
                .birthdate(LocalDate.now())
                .email("email")
                .telephoneNumber("telephoneNumber")
                .build();
        employeeRepository.save(employee);
        long count = employeeRepository.count();
        Employee employee1 = employeeRepository.findById(count).orElseThrow();
        assertTrue(new ReflectionEquals(employee, "personnelNumber").matches(employee1));
    }

    @DisplayName("После update() поля должны сохраниться")
    @Test
    public void whenUpdateThenAllFieldsEqualsSpecifiedOnes() {
        Long personnelNumber = 1L;
        Employee employee = employeeRepository.findById(personnelNumber).orElseThrow();
        employee.setFirstname("Имя");
        employee.setLastname("Фамилия");
        employee.setPatronymic("Отчество");
        employeeRepository.update(employee);
        Employee employee1 = employeeRepository.findById(personnelNumber).orElseThrow();
        assertTrue(new ReflectionEquals(employee, "personnelNumber").matches(employee1));
    }

    @DisplayName("При удалении сотрудника, не закрепленного за вакансией, сотрудников должно стать на один меньше")
    @Test
    public void whenDeleteThenShouldBeOneLessEmployees() {
        long countBeforeDelete = employeeRepository.count();
        Long personnelNumber = 2L;
        employeeRepository.deleteById(personnelNumber);
        long countAfterDelete = employeeRepository.count();
        assertEquals(1, countBeforeDelete - countAfterDelete);
    }

    @DisplayName("При удалении сотрудника, закрепленного за вакансией, должно выбросится исключение")
    @Test
    public void whenDeleteEmployeeWithVacancyThenThrownException() {
        Long personnelNumber = 1L;
        assertThrows(DataIntegrityViolationException.class, () -> employeeRepository.deleteById(personnelNumber));
    }
}