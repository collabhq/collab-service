package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Metrics;
import com.puffnote.backendservice.repository.MetricsRepository;
import com.puffnote.backendservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by karthik on 2019-04-19
 */
@Service
public class MetricsServiceImpl implements MetricsService {
    private static final Logger logger = LoggerFactory.getLogger(MetricsServiceImpl.class);

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
        logger.info("Updated Metrics Document: " + document);
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
