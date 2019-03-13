package com.puffnote.backendservice.controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-11
 */
@RestController
public interface RoomController {
    @PostMapping(value = "/room")
    HashMap createRoom(@PathVariable String username);
}
