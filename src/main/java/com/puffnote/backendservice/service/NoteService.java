package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Note;

import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
public interface NoteService {
    Iterable listAll();
    Note findById(String id);
    Note findByUuid(String uuid);
    Note findByName(String name);
    Note saveOrUpdate(Note note);
    void delete(Note note);
    void deleteById(String id);
    boolean existsById(String id);
    void deleteAll();
    List<Note> listAllNotesByUserUuid(String uuid);
    List<Note> listAllNotesByRoomUuid(String uuid);
}
