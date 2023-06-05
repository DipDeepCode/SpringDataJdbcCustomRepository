package ru.ddc.SpringDataJDBCDemo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.ddc.SpringDataJDBCDemo.model.Vacancy;

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
class VacancyRepositoryTest {

    @Autowired
    private VacancyRepository vacancyRepository;

    @DisplayName("Метод findAll() должен получить 9 вакансий, добавленных перед запуском теста")
    @Test
    public void whenFindAllThenListSizeEqualsNine() {
        List<Vacancy> vacancyList = vacancyRepository.findAll();
        assertEquals(9, vacancyList.size());
    }

    @DisplayName("Метод findById(id) должен вернуть вакансию с тем же id")
    @Test
    public void whenFindByIdThenIdEqualsVacancyId() {
        Long id = 1L;
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        assertEquals(id, vacancy.getId());
    }

    @DisplayName("Метод findById(id) должен вернуть пустой Optional если строки с таким id не существует")
    @Test
    public void whenIdNotExistThenEmptyOptional() {
        Long id = Long.MAX_VALUE;
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        assertEquals(Optional.empty(), optionalVacancy);
    }

    @DisplayName("Метод findByDepartmentId() должен вернуть 3 - количество вакансий, добавленных в отдел перед запуском теста")
    @Test
    public void whenFindAllByDepartmentIdThenListSizeEqualsThree() {
        Long departmentId = 1L;
        List<Vacancy> vacancyList = vacancyRepository.findByDepartment_Id(departmentId);
        assertEquals(3, vacancyList.size());
    }

    @DisplayName("Метод findAllByDepartmentNumber() должен вернуть 3 - количество вакансий, добавленных в отдел перед запуском теста")
    @Test
    public void whenFindAllByDepartmentNumberThenListSizeEqualsThree() {
        String departmentNumber = "470";
        List<Vacancy> vacancyList = vacancyRepository.findByDepartment_Number(departmentNumber);
        assertEquals(3, vacancyList.size());
    }

    @DisplayName("Метод findByEmployeePersonnelNumber() должен вернуть 1 - количество вакансий, добавленных в отдел перед запуском теста")
    @Test
    public void whenFindByEmployeePersonnelNumberThenListSizeEqualsOne() {
        Long personnelNumber = 1L;
        List<Vacancy> vacancyList = vacancyRepository.findByEmployeePersonnelNumber(personnelNumber);
        assertEquals(1, vacancyList.size());
    }

    @DisplayName("Метод count() должен вернуть 9 - количество вакансий, добавленных перед запуском теста")
    @Test
    public void whenCountThenEqualsNine() {
        long count = vacancyRepository.count();
        assertEquals(9, count);
    }

    @DisplayName("После выполнения метода detachEmployeeByVacancyIdAndPersonnelNumber поле employee_personnel_number равно null")
    @Test
    public void whenDetachEmployeeByVacancyIdAndPersonnelNumberThenEmployeePersonnelNumberEqualsNull() {
        Long personnelNumber = 1L;
        Long id = 1L;
        vacancyRepository.detachEmployeeByVacancyIdAndPersonnelNumber(id, personnelNumber);
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        assertNull(vacancy.getEmployee());
    }

    @DisplayName("После выполнения метода save(vacancy) вакансий должно стать на 1 больше")
    @Test
    public void whenSaveThenShouldBeOneMoreVacancies() {
        long countBeforeSave = vacancyRepository.count();
        Vacancy vacancy = Vacancy.builder().build();
        vacancyRepository.save(vacancy);
        long countAfterSave = vacancyRepository.count();
        assertEquals(1, countAfterSave - countBeforeSave);
    }

    @DisplayName("После выполнения метода save(vacancy) все поля должны сохраниться")
    @Test
    public void whenSaveThenAllFieldsEqualsSpecifiedOnes() {
        Vacancy vacancy = Vacancy.builder()
                .position("position")
                .salary(12345f)
                .build();
        vacancyRepository.save(vacancy);
        long count = vacancyRepository.count();
        Vacancy vacancy1 = vacancyRepository.findById(count).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy, "id").matches(vacancy1));
    }

    @DisplayName("После выполнения метода save(vacancy, departmentId) вакансия должна сохраниться в заданный отдел")
    @Test
    public void whenSaveThenVacancyAddIntoSpecifiedDepartment() {
        Vacancy vacancy = Vacancy.builder().build();
        Long departmentId = 1L;
        vacancyRepository.save(vacancy, departmentId);
        long count = vacancyRepository.count();
        Vacancy vacancy1 = vacancyRepository.findById(count).orElseThrow();
        assertEquals(departmentId, vacancy1.getDepartment().getId());
    }

    @DisplayName("После update() поля должны сохраниться")
    @Test
    public void whenUpdateThenAllFieldsEqualsSpecifiedOnes() {
        Long id = 9L;
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancy.setPosition("Новая должность");
        vacancy.setSalary(1000f);
        vacancyRepository.update(vacancy);
        Vacancy vacancy1 = vacancyRepository.findById(id).orElseThrow();
        assertTrue(new ReflectionEquals(vacancy, "id").matches(vacancy1));
    }

    @DisplayName("При удалении вакансии их должно стать на одну меньше")
    @Test
    public void whenDeleteThenSouldBeOneLessDepartment() {
        long countBeforeDelete = vacancyRepository.count();
        Long id = 1L;
        vacancyRepository.deleteById(id);
        long countAfterDelete = vacancyRepository.count();
        assertEquals(1, countBeforeDelete - countAfterDelete);
    }
}