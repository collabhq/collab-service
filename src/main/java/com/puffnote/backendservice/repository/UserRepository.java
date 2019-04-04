package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Data repository class used to implement data access on User objects
 */
@RestResource(exported = false)
public interface UserRepository extends MongoRepository<User,String> {
    /**
     * Find user object by name
     * @param name
     * @return User object found
     */
    public User findByName(String name);

    /**
     * Find User object by uuid
     * @param uuid
     * @return User object found
     */
    public User findByUUID(String uuid);
}

