package com.collab.backendservice.controller;

import com.collab.backendservice.component.DeleteWorkspaceTask;
import com.collab.backendservice.component.JwtTokenBuilder;
import com.collab.backendservice.model.SocketResponse;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.model.User;
import com.collab.backendservice.service.MetricsService;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.WorkspaceService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Component
public class WorkspaceControllerImpl implements WorkspaceController {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceControllerImpl.class);

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
    @Override
    public HashMap createWorkspace(HashMap reqBody) {
        //TODO: Add a model object instead of HashMap as request body
        Object username = reqBody.get("username");
        Object workspaceName = reqBody.get("workspaceName");
        User user = new User();
        Workspace workspace = new Workspace();
        if(username != null)
            user.setName(username.toString());
        if(workspaceName != null)
            workspace.setName(workspaceName.toString());
        user.setWorkspaceUUID(workspace.getUUID());
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
                        workspace.getUUID(),
                        workspaceService,
                        userService,
                        noteService,
                        simpMessagingTemplate),
                workspaceDeletionDate);
        logger.info("Task scheduled for Workspace deletion at "+ workspaceDeletionDate);
        HashMap<String, Object> output = new HashMap<>();
        output.put("workspaceUUID", workspace.getUUID());
        output.put("userUUID", user.getUUID());
        output.put("users", userService.listAllUsersByWorkspaceUuid(workspace.getUUID()));
        output.put("jwt", jwtTokenBuilder.buildJwtToken(user.getUUID()));
        return output;
    }

    /**
     * Adds a user to the workspace by their identifier
     * @param identifier Workspace Identifier
     * @param reqBody reqBody
     * @return HashMap containing workspaceUUID & userUUID
     */
    @Override
    public HashMap joinWorkspace(String identifier, HashMap reqBody) {
        //TODO: Add a model object instead of HashMap as request body
        Object username = reqBody.get("username");
        HashMap<String, Object> output = new HashMap<>();
        Workspace workspace = workspaceService.findByUuid(identifier);
        User user = new User(username.toString());
        if(workspace != null) {
            user.setWorkspaceUUID(workspace.getUUID());
            userService.saveOrUpdate(user);
            workspaceService.addUserToWorkspace(workspace, user);
            metricsService.incrementUserMetric();

            output.put("workspaceUUID", workspace.getUUID());
            output.put("workspaceName", workspace.getName());
            output.put("userUUID", user.getUUID());
            output.put("notes", noteService.listAllNotesByWorkspaceUuid(workspace.getUUID()));
            output.put("users", userService.listAllUsersByWorkspaceUuid(workspace.getUUID()));
            output.put("jwt", jwtTokenBuilder.buildJwtToken(user.getUUID()));
        }
        //TODO: Throw custom exception when workspace is not found
        //Notify when a user joins a workspace
        simpMessagingTemplate.convertAndSend("/topic/workspace/"+identifier,
                new SocketResponse(SocketResponse.SocketResponseType.USER, user));
        return output;
    }
}
