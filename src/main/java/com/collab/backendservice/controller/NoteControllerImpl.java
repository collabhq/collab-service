package com.collab.backendservice.controller;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.NoteOperationObject;
import com.collab.backendservice.model.User;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.WorkspaceService;
import com.collab.backendservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Component
public class NoteControllerImpl implements NoteController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private WorkspaceService workspaceService;

    /***
     * Performs a patch operation on the note model
     * @param payload Contains information about operation type & value for patch
     * @return Note
     */
    @Override
    public Note patchNote(NoteOperationObject payload) {
        Note note = null;
        if(payload != null) {
            switch (payload.getNoteOperation()) {
                case ADD:
                    note = new Note();
                    if(payload.getNoteName() != null)
                        note.setName(payload.getNoteName());
                    if(payload.getNoteValue() != null)
                        note.setValue(payload.getNoteValue());
                    noteService.saveOrUpdate(note);
                    userService.addNoteToUserByUuid(payload.getUserUUID(), note.getUUID());
                    break;
                case EDIT:
                    note = noteService.findByUuid(payload.getNoteUUID());
                    if(note != null) {
                        if (payload.getNoteName() != null)
                            note.setName(payload.getNoteName());
                        if (payload.getNoteValue() != null)
                            note.setValue(payload.getNoteValue());
                        noteService.saveOrUpdate(note);
                    }
                    //TODO: Throw custom exception for note not being found
                    break;
                case DELETE:
                    note = noteService.findByUuid(payload.getNoteUUID());
                    if(note != null) {
                        userService.removeNoteFromUserByUuid(payload.getUserUUID(), payload.getNoteUUID());
                        noteService.deleteById(note.getId());
                    }
                    //TODO: Throw custom exception for note not being found
                    break;
                default:
                    break;
            }
        }
        //TODO: Throw custom exception as payload is empty
        return note;
    }

    /**
     * Fetches a list of notes associated with an user UUID
     * @param userUUID userUUID
     * @return List<Note>
     */
    @Override
    public List<Note> getNotes(String userUUID) {
        List<Note> notes = new ArrayList<>();
        User user = userService.findByUuid(userUUID);
        if (user != null) {
            notes = user.getNotesReferences()
                    .stream()
                    .map(noteRef -> (noteService.findById(noteRef)))
                    .collect(Collectors.toList());
        }
        return notes;
    }
}
