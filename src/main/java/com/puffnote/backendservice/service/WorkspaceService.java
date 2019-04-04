package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Workspace;
import com.puffnote.backendservice.model.User;

/**
 * Created by karthik on 2019-03-11
 */
public interface WorkspaceService {
    /**
     * List all Workspace objects
     * @return All Workspace objects
     */
    Iterable listAll();

    /**
     * Find Workspace object by id
     * @param id
     * @return Workspace object found
     */
    Workspace findById(String id);

    /**
     * Find Workspace object by uuid
     * @param uuid
     * @return Workspace object found
     */
    Workspace findByUuid(String uuid);

    /**
     * Find Workspace object by name
     * @param name
     * @return Workspace object found
     */
    Workspace findByName(String name);

    /**
     * Save or Update Workspace object
     * @param workspace
     * @return Workspace object saved or updated
     */
    Workspace saveOrUpdate(Workspace workspace);

    /**
     * Delete Workspace object
     * @param workspace
     */
    void delete(Workspace workspace);

    /**
     * Delete Workspace object by id
     * @param id
     */
    void deleteById(String id);

    /**
     * Check if Workspace object exists by id
     * @param id
     * @return
     */
    boolean existsById(String id);

    /**
     * Delete all Workspace objects
     */
    void deleteAll();

    /**
     * Add User object reference to Workspace object
     * @param workspace
     * @param user
     */
    void addUserToWorkspace(Workspace workspace, User user);

    /**
     * Add User object reference to Workspace object by id
     * @param workspaceId
     * @param userId
     */
    void addUserToWorkspaceById(String workspaceId, String userId);

    /**
     * Add User object reference to Workspace object by uuid
     * @param workspaceUUID
     * @param userUUID
     */
    void addUserToWorkspaceByUuid(String workspaceUUID, String userUUID);

    /**
     * Remove User object reference from Workspace object
     * @param workspace
     * @param user
     */
    void removeUserFromWorkspace(Workspace workspace, User user);

    /**
     * Remove User object reference from Workspace object by id
     * @param workspaceId
     * @param userId
     */
    void removeUserFromWorkspaceById(String workspaceId, String userId);

    /**
     * Remove User object reference from Workspace object by uuid
     * @param workspaceUUID
     * @param userUUID
     */
    void removeUserFromWorkspaceByUuid(String workspaceUUID, String userUUID);
}
