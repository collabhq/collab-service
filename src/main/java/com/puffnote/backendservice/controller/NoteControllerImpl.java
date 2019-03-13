package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.service.NoteService;
import com.puffnote.backendservice.service.RoomService;
import com.puffnote.backendservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by sudeshgutta on 2019-03-12
 */
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
    public void patchNote(String roomId, String userId) {
        //Create Note
        Note note = new Note();
        noteService.saveOrUpdate(note);
        userService.addNoteToUserById(userId, note.getId());
        simpMessagingTemplate.convertAndSend("/queue/room/" + roomId, note);
    }
}
