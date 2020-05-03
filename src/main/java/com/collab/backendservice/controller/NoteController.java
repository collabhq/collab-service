package com.collab.backendservice.controller;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.User;
import com.collab.backendservice.model.request.NoteRequestObject;
import com.collab.backendservice.model.response.NoteResponseObject;
import com.collab.backendservice.model.response.SocketResponseObject;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Controller
public class NoteController {

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
    @MessageMapping("/note/workspace/{identifier}")
    @SendTo("/topic/workspace/{identifier}")
    public SocketResponseObject patchNote(Principal principal, @Payload NoteRequestObject payload) {
        Note note;
        SocketResponseObject socketResponseObject = null;
        NoteResponseObject noteResponseObject;
        String principalUserUUID = ((User)((AnonymousAuthenticationToken) principal).getPrincipal()).getUuid();
        if(payload != null && userService.isUserPartOfWorkspace(principalUserUUID , payload.getWorkspaceUUID())) {
            switch (payload.getNoteOperation()) {
                case ADD:
                    note = new Note();
                    if(payload.getNoteName() != null)
                        note.setName(payload.getNoteName());
                    if(payload.getNoteValue() != null)
                        note.setValue(payload.getNoteValue());
                    note.setUserUUID(payload.getUserUUID());
                    noteService.saveOrUpdate(note);
                    userService.addNoteToUserByUuid(payload.getUserUUID(), note.getUuid());
                    noteResponseObject = new NoteResponseObject(note);
                    socketResponseObject = new SocketResponseObject(SocketResponseObject.SocketResponseType.SAVE_NOTE, noteResponseObject);
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
                    noteResponseObject = new NoteResponseObject(note);
                    socketResponseObject = new SocketResponseObject(SocketResponseObject.SocketResponseType.SAVE_NOTE, noteResponseObject);
                    //TODO: Throw custom exception for note not being found
                    break;
                case DELETE:
                    note = noteService.findByUuid(payload.getNoteUUID());
                    if(note != null) {
                        userService.removeNoteFromUserByUuid(payload.getUserUUID(), payload.getNoteUUID());
                        noteService.deleteById(note.getId());
                    }
                    noteResponseObject = new NoteResponseObject(note);
                    socketResponseObject = new SocketResponseObject(SocketResponseObject.SocketResponseType.DELETE_NOTE, noteResponseObject);
                    //TODO: Throw custom exception for note not being found
                    break;
                default:
                    break;
            }
        }
        //TODO: Throw custom exception as payload is empty
        return socketResponseObject;
    }

    /**
     * Fetches a list of notes associated with an user UUID
     * @param userUUID userUUID
     * @return List<Note>
     */
    @RequestMapping(value = "/note/{userUUID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Note> getNotes(Principal principal, @PathVariable String userUUID) {
        List<Note> notes = new ArrayList<>();
        if(((User)((AnonymousAuthenticationToken) principal).getPrincipal()).getUuid().equalsIgnoreCase(userUUID)) {
            User user = userService.findByUuid(userUUID);
            if (user != null) {
                notes = user.getNotesReferences()
                        .stream()
                        .map(noteRef -> (noteService.findById(noteRef)))
                        .collect(Collectors.toList());
            }
        }
        //TODO: Throw custom exception when principal and userUUID does not match
        return notes;
    }
}
