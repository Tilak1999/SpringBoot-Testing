package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        // below code is uses mock() method.
//         employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .id(1L)
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();
    }

    // JUnit Test for save Employee method, using mock() method & @Mock annotation
    @DisplayName("JUnit Test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given-precondition or setup

        // Stubbing two methods findById & save of EmployeeServiceImpl class
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);
        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }


    // JUnit Test for save Employee method, which throws exception
    @DisplayName("JUnit Test for save Employee method")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowException() {
        // given-precondition or setup

        // Stubbing two methods findById of EmployeeServiceImpl class
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

//        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when-action or behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method +ve scenario")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployee() {
        // given-precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("cena")
                .email("john@gmail.com")
                .build();
        // stubbing
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when-action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then-verity the result
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method -ve scenario")
    @Test
    public void givenEmptyListOfEmployees_whenGetAllEmployees_thenReturnEmptyListOfEmployee() {
        // given-precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("cena")
                .email("john@gmail.com")
                .build();
        // stubbing
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when-action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then-verity the result
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given-precondition or setup
        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when-action or behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then-verity the result
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for update employee method
    @DisplayName("JUnit test for update employee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given-precondition or setup
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("Basya");
        employee.setEmail("basya@gmail.com");

        // when-action or behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then-verity the result
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Basya");
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("basya@gmail.com");

    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenDeleteEmployee() {
        // given-precondition or setup
        // stubbing for method type void
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(1L);

        // when-action or behaviour that we are going to test
        employeeService.deleteEmployee(1L);

        // then-verity the result
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById(1L);
    }
}
