package org.babu.springboottest.repository;

import org.babu.springboottest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("select e from Employee e where e.firstName= ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);
}
