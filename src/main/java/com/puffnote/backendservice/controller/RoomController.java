package com.puffnote.backendservice.controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-11
 */
@RestController
public interface RoomController {
    @RequestMapping(value = "/room/{username}")
    HashMap createRoom(@PathVariable("username") String username);

}
