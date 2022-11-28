package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employee/{id}/view-reporting-structure")
    public String viewReportingStructure(@PathVariable String id) {
        LOG.debug("Received Reporting Structure view request for ID [{}]", id);

        Employee employee = employeeService.read(id);
        ReportingStructure reportingStructure = new ReportingStructure(employee);
        reportingStructure.setTotalReports(reportingStructureService.countTotalReports(reportingStructure));
        return reportingStructureService.visualize(reportingStructure);   
    }

    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure reportingStructure(@PathVariable String id) {
        LOG.debug("Received Reporting Structure get request for ID [{}]", id);

        Employee employee = employeeService.read(id);
        ReportingStructure reportingStructure = new ReportingStructure(employee);
        reportingStructure.setTotalReports(reportingStructureService.countTotalReports(reportingStructure));
        return reportingStructure;
    }

}
