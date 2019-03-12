package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by karthik on 2019-03-11
 */
public interface UserRepository extends MongoRepository<User,String> {
    public User findByName(String name);
    public User findByUuid(String uuid);
}

