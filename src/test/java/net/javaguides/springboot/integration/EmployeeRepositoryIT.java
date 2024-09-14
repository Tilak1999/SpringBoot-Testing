package net.javaguides.springboot.integration;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT extends AbstractContainerBaseTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    // use below code to refactor the code in all the methods below

//    private Employee employee;
//
//    @BeforeEach
//    public void setup() {
//        employee = Employee.builder()
//                .firstName("Jhon")
//                .lastName("Joe")
//                .email("joe@gmail.com")
//                .build();
//    }


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
        Assertions.assertThat(employeeList.size()).isEqualTo(4);
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

    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("suresh")
                .lastName("suri")
                .email("suri@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test

        // employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then-verity the result
        Assertions.assertThat(employeeOptional).isEmpty();
    }

    // JUnit test for custom query using JPQL index params
    @DisplayName("JUnit test for custom query using JPQL index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("suresh")
                .lastName("suri")
                .email("suri@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "suresh";
        String lastName = "suri";

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using JPQL Named params
    @DisplayName("JUnit test for custom query using JPQL Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNameParam_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Sharan")
                .lastName("basya")
                .email("sharan@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Sharan";
        String lastName = "basya";

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParam(firstName, lastName);

        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with index params
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Stallin")
                .lastName("ram")
                .email("stalling@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQlNamedParams_thenReturnEmployeeObject() {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Stallin")
                .lastName("basya")
                .email("stallin@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParam(employee.getFirstName(), employee.getLastName());

        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
