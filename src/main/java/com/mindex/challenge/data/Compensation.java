package com.mindex.challenge.data;


import java.util.Date;

import org.springframework.data.mongodb.core.mapping.MongoId;

public class Compensation implements Comparable<Compensation> {
    @MongoId
    private String compensationId;

    private Employee employee;
    private String salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public String getSalary() {
        return salary;
    }
    
    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public int compareTo(Compensation c) {
        return this.getEffectiveDate().compareTo(c.getEffectiveDate());
    }


}
