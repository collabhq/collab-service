package com.collab.backendservice.service;

import com.collab.backendservice.model.Note;

import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
public interface NoteService {
    /**
     * List all Note objects
     * @return All Note objects
     */
    Iterable listAll();

    /**
     * Find Note object by id
     * @param id
     * @return Note object found
     */
    Note findById(String id);

    /**
     * Find Note object by uuid
     * @param uuid
     * @return Note object found
     */
    Note findByUuid(String uuid);

    /**
     * Find Note object by name
     * @param name
     * @return Note object found
     */
    Note findByName(String name);

    /**
     * Save or Update Note object
     * @param note
     * @return Note object saved or updated
     */
    Note saveOrUpdate(Note note);

    /**
     * Delete Note object
     * @param note
     */
    void delete(Note note);

    /**
     * Delete Note object by id
     * @param id
     */
    void deleteById(String id);

    /**
     * Check if Note object exists by id
     * @param id
     * @return
     */
    boolean existsById(String id);

    /**
     * Delete all Note objects
     */
    void deleteAll();

    /**
     * List all Note objects by User uuid
     * @param uuid
     * @return List of all Note objects found
     */
    List<Note> listAllNotesByUserUuid(String uuid);

    /**
     * List all Note objects by Workspace uuid
     * @param uuid
     * @return List of all Note objects found
     */
    List<Note> listAllNotesByWorkspaceUuid(String uuid);
}
