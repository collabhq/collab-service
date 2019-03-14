package com.puffnote.backendservice.controller;

import com.mongodb.util.JSON;
import com.puffnote.backendservice.model.Note;
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
    public Note patchNote(String payload) {
        //TODO: Fix this in a better way
        HashMap payloadObject = (HashMap) JSON.parse(payload);
        //Create Note
        Note note = new Note();
        noteService.saveOrUpdate(note);
        userService.addNoteToUserByUuid(payloadObject.get("userId").toString(), note.getId());
        return note;
    }
}
