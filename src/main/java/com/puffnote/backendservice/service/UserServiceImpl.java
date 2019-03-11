package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.UserRepository;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository UserRepository;

    @Override
    public Iterable listAll() {
        return UserRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return UserRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return UserRepository.findByName(name);
    }

    @Override
    public User saveOrUpdate(User user) {
        UserRepository.save(user);
        logger.info("Updated User: " + user);
        return user;
    }

    @Override
    public void delete(User user) {
        UserRepository.delete(user);
        logger.info("Deleted User: " + user);
    }

    @Override
    public void deleteById(String id) {
        UserRepository.deleteById(id);
        logger.info("Deleted User with Id: " + id);
    }

    @Override
    public boolean existsById(String id) {
        if(UserRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        UserRepository.deleteAll();
        logger.info("Deleted All Users");
    }

}
