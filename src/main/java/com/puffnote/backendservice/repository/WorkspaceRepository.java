package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Data repository class used to implement data access on Workspace objects
 */
@RestResource(exported = false)
public interface WorkspaceRepository extends MongoRepository<Workspace,String> {
    /**
     * Find workspace object by name
     * @param name
     * @return Workspace object found
     */
    public Workspace findByName(String name);

    /**
     * Find workspace object by uuid
     * @param uuid
     * @return Workspace object found
     */
    public Workspace findByUuid(String uuid);
}
