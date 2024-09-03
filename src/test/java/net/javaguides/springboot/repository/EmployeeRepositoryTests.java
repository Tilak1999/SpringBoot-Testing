package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();

        // when-action or behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then-verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for get all employee operation
    @DisplayName("Junit test for get all employee operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given-precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Jhon")
                .lastName("Joe")
                .email("joe@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Ram")
                .lastName("R")
                .email("ram@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when-action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then-verity the result
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Lia")
                .lastName("Jo")
                .email("lia@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then-verity the result
        Assertions.assertThat(employeeDB).isNotNull();
    }

    // JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Stallin")
                .lastName("stallin")
                .email("stallin@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then-verity the result
        Assertions.assertThat(employeeDB).isNotNull();
    }

    // JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Suresh")
                .lastName("Ramesh")
                .email("suresh@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("suri@gmail.com");
        savedEmployee.setLastName("suri");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then-verity the result
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("suri@gmail.com");
        Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("suri");
    }
}
