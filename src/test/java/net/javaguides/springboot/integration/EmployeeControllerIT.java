package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    // Junit test for POST employees REST API
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then-verity the result
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    // JUnit test for GET All Employee REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {
        // given-precondition or setup
        List<Employee> listOfEmployee = new ArrayList<>();
        listOfEmployee.add(Employee.builder().firstName("Suresh").lastName("basya").email("suresh@gmail.com").build());
        listOfEmployee.add(Employee.builder().firstName("Undertaker").lastName("cina").email("undertaker@gmail.com").build());
        employeeRepository.saveAll(listOfEmployee);

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployee.size())));
    }

    // +ve Scenario - valid employee ID
    // JUnit test for GET All Employee REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given-precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employee.getId()));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    // -ve Scenario - valid employee ID
    // JUnit test for GET All Employee REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given-precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employeeId));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    // +ve Scenario
    // Junit test for update employee REST API
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
        // given-precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // below info will be updated
        Employee updatedEmployee = Employee.builder()
                .firstName("Undertaker")
                .lastName("Bob")
                .email("undertaker@gmail.com")
                .build();

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));

    }

    //  -ve Scenario
    // Junit test for update employee REST API
    @DisplayName("Junit test for update employee REST API -ve")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given-precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Suresh")
                .lastName("basya")
                .email("suresh@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // below info will be updated
        Employee updatedEmployee = Employee.builder()
                .firstName("Undertaker")
                .lastName("Bob")
                .email("undertaker@gmail.com")
                .build();

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", updatedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    // JUnit test for delete employee REST API
    @DisplayName("JUnit test for delete employee REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given-precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Undertaker")
                .lastName("Bob")
                .email("undertaker@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // when-action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", savedEmployee.getId()));

        // then-verity the result
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
