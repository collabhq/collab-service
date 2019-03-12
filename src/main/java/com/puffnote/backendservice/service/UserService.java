package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.User;

/**
 * Created by karthik on 2019-03-11
 */
public interface UserService {
    Iterable listAll();
    User findById(String id);
    User findByUuid(String uuid);
    User findByName(String name);
    User saveOrUpdate(User user);
    void delete(User user);
    void deleteById(String id);
    boolean existsById(String id);
    void deleteAll();
    void addNoteToUser(User user, Note note);
    void addNoteToUserById(String userId, String noteId);
    void removeNoteFromUser(User user, Note note);
    void removeNoteFromUserById(String userId, String noteId);
}
