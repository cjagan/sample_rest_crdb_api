package com.sample.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Integer employeeId;

    @Column(name="employee_name")
    private String employeeName;

    @Column(name="email")
    private String email;

    @Column(name="salary")
    private BigDecimal salary;

    public Employee(String employeeName, String email, BigDecimal salary) {
        this.employeeName = employeeName;
        this.email = email;
        this.salary = salary;
    }
}
