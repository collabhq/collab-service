package com.collab.backendservice.controller;

import com.collab.backendservice.component.DeleteWorkspaceTask;
import com.collab.backendservice.component.JwtTokenBuilder;
import com.collab.backendservice.model.User;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.model.response.SocketResponseObject;
import com.collab.backendservice.service.MetricsService;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.service.WorkspaceService;
import com.collab.backendservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@RestController
@Slf4j
public class WorkspaceController {

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;
    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Creates a new workspace and adds the creator to it
     * @param reqBody reqBody
     * @return HashMap containing workspaceUUID & userUUID
     */
    @PostMapping(
            value = "/workspace",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public HashMap createWorkspace(@RequestBody HashMap reqBody) {
        //TODO: Add a model object instead of HashMap as request body
        Object username = reqBody.get("username");
        Object workspaceName = reqBody.get("workspaceName");
        User user = new User();
        Workspace workspace = new Workspace();
        if(username != null)
            user.setName(username.toString());
        if(workspaceName != null)
            workspace.setName(workspaceName.toString());
        user.setWorkspaceUUID(workspace.getUuid());
        userService.saveOrUpdate(user);
        // Set expiry to workspace object
        workspace.setExpiry(Constants.getDocumentDeletionTime(reqBody.get("expiry").toString()));
        // Set workspace deletion date
        Date workspaceDeletionDate = new Date(new Date().getTime() + Constants.getDocumentDeletionTime(reqBody.get("expiry").toString()));
        workspaceService.saveOrUpdate(workspace);
        workspaceService.addUserToWorkspace(workspace, user);
        // Update metrics
        metricsService.incrementUserMetric();
        metricsService.incrementWorkspaceMetric();
        // Schedule workspace deletion task
        taskScheduler.schedule(
                new DeleteWorkspaceTask(
                        workspace.getUuid(),
                        workspaceService,
                        userService,
                        noteService,
                        simpMessagingTemplate),
                workspaceDeletionDate);
        log.info("Task scheduled for Workspace deletion at "+ workspaceDeletionDate);
        HashMap<String, Object> output = new HashMap<>();
        output.put("workspaceUUID", workspace.getUuid());
        output.put("userUUID", user.getUuid());
        output.put("users", userService.listAllUsersByWorkspaceUuid(workspace.getUuid()));
        output.put("jwt", jwtTokenBuilder.buildJwtToken(user.getUuid()));
        output.put("createdAt", workspace.getCreatedAt().getTime());
        output.put("expiry", workspace.getExpiry());
        return output;
    }

    /**
     * Adds a user to the workspace by their identifier
     * @param identifier Workspace Identifier
     * @param reqBody reqBody
     * @return HashMap containing workspaceUUID & userUUID
     */
    @PostMapping(
            value = "/workspace/{identifier}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap joinWorkspace(@PathVariable String identifier, @RequestBody HashMap reqBody) {
        //TODO: Add a model object instead of HashMap as request body
        Object username = reqBody.get("username");
        HashMap<String, Object> output = new HashMap<>();
        Workspace workspace = workspaceService.findByUuid(identifier);
        User user = new User();
        if(username != null)
            user.setName(username.toString());
        if(workspace != null) {
            user.setWorkspaceUUID(workspace.getUuid());
            userService.saveOrUpdate(user);
            workspaceService.addUserToWorkspace(workspace, user);
            metricsService.incrementUserMetric();

            output.put("workspaceUUID", workspace.getUuid());
            output.put("workspaceName", workspace.getName());
            output.put("userUUID", user.getUuid());
            output.put("notes", noteService.listAllNotesByWorkspaceUuid(workspace.getUuid()));
            output.put("users", userService.listAllUsersByWorkspaceUuid(workspace.getUuid()));
            output.put("jwt", jwtTokenBuilder.buildJwtToken(user.getUuid()));
            output.put("createdAt", workspace.getCreatedAt().getTime());
            output.put("expiry", workspace.getExpiry());
        }
        //TODO: Throw custom exception when workspace is not found
        //Notify when a user joins a workspace
        simpMessagingTemplate.convertAndSend("/topic/workspace/"+identifier,
                new SocketResponseObject(SocketResponseObject.SocketResponseType.USER, user));
        return output;
    }

    /**
     * Checks if a given workspace exists by its UUID
     * @param workspaceUUID Workspace UUID
     * @return SocketResponse
     */
    @MessageMapping("/workspace/{identifier}")
    @SendTo("/topic/workspace/{identifier}")
    public SocketResponseObject checkIfWorkspaceExists(@Payload String workspaceUUID) {
        if (workspaceUUID != null && workspaceService.findByUuid(workspaceUUID) != null)
            return null;
        else
            return new SocketResponseObject(SocketResponseObject.SocketResponseType.DELETE_WORKSPACE, workspaceUUID);
    }
}
