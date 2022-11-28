package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("compensation/{EMPLOYEE_ID}")
    public Compensation create(@PathVariable final String EMPLOYEE_ID, @RequestBody final Compensation COMPENSATION) {
        LOG.debug("Received compensation create request for id [{}] and compensation [{}]", EMPLOYEE_ID, COMPENSATION);
        try{
            int validateCreate = compensationService.validateCreate(COMPENSATION);
            if(validateCreate == 0){
                return compensationService.create(COMPENSATION);
            }
            else{
                throw new RuntimeException("Validation failed with error code: " + validateCreate);
            }
        }
        catch(Exception e){
            throw new RuntimeException("Error creating compensation: " + e.getMessage());
        }
    }

    @GetMapping("/current-compensation/{EMPLOYEE_ID}")
    public Compensation currentCompensation (@PathVariable final String EMPLOYEE_ID) {
        LOG.debug("Received current compensation read request for id [{}]", EMPLOYEE_ID);

        return compensationService.lookupCurrent(EMPLOYEE_ID);
    }

    @GetMapping("/compensation-history/{EMPLOYEE_ID}") 
    public List<Compensation> compensationHistory(@PathVariable final String EMPLOYEE_ID) {
        LOG.debug("Received compensation history read request for id [{}]", EMPLOYEE_ID);

        return compensationService.lookupAll(EMPLOYEE_ID);
    }

}
