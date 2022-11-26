package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    String visualize(ReportingStructure reportingStructure);
    int countTotalReports(ReportingStructure reportingStructure);
}
