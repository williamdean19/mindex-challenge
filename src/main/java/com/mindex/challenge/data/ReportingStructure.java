package com.mindex.challenge.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportingStructure {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructure.class);    
    private Employee employee;
    private int totalReports;

    public ReportingStructure(){
    }

    public ReportingStructure(Employee employee){
        this.employee = employee;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public int getTotalReports(){
        if (this.employee.getDirectReports() == null){
            return 0;
        }
        else {
            return this.totalReports;
        }
            
    }

    public void setTotalReports(int numberOfReports){  
        if (this.employee == null){
            LOG.debug("Employee is null, cannot set total reports");
        }
        if (numberOfReports < 0){
            throw new IllegalArgumentException("Number of reports cannot be negative.");
        }
        else {
            int previousTotal = this.totalReports;
            int newTotal = numberOfReports;

            this.totalReports = numberOfReports;
            LOG.debug("Number of reports has changed from " + previousTotal + " to " + newTotal);
        }
    }

    }

