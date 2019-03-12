package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.UserRepository;

import java.util.Optional;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable listAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User saveOrUpdate(User user) {
        userRepository.save(user);
        logger.info("Updated User: " + user);
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
        logger.info("Deleted User: " + user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
        logger.info("Deleted User with Id: " + id);
    }

    @Override
    public boolean existsById(String id) {
        if(userRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
        logger.info("Deleted All Users");
    }

    @Override
    public void addNoteToUser(User user, Note note) {
        user.getNotesReferences().add(note.getId());
        userRepository.save(user);
        logger.info("Added Note: " + note + " to User: " + user);
    }

    @Override
    public void addNoteToUserById(String userId, String noteId) {
        User user = this.findById(userId);
        user.getNotesReferences().add(noteId);
        userRepository.save(user);
        logger.info("Added Note with Id: " + noteId + " to User: " + user);
    }

    @Override
    public void removeNoteFromUser(User user, Note note) {
        user.getNotesReferences().removeIf(reference -> (reference == note.getId()));
        userRepository.save(user);
        logger.info("Removed Note: " + note + " to User: " + user);
    }

    @Override
    public void removeNoteFromUserById(String userId, String noteId) {
        User user = this.findById(userId);
        user.getNotesReferences().removeIf(reference -> (reference == noteId));
        userRepository.save(user);
        logger.info("Removed Note with Id: " + noteId + " to User: " + user);
    }

}
