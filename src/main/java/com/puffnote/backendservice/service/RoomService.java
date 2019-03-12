package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;

/**
 * Created by karthik on 2019-03-11
 */
public interface RoomService {
    Iterable listAll();
    Room findById(String id);
    Room findByUuid(String uuid);
    Room findByName(String name);
    Room saveOrUpdate(Room room);
    void delete(Room room);
    void deleteById(String id);
    boolean existsById(String id);
    void deleteAll();
    void addUserToRoom(Room room, User user);
    void addUserToRoomById(String roomId, String userId);
    void removeUserFromRoom(Room room, User user);
    void removeUserFromRoomById(String roomId, String userId);
}
