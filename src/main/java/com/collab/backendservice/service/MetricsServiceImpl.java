package com.collab.backendservice.service;

import com.collab.backendservice.model.Metrics;
import com.collab.backendservice.repository.MetricsRepository;
import com.collab.backendservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by karthik on 2019-04-19
 */
@Service
@Slf4j
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    public Iterable listAll() {
        return metricsRepository.findAll();
    }

    @Override
    public Metrics findById(String id) {
        return metricsRepository.findById(id).orElse(null);
    }

    @Override
    public Metrics findByUniqueIndex(String uniqueIndex) {
        return metricsRepository.findByUniqueIndex(uniqueIndex);
    }

    @Override
    public Metrics saveOrUpdate(Metrics document) {
        metricsRepository.save(document);
        log.info("Updated Metrics Document: " + document);
        return document;
    }

    @Override
    public void incrementUserMetric() {
        Metrics metricObject = metricsRepository.findByUniqueIndex(Constants.METRICS_UNIQUE_INDEX);
        metricObject.incrementUserValue();
        metricsRepository.save(metricObject);
    }

    @Override
    public void incrementWorkspaceMetric() {
        Metrics metricObject = metricsRepository.findByUniqueIndex(Constants.METRICS_UNIQUE_INDEX);
        metricObject.incrementWorkspaceValue();
        metricsRepository.save(metricObject);
    }
}
