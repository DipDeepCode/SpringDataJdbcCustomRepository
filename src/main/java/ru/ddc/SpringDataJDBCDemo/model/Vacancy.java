package ru.ddc.SpringDataJDBCDemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @Column("id")
    private Long id;

    @Column("position")
    private String position;

    @Column("salary")
    private float salary;

    private Department department;

    private Employee employee;
}
