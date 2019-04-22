package com.collab.backendservice.repository;

import com.collab.backendservice.model.Metrics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-04-19
 */

/**
 * Data repository class used to implement data access on Metrics object
 */
@RestResource(exported = false)
public interface MetricsRepository extends MongoRepository<Metrics,String> {
    /**
     * Find metrics object by unique index
     * @param uniqueIndex
     * @return Metrics object found
     */
    public Metrics findByUniqueIndex(String uniqueIndex);

}
