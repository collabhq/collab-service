package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by karthik on 2019-03-11
 */
public interface NoteRepository extends MongoRepository<Note,String> {
    public Note findByName(String name);
}

