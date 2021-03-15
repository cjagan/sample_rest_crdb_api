package com.sample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("junit")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllEmployee() throws Exception {
    /*    this.mockMvc.perform(get("/AllEmployeeList"))
                .andDo(print())
                .andExpect(status().isOk());*/
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void addEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void deleteEmployees() {
    }
}