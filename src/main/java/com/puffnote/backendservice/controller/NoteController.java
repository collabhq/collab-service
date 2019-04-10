package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.NoteOperationObject;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Controller
public interface NoteController {
    @MessageMapping("/note/workspace/{identifier}")
    @SendTo("/topic/workspace/{identifier}")
    Note patchNote(@Payload NoteOperationObject payload);

    @RequestMapping(value = "/note/{userUUID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Note> getNotes(@PathVariable String userUUID);
}