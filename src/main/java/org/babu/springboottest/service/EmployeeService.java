package org.babu.springboottest.service;

import org.babu.springboottest.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();
}
