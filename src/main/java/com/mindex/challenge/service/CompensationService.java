package com.mindex.challenge.service;

import java.util.List;

import com.mindex.challenge.data.Compensation;


public interface CompensationService {
    Compensation create(Compensation Compensation);
    Compensation lookupCurrent(String employeeId);
    List<Compensation> lookupAll(String employeeId);
    Compensation lookupByCompensationId(String compensationId);
    int validateCreate(Compensation compensation);
}
