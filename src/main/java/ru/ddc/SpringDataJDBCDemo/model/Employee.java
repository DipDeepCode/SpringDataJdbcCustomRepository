package ru.ddc.SpringDataJDBCDemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employees")
public class Employee {

    @Id
    @Column("personnel_number")
    private Long personnelNumber;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    @Column("patronymic")
    private String patronymic;

    @Column("birthdate")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthdate;

    @Column("email")
    private String email;

    @Column("telephone_number")
    private String telephoneNumber;
}
