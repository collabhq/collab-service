package com.collab.backendservice.service;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.User;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.repository.NoteRepository;
import com.collab.backendservice.repository.UserRepository;
import com.collab.backendservice.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by karthik on 2019-03-11
 */
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

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
        log.info("Updated Note: " + note);
        return note;
    }

    @Override
    public void delete(Note note) {
        noteRepository.delete(note);
        log.info("Deleted Note: " + note);
    }

    @Override
    public void deleteById(String id) {
        noteRepository.deleteById(id);
        log.info("Deleted Note with Id: " + id);
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
        log.info("Deleted All Notes");
    }

    @Override
    public List<Note> listAllNotesByUserUuid(String uuid) {
        List<Note> notesList = new ArrayList<Note>();
        User user = userRepository.findByUuid(uuid);
        for (String noteReference : user.getNotesReferences()) {
            notesList.add(this.findById(noteReference));
        }
        log.info("All notes by user uuid: " + notesList);
        return notesList;
    }

    @Override
    public List<Note> listAllNotesByWorkspaceUuid(String uuid) {
        List<Note> notesList = new ArrayList<Note>();
        Workspace workspace = workspaceRepository.findByUuid(uuid);
        for (String userReference : workspace.getUserReferences()) {
            Optional<User> user = userRepository.findById(userReference);
            user.ifPresent(existingUser -> {
                String userUuid = existingUser.getUuid();
                notesList.addAll(this.listAllNotesByUserUuid(userUuid));
            });
        }
        log.info("All notes by workspace uuid: " + notesList);
        return notesList;
    }


}
