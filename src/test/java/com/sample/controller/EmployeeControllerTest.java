package com.sample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.entity.Employee;
import com.sample.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;





@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("junit")

class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeController employeeController ;


    @MockBean
    private EmployeeService employeeService;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

    }

    @Test
    public void getAllEmployeeTest() throws Exception {
        Employee mockEmployee1 = new Employee("divya","divya@gmail.com",new BigDecimal("7675.0"));
        Employee mockEmployee2 = new Employee("Ayaan","ayaan@gmail.com",new BigDecimal("678.0"));
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(mockEmployee1);
        employeeList.add(mockEmployee2);
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        String URI = "/api/AllEmployeeList";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = this.mapToJson(employeeList);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);

    }

    @Test
    void addEmployeeTest() throws Exception {
        Employee mockEmployee = new Employee("divya","divya@gmail.com",new BigDecimal("7675.0"));
        mockEmployee.setEmployeeId(12);
        String inputJson = this.mapToJson(mockEmployee);
        String URI = "/api/AddEmployee";
        when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(mockEmployee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        Employee mockEmployee = new Employee("akshara","akshara@gmail.com",new BigDecimal("7675.0"));
        mockEmployee.setEmployeeId(10);

        when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(java.util.Optional.of(mockEmployee));
        String URI = "/api/GetEmployee/10";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(mockEmployee);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    public void updateEmployeeTest() throws Exception{
        when(employeeService.updateEmployee(anyInt(), (Employee) any())).thenReturn(anyBoolean());
        mockMvc.perform( MockMvcRequestBuilders
                .put("/api/updateEmployee/{id}", 2)
                .content(objectMapper.writeValueAsString(new Employee( "name", "name@gmail.com", new BigDecimal("7675.0"))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("name@ggmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("7675.0"));
    }

    @Test
    void deleteEmployeeTest() throws Exception{
        Employee mockEmployee = new Employee("akshara","akshara@gmail.com",new BigDecimal("7675.0"));
        mockEmployee.setEmployeeId(10);
        when(employeeService.getEmployeeById(mockEmployee.getEmployeeId())).thenReturn(java.util.Optional.of(mockEmployee));
        doNothing().when(employeeService).deleteEmployee(mockEmployee.getEmployeeId());
        String URI = "/api/DeleteEmployee/10";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isNoContent()).andReturn();
        verify(employeeService, times(1)).deleteEmployee(mockEmployee.getEmployeeId());
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    void deleteEmployeesTest() throws Exception {
        Employee mockEmployee1 = new Employee("divya","divya@gmail.com",new BigDecimal("7675.0"));
        Employee mockEmployee2 = new Employee("Ayaan","ayaan@gmail.com",new BigDecimal("678.0"));
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(mockEmployee1);
        employeeList.add(mockEmployee2);
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        doNothing().when(employeeService).deleteEmployees();
        String URI = "/api/DeleteAllEmployees";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isNoContent()).andReturn();
        verify(employeeService, times(1)).deleteEmployees();
        verifyNoMoreInteractions(employeeService);

    }

    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     */
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}