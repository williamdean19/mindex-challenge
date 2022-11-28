package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {
    String visualize(ReportingStructure reportingStructure);
    int countTotalReports(ReportingStructure reportingStructure);
}
