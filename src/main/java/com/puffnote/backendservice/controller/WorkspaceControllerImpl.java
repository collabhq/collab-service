package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Workspace;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.service.NoteService;
import com.puffnote.backendservice.service.WorkspaceService;
import com.puffnote.backendservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Component
public class WorkspaceControllerImpl implements WorkspaceController {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceControllerImpl.class);

    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;

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
        if(username != null && workspaceName != null) {
            user.setName(username.toString());
            workspace.setName(workspaceName.toString());
        }
        userService.saveOrUpdate(user);
        workspaceService.saveOrUpdate(workspace);
        workspaceService.addUserToWorkspace(workspace, user);
        HashMap<String, String> output = new HashMap<>();
        output.put("workspaceUUID", workspace.getUUID());
        output.put("userUUID", user.getUUID());
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
        if(workspace != null) {
            User user = new User(username.toString());
            userService.saveOrUpdate(user);
            workspaceService.addUserToWorkspace(workspace, user);

            output.put("workspaceUUID", workspace.getUUID());
            output.put("workspaceName", workspace.getName());
            output.put("userUUID", user.getUUID());
            output.put("notes", noteService.listAllNotesByWorkspaceUuid(workspace.getUUID()));
        }
        //TODO: Throw custom exception when workspace is not found
        return output;
    }
}
