package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


import java.io.IOException;
import java.io.InputStream;

@Component
public class EmployeeDataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/employee_database.json";

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDataBootstrap.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Employee[] employees = null;

        try {
            employees = objectMapper.readValue(inputStream, Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Employee employee : employees) {           
            LOG.debug("Inserting employee [{}] to employeeRepository", employee.getEmployeeId());
            employeeRepository.insert(employee);
        }

    }
}
