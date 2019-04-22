package com.collab.backendservice.service;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.User;

import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
public interface UserService {
    /**
     * List all User objects
     * @return All User objects
     */
    Iterable listAll();

    /**
     * Find User object by id
     * @param id
     * @return User object found
     */
    User findById(String id);

    /**
     * Find User object by uuid
     * @param uuid
     * @return User object found
     */
    User findByUuid(String uuid);

    /**
     * Find User object by name
     * @param name
     * @return User object found
     */
    User findByName(String name);

    /**
     * Save or Update User object
     * @param user
     * @return User object saved or updated
     */
    User saveOrUpdate(User user);

    /**
     * Delete User object
     * @param user
     */
    void delete(User user);

    /**
     * Delete User object by id
     * @param id
     */
    void deleteById(String id);

    /**
     * Check if User object exists by id
     * @param id
     * @return
     */
    boolean existsById(String id);

    /**
     * Delete all User objects
     */
    void deleteAll();

    /**
     * Add Note object reference to User object
     * @param user
     * @param note
     */
    void addNoteToUser(User user, Note note);

    /**
     * Add Note object reference to User object by id
     * @param userId
     * @param noteId
     */
    void addNoteToUserById(String userId, String noteId);

    /**
     * Add Note object reference to User object by uuid
     * @param userUuid
     * @param noteUuid
     */
    void addNoteToUserByUuid(String userUuid, String noteUuid);

    /**
     * Remove Note object reference from User object
     * @param user
     * @param note
     */
    void removeNoteFromUser(User user, Note note);

    /**
     * Remove Note object reference from User object by id
     * @param userId
     * @param noteId
     */
    void removeNoteFromUserById(String userId, String noteId);

    /**
     * Remove Note object reference from User object by uuid
     * @param userUuid
     * @param noteUuid
     */
    void removeNoteFromUserByUuid(String userUuid, String noteUuid);

    /**
     * List all User objects referred to in Workspace object by uuid
     * @param uuid
     * @return List of all User objects found
     */
    List<User> listAllUsersByWorkspaceUuid(String uuid);
}
