package org.babu.springboottest.service;

import org.assertj.core.api.Assertions;
import org.babu.springboottest.exception.ResourceNotFoundException;
import org.babu.springboottest.model.Employee;
import org.babu.springboottest.repository.EmployeeRepository;
import org.babu.springboottest.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;


    @BeforeEach
    public void setup() {
         employee = Employee.builder()
                .Id(1)
                .firstName("Baburam")
                .lastName("Neupane")
                .email("abc@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Junit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("Junit test for saveEmployee method should throw exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Junit test for getAll Employee method")
    public void givenEmployeeList_whenGetAllEmployee_thenReturnAllEmployee() {

        Employee employee1 = Employee.builder()
                .Id(2)
                .firstName("John")
                .lastName("sina")
                .email("abc@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        Assertions.assertThat(employeeService.getAllEmployee()).isNotNull();
        Assertions.assertThat(employeeService.getAllEmployee().size()).isEqualTo(2);
    }
}
