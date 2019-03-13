package com.puffnote.backendservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Controller
public interface NoteController {
    @MessageMapping("/note")
    //TODO: Add operation object as parameter to this method
    void patchNote(@Payload String roomId, @Payload String userId);
}