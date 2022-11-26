package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public String visualize(ReportingStructure reportingStructure) {
        Employee employee = read(reportingStructure.getEmployee().getEmployeeId());
        LOG.debug("Visualizing reporting structure for employee {}", employee.computeEmployeeIdAndFullName());
  
        if(employee.getDirectReports() == null){
            return employee.computeFullName() + " has no direct reports.";
        }
        else{
            int tabCount = 0;
            int empCount = 0;
            StringBuilder visualization = new StringBuilder();
            visualization.append(employee.computeFullName() + " has " + employee.getDirectReports().size() + " direct reports and " + countHandler(employee, empCount) + " total reports.");
            visualization.append(System.lineSeparator());
            visualization.append(employee.computeFullName() + "'s full reporting structure is:");
            visualization.append(System.lineSeparator());
            return visualizationHandler(employee, visualization, tabCount);
        }

    }
    @Override
    public int countTotalReports(ReportingStructure reportingStructure) {
        Employee supervisor = read(reportingStructure.getEmployee().getEmployeeId());
        
        LOG.debug("Counting total reports in reporting structure for supervisor {}", supervisor.computeFullName());
        int empCount = 0;
        return countHandler(supervisor, empCount);
    }

    private int countHandler(Employee supervisor, int empCount) {
        LOG.debug("Counting employees for supervisor with id {}", supervisor.computeFullName());
        
        if(supervisor.getDirectReports() == null){
            return empCount;
        }
        else{
            for(Employee subordinate : supervisor.getDirectReports()){
                subordinate = read(subordinate.getEmployeeId());
                empCount++;
                LOG.debug("Employee count is now {}", empCount);
                empCount = countHandler(subordinate, empCount);
            }
            return empCount;
        }
    }

    
    private String visualizationHandler(Employee supervisor, StringBuilder visualizationBuilder, int tabsFromRoot) {
        LOG.debug("Handling reporting structure visualization for supervisor {}", supervisor.computeFullName());        
        if(tabsFromRoot == 0){
            visualizationBuilder.append(supervisor.computeFullName());
            visualizationBuilder.append(System.lineSeparator());
        }
        tabsFromRoot++;
        for (Employee subordinate : supervisor.getDirectReports()) {
            subordinate = read(subordinate.getEmployeeId());
            for(int i = 0; i < tabsFromRoot; i++) {
                visualizationBuilder.append("\t");
            }
            visualizationBuilder.append(subordinate.computeFullName());
            visualizationBuilder.append(System.lineSeparator());     
            if (subordinate.getDirectReports() != null) {
                visualizationHandler(subordinate, visualizationBuilder, tabsFromRoot);
            }
        }
        LOG.debug("Phew, no stack overflows! I hope you like it!");
        return visualizationBuilder.toString();

    }
}
