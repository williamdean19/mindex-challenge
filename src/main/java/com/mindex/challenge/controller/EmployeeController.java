package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);
       
        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @PutMapping("/employee/{supervisorId}/add-direct-report?{subordinateId}")
    public Employee addDirectReport(@PathVariable String supervisorId, @PathVariable String subordinateId) {
        LOG.debug("Received request to add subordinate with employee ID [{}] to the list of direct reports for supervisor with employee ID [{}]", supervisorId, subordinateId);

        Employee supervisor = employeeService.read(supervisorId);
        Employee subordinate = employeeService.read(subordinateId);
        supervisor.addDirectReport(subordinate);
        return employeeService.update(subordinate);
    }

}
