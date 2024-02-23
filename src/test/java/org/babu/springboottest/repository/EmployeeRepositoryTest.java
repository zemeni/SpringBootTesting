package org.babu.springboottest.repository;

import org.babu.springboottest.model.Employee;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    private Employee employee;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Neupane")
                .email("abc@gmail.com")
                .build();
    }

    //JUnit test for save employee operation
    @Test
    @DisplayName("JUnit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given - precondition

        //when - action or the behaviour
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JUnit test for get all employee operation")
    public void givenEmployeeList_whenFindAll_thenReturnAllEmployee(){


        Employee employee2 = Employee.builder()
                .firstName("Ramesh")
                .lastName("Neupane")
                .email("abc@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test to find employee by id")
    public void givenEmployeeObject_whenFindById_thenReturnEmployee() {

        Employee savedEmployee = employeeRepository.save(employee);

        Employee employeeDB = employeeRepository.findById(employee.getId()).orElseThrow(() -> new ObjectNotFoundException(employee, "not found"));

        assertThat(employeeDB.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for custom query with jpql")
    public void givenFistNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        employeeRepository.save(employee);

        String firstName = "Ramesh";
        String lastName = "Neupane";

        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        assertThat(savedEmployee).isNotNull();
    }
}
