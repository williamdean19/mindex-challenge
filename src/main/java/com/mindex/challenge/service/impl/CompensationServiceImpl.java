package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationService.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(Compensation compensation) {
        if(validateCreate(compensation)==0){
            LOG.debug("Creating compensation for employee [{}]", compensation.getEmployee().getEmployeeId());
            
            compensation.setCompensationId(UUID.randomUUID().toString());
            compensationRepository.insert(compensation);

            return compensation;
        }
        else{
            throw new RuntimeException("Validation failed.");
        }
    }

    @Override
    public Compensation lookupCurrent(String employeeId) {
        // will not return a compensation with an effective date in the future.
        LOG.debug("Looking up current compensation for employeeId [{}]", employeeId);

        List<Compensation> compensationList = compensationRepository.findByEmployee(employeeId);
        compensationList.sort((c1, c2) -> c1.getEffectiveDate().compareTo(c2.getEffectiveDate()));

        Date currentDate = new Date();
        for(int i=compensationList.size()-1; i>=0; i--){
            if(compensationList.get(i).getEffectiveDate().compareTo(currentDate)<=0){
                return compensationList.get(i);
            }
        }
        LOG.debug("No current compensation found for employeeId [{}]", employeeId);
        return null;
    }

    @Override
    public List<Compensation> lookupAll(String employeeId) {
        LOG.debug("Looking up all compensation records for employeeId [{}]", employeeId);
        
        List<Compensation> compensationList = compensationRepository.findByEmployee(employeeId);
        if(compensationList == null){
            throw new RuntimeException("No compensation records found for: " + employeeId);
        }

        return compensationList;
    }

    @Override
    public Compensation lookupByCompensationId(String compensationId) {
        LOG.debug("Looking up compensation with compensationId [{}]", compensationId);
        
        return compensationRepository.findByCompensationId(compensationId);

    }

    @Override
    public int validateCreate(Compensation compensation) {
        LOG.debug("Validating compensation data for compensation [{}]", compensation);
        if (compensation.getEmployee() == null) {
            throw new RuntimeException("Invalid employee: null");
        }

        if (employeeService.read(compensation.getEmployee().getEmployeeId()) == null) {
            throw new RuntimeException("Save employee to the database first.");
        }

        if (compensation.getSalary() == null) {
            throw new RuntimeException("Invalid salary: null");
        }

        if (compensation.getEffectiveDate() == null) {
            throw new RuntimeException("Invalid effectiveDate: null");
        }

        return 0;
    }
}