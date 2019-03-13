package com.puffnote.backendservice.controller;

import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.service.RoomService;
import com.puffnote.backendservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-12
 */
public class RoomControllerImpl implements RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomControllerImpl.class);

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @Override
    public HashMap createRoom(String username) {
        User user = new User(username, null);
        userService.saveOrUpdate(user);
        Room room = new Room();
        roomService.saveOrUpdate(room);
        roomService.addUserToRoom(room, user);
        HashMap<String, String> output = new HashMap<>();
        output.put("roomId", room.getUUID());
        output.put("userId", user.getUUID());
        return output;
    }
}
