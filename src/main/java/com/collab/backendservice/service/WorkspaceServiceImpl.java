package com.collab.backendservice.service;

import com.collab.backendservice.model.User;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.repository.UserRepository;
import com.collab.backendservice.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by karthik on 2019-03-11
 */
@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

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
        log.info("Updated Workspace: " + workspace);
        return workspace;
    }

    @Override
    public void delete(Workspace workspace) {
        workspaceRepository.delete(workspace);
        log.info("Deleted Workspace: " + workspace);
    }

    @Override
    public void deleteById(String id) {
        workspaceRepository.deleteById(id);
        log.info("Deleted Workspace with Id: " + id);
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
        log.info("Deleted All Workspaces");
    }

    @Override
    public void addUserToWorkspace(Workspace workspace, User user) {
        workspace.getUserReferences().add(user.getId());
        workspaceRepository.save(workspace);
        log.info("Added User: " + user + " to workspace: " + workspace);
    }

    @Override
    public void addUserToWorkspaceById(String workspaceId, String userId) {
        Workspace workspace = this.findById(workspaceId);
        workspace.getUserReferences().add(userId);
        workspaceRepository.save(workspace);
        log.info("Added User with Id: " + userId + " to workspace: " + workspace);
    }

    @Override
    public void addUserToWorkspaceByUuid(String workspaceUUID, String userUUID) {
        Workspace workspace = this.findByUuid(workspaceUUID);
        User user = userRepository.findByUuid(userUUID);
        workspace.getUserReferences().add(user.getId());
        workspaceRepository.save(workspace);
        log.info("Added User with Uuid: " + userUUID + " to workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspace(Workspace workspace, User user) {
        workspace.getUserReferences().removeIf((String reference) -> user.getId().equals(reference));
        workspaceRepository.save(workspace);
        log.info("Removed User: " + user + " from workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspaceById(String workspaceId, String userId) {
        Workspace workspace = this.findById(workspaceId);
        workspace.getUserReferences().removeIf((String reference) -> userId.equals(reference));
        workspaceRepository.save(workspace);
        log.info("Removed User with Id: " + userId + " from workspace: " + workspace);
    }

    @Override
    public void removeUserFromWorkspaceByUuid(String workspaceUUID, String userUUID) {
        Workspace workspace = this.findByUuid(workspaceUUID);
        User user = userRepository.findByUuid(userUUID);
        workspace.getUserReferences().removeIf((String reference) -> user.getId().equals(reference));
        workspaceRepository.save(workspace);
        log.info("Removed User with Uuid: " + userUUID + " from workspace: " + workspace);
    }

}
