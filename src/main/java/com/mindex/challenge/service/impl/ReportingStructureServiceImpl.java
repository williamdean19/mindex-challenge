package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    /*Service Implementation Methods */
    @Override
    public String visualize(ReportingStructure reportingStructure) {
        Employee employee = employeeService.read(reportingStructure.getEmployee().getEmployeeId());
        LOG.debug("Visualizing reporting structure for employee [{}]", employee.computeEmployeeIdAndFullName());
  
        if(employee.getDirectReports() == null){
            return employee.computeFullName() + " has no direct reports.";
        }
        else{
            int tabCount = 0;
            StringBuilder vizBuilder = new StringBuilder();
            vizBuilder.append(employee.computeFullName() + " has " + employee.getDirectReports().size() + " direct reports and " + countTotalReports(reportingStructure) + " total reports.");
            vizBuilder.append(System.lineSeparator());
            vizBuilder.append(employee.computeFullName() + "'s full reporting structure is:");
            vizBuilder.append(System.lineSeparator());
            return visualizationHandler(employee, vizBuilder, tabCount);
        }

    }
    @Override
    public int countTotalReports(ReportingStructure reportingStructure) {
        Employee supervisor = employeeService.read(reportingStructure.getEmployee().getEmployeeId());
        
        LOG.debug("Counting total reports for supervisor [{}]", supervisor.computeFullName());
        int empCount = 0;
        return countHandler(supervisor, empCount);
    }

    /*Recursive Handlers*/
    private String visualizationHandler(Employee supervisor, StringBuilder vizBuilder, int tabsFromRoot) {
        LOG.debug("Handling reporting structure visualization for supervisor [{}]", supervisor.computeFullName());        
        if(tabsFromRoot == 0){
            vizBuilder.append(supervisor.computeFullName());
            vizBuilder.append(System.lineSeparator());
        }
        tabsFromRoot++;
        for (Employee subordinate : supervisor.getDirectReports()) {
            subordinate = employeeService.read(subordinate.getEmployeeId());
            for(int i = 0; i < tabsFromRoot; i++) {
                vizBuilder.append("\t");
            }
            vizBuilder.append(subordinate.computeFullName());
            vizBuilder.append(System.lineSeparator());     
            if (subordinate.getDirectReports() != null) {
                visualizationHandler(subordinate, vizBuilder, tabsFromRoot);
            }
        }
        return vizBuilder.toString();

    }

    private int countHandler(Employee supervisor, int empCount) {
        LOG.debug("Counting employees for supervisor with id [{}]", supervisor.computeFullName());
        
        if(supervisor.getDirectReports() == null){
            return empCount;
        }
        else{
            for(Employee subordinate : supervisor.getDirectReports()){
                subordinate = employeeService.read(subordinate.getEmployeeId());
                empCount++;
                empCount = countHandler(subordinate, empCount);
            }
            return empCount;
        }
    }    
}