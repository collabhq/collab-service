package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;

/**
 * Created by karthik on 2019-03-11
 */
public interface RoomService {
    /**
     * List all Room objects
     * @return All Room objects
     */
    Iterable listAll();

    /**
     * Find Room object by id
     * @param id
     * @return Room object found
     */
    Room findById(String id);

    /**
     * Find Room object by uuid
     * @param uuid
     * @return Room object found
     */
    Room findByUuid(String uuid);

    /**
     * Find Room object by name
     * @param name
     * @return Room object found
     */
    Room findByName(String name);

    /**
     * Save or Update Room object
     * @param room
     * @return Room object saved or updated
     */
    Room saveOrUpdate(Room room);

    /**
     * Delete Room object
     * @param room
     */
    void delete(Room room);

    /**
     * Delete Room object by id
     * @param id
     */
    void deleteById(String id);

    /**
     * Check if Room object exists by id
     * @param id
     * @return
     */
    boolean existsById(String id);

    /**
     * Delete all Room objects
     */
    void deleteAll();

    /**
     * Add User object reference to Room object
     * @param room
     * @param user
     */
    void addUserToRoom(Room room, User user);

    /**
     * Add User object reference to Room object by id
     * @param roomId
     * @param userId
     */
    void addUserToRoomById(String roomId, String userId);

    /**
     * Add User object reference to Room object by uuid
     * @param roomUuid
     * @param userUuid
     */
    void addUserToRoomByUuid(String roomUuid, String userUuid);

    /**
     * Remove User object reference from Room object
     * @param room
     * @param user
     */
    void removeUserFromRoom(Room room, User user);

    /**
     * Remove User object reference from Room object by id
     * @param roomId
     * @param userId
     */
    void removeUserFromRoomById(String roomId, String userId);

    /**
     * Remove User object reference from Room object by uuid
     * @param roomUuid
     * @param userUuid
     */
    void removeUserFromRoomByUuid(String roomUuid, String userUuid);
}
