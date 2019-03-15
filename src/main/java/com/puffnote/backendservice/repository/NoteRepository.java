package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */
@RestResource(exported = false)
public interface NoteRepository extends MongoRepository<Note,String> {
    public Note findByName(String name);
    public Note findByUuid(String uuid);
}

