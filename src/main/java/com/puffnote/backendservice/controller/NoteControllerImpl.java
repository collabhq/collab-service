package com.puffnote.backendservice.controller;

import com.mongodb.util.JSON;
import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.NoteOperationObject;
import com.puffnote.backendservice.service.NoteService;
import com.puffnote.backendservice.service.RoomService;
import com.puffnote.backendservice.service.UserService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
    private RoomService roomService;

    @Override
    public Note patchNote(NoteOperationObject payload) {
        //Use NoteOperationObject to add/modify/delete Note
        Note note = null;
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
                if(payload.getNoteName() != null)
                    note.setName(payload.getNoteName());
                if(payload.getNoteValue() != null)
                    note.setValue(payload.getNoteValue());
                noteService.saveOrUpdate(note);
                break;
            case DELETE:
                note = noteService.findByUuid(payload.getNoteUUID());
                userService.removeNoteFromUserByUuid(payload.getUserUUID(), payload.getNoteUUID());
                noteService.deleteById(note.getId());
                break;
            default:
                break;
        }
        return note;
    }
}
