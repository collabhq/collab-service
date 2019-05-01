package com.collab.backendservice.controller;

import com.collab.backendservice.exception.CollabException;
import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.NoteOperationObject;
import com.collab.backendservice.model.SocketResponse;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Controller
public interface NoteController {
    @MessageMapping("/note/workspace/{identifier}")
    @SendTo("/topic/workspace/{identifier}")
    SocketResponse patchNote(Principal principal, @Payload NoteOperationObject payload);

    @RequestMapping(value = "/note/{userUUID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Note> getNotes(Principal principal, @PathVariable String userUUID);
}