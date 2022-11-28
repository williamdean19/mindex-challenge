package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


import java.io.IOException;
import java.io.InputStream;

@Component
public class CompensationDataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/compensation_database.json";
    private static final Logger LOG = LoggerFactory.getLogger(CompensationDataBootstrap.class);
    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Compensation[] compensations = null;

        try {
            compensations = objectMapper.readValue(inputStream, Compensation[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Compensation compensation : compensations) {        
            LOG.debug("Inserting compensation for employee [{}] to compensationRepository", compensation);   
            compensationRepository.insert(compensation);
        }

    }
}
