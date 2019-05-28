package com.collab.backendservice.controller;
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

/**
 * Created by sudeshgutta on 2019-03-11
 */
@RestController
public interface WorkspaceController {
    @PostMapping(
            value = "/workspace",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    HashMap createWorkspace(@RequestBody HashMap reqBody);

    @PostMapping(
            value = "/workspace/{identifier}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    HashMap joinWorkspace(@PathVariable String identifier, @RequestBody HashMap reqBody);

    @MessageMapping("/workspace/{identifier}")
    @SendTo("/topic/workspace/{identifier}")
    SocketResponse checkIfWorkspaceExists(@Payload String workspaceUUID);
}
