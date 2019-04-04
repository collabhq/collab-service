package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Data repository class used to implement data access on Note objects
 */
@RestResource(exported = false)
public interface NoteRepository extends MongoRepository<Note,String> {
    /**
     * Find note object by name
     * @param name
     * @return Note object found
     */
    public Note findByName(String name);

    /**
     * Find note object by uuid
     * @param uuid
     * @return Note object found
     */
    public Note findByUuid(String uuid);
}

