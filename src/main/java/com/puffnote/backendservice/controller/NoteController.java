package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Note;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Controller
public interface NoteController {
    @MessageMapping("/note/{roomId}")
    @SendTo("/topic/room/{roomId}")
    //TODO: Add operation object as parameter to this method
    Note patchNote(@Payload String payload);
}