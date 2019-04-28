package com.collab.backendservice.service;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.model.User;
import com.collab.backendservice.repository.NoteRepository;
import com.collab.backendservice.repository.WorkspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.collab.backendservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

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
    public void addNoteToUserByUuid(String userUuid, String noteUuid) {
        User user = this.findByUuid(userUuid);
        Note note = noteRepository.findByUuid(noteUuid);
        user.getNotesReferences().add(note.getId());
        userRepository.save(user);
        logger.info("Added Note with UUID: " + noteUuid + " to User: " + user);
    }

    @Override
    public void removeNoteFromUser(User user, Note note) {
        user.getNotesReferences().removeIf((String reference) -> note.getId().equals(reference));
        userRepository.save(user);
        logger.info("Removed Note: " + note + " from User: " + user);
    }

    @Override
    public void removeNoteFromUserById(String userId, String noteId) {
        User user = this.findById(userId);
        user.getNotesReferences().removeIf((String reference) -> noteId.equals(reference));
        userRepository.save(user);
        logger.info("Removed Note with Id: " + noteId + " from User: " + user);
    }

    @Override
    public void removeNoteFromUserByUuid(String userUuid, String noteUuid) {
        User user = this.findByUuid(userUuid);
        Note note = noteRepository.findByUuid(noteUuid);
        user.getNotesReferences().removeIf((String reference) -> note.getId().equals(reference));
        userRepository.save(user);
        logger.info("Removed Note with UUID: " + noteUuid + " from User: " + user);
    }

    @Override
    public List<User> listAllUsersByWorkspaceUuid(String uuid) {
        List<User> userList = new ArrayList<User>();
        Workspace workspace = workspaceRepository.findByUuid(uuid);
        for (String userReference : workspace.getUserReferences()) {
            userList.add(this.findById(userReference));
        }
        logger.info("All users by workspace uuid: " + userList);
        return userList;
    }

    @Override
    public boolean isUserPartOfWorkspace(String userUuid, String workspaceUuid) {
        // If user does not exist at all or if user is not part of workspace, return false
        User user;
        user = this.findByUuid(userUuid);
        if(user == null || !user.getWorkspaceUUID().equals(workspaceUuid)) {
            return false;
        }

        return true;
    }

}
