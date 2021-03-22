package com.sample.service;

import com.sample.entity.Employee;
import com.sample.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employees::add);
        System.out.println("Getting data from DB : " + employees);
        return employees;
    }

    public Optional<Employee> getEmployeeById(int id) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        return employeeData;
    }

    public Employee addEmployee(@RequestBody Employee employee) {
        Employee _employee = employeeRepository
                .save(Employee.builder()
                        .employeeName(employee.getEmployeeName())
                        .email(employee.getEmail())
                        .salary(employee.getSalary())
                        .build());
        return _employee;
    }

    public Boolean updateEmployee(int id, Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        if (employeeData.isPresent()) {
            Employee _employee = employeeData.get();
            _employee.setEmployeeName(employee.getEmployeeName());
            _employee.setEmail(employee.getEmail());
            _employee.setSalary(employee.getSalary());
            employeeRepository.save(_employee);
            return true;
        }
        return false;
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public void deleteEmployees() {
        employeeRepository.deleteAll();
    }
}
