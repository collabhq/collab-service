package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.service.RoomService;
import com.puffnote.backendservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-12
 */
@Component
public class RoomControllerImpl implements RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomControllerImpl.class);

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @Override
    public HashMap createRoom(String username) {
        User user = new User(username);
        userService.saveOrUpdate(user);
        Room room = new Room();
        roomService.saveOrUpdate(room);
        roomService.addUserToRoom(room, user);
        HashMap<String, String> output = new HashMap<>();
        //TODO: Refactor identifiers below
        output.put("roomId", room.getUUID());
        output.put("userId", user.getUUID());
        return output;
    }

    @Override
    public HashMap joinRoom(String identifier, String username) {
        HashMap<String, String> output = new HashMap<>();
        Room room = roomService.findByUuid(identifier);
        if(room != null) {
            User user = new User(username);
            roomService.addUserToRoom(room, user);
            //TODO: Refactor identifiers below
            output.put("roomId", room.getUUID());
            output.put("userId", user.getUUID());
        }
        //TODO: Throw custom exception when room is not found
        return output;
    }
}
