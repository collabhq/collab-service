package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.puffnote.backendservice.repository.RoomRepository;

/**
 * Created by karthik on 2019-03-11
 */
@Service
public class RoomServiceImpl implements RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Iterable listAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room findByUuid(String uuid) {
        return roomRepository.findByUuid(uuid);
    }

    @Override
    public Room findByName(String name) {
        return roomRepository.findByName(name);
    }

    @Override
    public Room saveOrUpdate(Room room) {
        roomRepository.save(room);
        logger.info("Updated Room: " + room);
        return room;
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
        logger.info("Deleted Room: " + room);
    }

    @Override
    public void deleteById(String id) {
        roomRepository.deleteById(id);
        logger.info("Deleted Room with Id: " + id);
    }

    @Override
    public boolean existsById(String id) {
        if(roomRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        roomRepository.deleteAll();
        logger.info("Deleted All Rooms");
    }

    @Override
    public void addUserToRoom(Room room, User user) {
        room.getUserReferences().add(user.getId());
        roomRepository.save(room);
        logger.info("Added User: " + user + " to room: " + room);
    }

    @Override
    public void addUserToRoomById(String roomId, String userId) {
        Room room = this.findById(roomId);
        room.getUserReferences().add(userId);
        roomRepository.save(room);
        logger.info("Added User with Id: " + userId + " to room: " + room);
    }

    @Override
    public void removeUserFromRoom(Room room, User user) {
        room.getUserReferences().removeIf(reference -> (reference == user.getId()));
        roomRepository.save(room);
        logger.info("Removed User: " + user + " to room: " + room);
    }

    @Override
    public void removeUserFromRoomById(String roomId, String userId) {
        Room room = this.findById(roomId);
        room.getUserReferences().removeIf(reference -> (reference == userId));
        roomRepository.save(room);
        logger.info("Removed User with Id: " + userId + " to room: " + room);
    }

}
