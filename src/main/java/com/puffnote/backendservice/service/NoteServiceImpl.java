package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.repository.RoomRepository;
import com.puffnote.backendservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class NoteServiceImpl implements NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Iterable listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note findById(String id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note findByUuid(String uuid) {
        return noteRepository.findByUuid(uuid);
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

    @Override
    public List<Note> listAllNotesByUserUuid(String uuid) {
        List<Note> notesList = new ArrayList<Note>();
        User user = userRepository.findByUuid(uuid);
        for (String noteReference : user.getNotesReferences()) {
            notesList.add(this.findById(noteReference));
        }
        logger.info("All notes by user uuid: " + notesList);
        return notesList;
    }

    @Override
    public List<Note> listAllNotesByRoomUuid(String uuid) {
        List<Note> notesList = new ArrayList<Note>();
        Room room = roomRepository.findByUuid(uuid);
        for (String userReference : room.getUserReferences()) {
            Optional<User> user = userRepository.findById(userReference);
            user.ifPresent(existingUser -> {
                String userUuid = existingUser.getUUID();
                notesList.addAll(this.listAllNotesByUserUuid(userUuid));
            });
        }
        logger.info("All notes by room uuid: " + notesList);
        return notesList;
    }


}
