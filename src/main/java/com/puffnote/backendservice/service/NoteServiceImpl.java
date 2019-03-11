package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.NoteRepository;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class NoteServiceImpl implements NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Iterable listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note findById(String id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note findByName(String name) {
        return noteRepository.findByName(name);
    }

    @Override
    public Note saveOrUpdate(Note note) {
        noteRepository.save(note);
        logger.info("Updated Note: " + note);
        return note;
    }

    @Override
    public void delete(Note note) {
        noteRepository.delete(note);
        logger.info("Deleted Note: " + note);
    }

    @Override
    public void deleteById(String id) {
        noteRepository.deleteById(id);
        logger.info("Deleted Note with Id: " + id);
    }

    @Override
    public boolean existsById(String id) {
        if(noteRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        noteRepository.deleteAll();
        logger.info("Deleted All Notes");
    }

}
