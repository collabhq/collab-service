package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */
@RestResource(exported = false)
public interface UserRepository extends MongoRepository<User,String> {
    public User findByName(String name);
    public User findByUuid(String uuid);
}

