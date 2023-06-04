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
@Table(name = "departments")
public class Department {

    @Id
    @Column("id")
    private Long id;

    @Column("number")
    private String number;

    @Column("name")
    private String name;
}
