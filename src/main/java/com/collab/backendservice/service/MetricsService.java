package com.collab.backendservice.service;

import com.collab.backendservice.model.Metrics;

/**
 * Created by karthik on 2019-04-19
 */
public interface MetricsService {
    /**
     * List all Metrics objects
     * @return All Metrics objects
     */
    Iterable listAll();

    /**
     * Find Metrics object by id
     * @param id
     * @return Metrics object found
     */
    Metrics findById(String id);

    /**
     * Find metrics object by unique index
     * @param uniqueIndex
     * @return Metrics object found
     */
    Metrics findByUniqueIndex(String uniqueIndex);

    /**
     * Save or Update Metrics object
     * @param document
     * @return Metrics object saved or updated
     */
    Metrics saveOrUpdate(Metrics document);

    /**
     * Increment User metric value
     */
    void incrementUserMetric();

    /**
     * Increment Workspace metric value
     */
    void incrementWorkspaceMetric();

}
