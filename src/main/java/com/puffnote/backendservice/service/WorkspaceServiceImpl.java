package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Workspace;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.WorkspaceRepository;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable listAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public Workspace findById(String id) {
        return workspaceRepository.findById(id).orElse(null);
    }

    @Override
    public Workspace findByUuid(String uuid) {
        return workspaceRepository.findByUuid(uuid);
    }

    @Override
    public Workspace findByName(String name) {
        return workspaceRepository.findByName(name);
    }

    @Override
    public Workspace saveOrUpdate(Workspace workspace) {
        workspaceRepository.save(workspace);
        logger.info("Updated Workspace: " + workspace);
        return workspace;
    }

    @Override
    public void delete(Workspace workspace) {
        workspaceRepository.delete(workspace);
        logger.info("Deleted Workspace: " + workspace);
    }

    @Override
    public void deleteById(String id) {
        workspaceRepository.deleteById(id);
        logger.info("Deleted Workspace with Id: " + id);
    }

    @Override
    public boolean existsById(String id) {
        if(workspaceRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        workspaceRepository.deleteAll();
        logger.info("Deleted All Workspaces");
    }

    @Override
    public void addUserToWorkspace(Workspace workspace, User user) {
        workspace.getUserReferences().add(user.getId());
        workspaceRepository.save(workspace);
        logger.info("Added User: " + user + " to workspace: " + workspace);
    }

    @Override
    public void addUserToWorkspaceById(String workspaceId, String userId) {
        Workspace workspace = this.findById(workspaceId);
        workspace.getUserReferences().add(userId);
        workspaceRepository.save(workspace);
        logger.info("Added User with Id: " + userId + " to workspace: " + workspace);
    }

    @Override
    public void addUserToWorkspaceByUuid(String workspaceUUID, String userUUID) {
        Workspace workspace = this.findByUuid(workspaceUUID);
        User user = userRepository.findByUuid(userUUID);
        workspace.getUserReferences().add(user.getId());
        workspaceRepository.save(workspace);
        logger.info("Added User with Uuid: " + userUUID + " to workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspace(Workspace workspace, User user) {
        workspace.getUserReferences().removeIf(reference -> (reference == user.getId()));
        workspaceRepository.save(workspace);
        logger.info("Removed User: " + user + " to workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspaceById(String workspaceId, String userId) {
        Workspace workspace = this.findById(workspaceId);
        workspace.getUserReferences().removeIf(reference -> (reference == userId));
        workspaceRepository.save(workspace);
        logger.info("Removed User with Id: " + userId + " to workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspaceByUuid(String workspaceUUID, String userUUID) {
        Workspace workspace = this.findByUuid(workspaceUUID);
        User user = userRepository.findByUuid(userUUID);
        workspace.getUserReferences().removeIf(reference -> (reference == user.getId()));
        workspaceRepository.save(workspace);
        logger.info("Removed User with Uuid: " + userUUID + " to workspace: " + workspace);
    }

}
