package com.puffnote.backendservice.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-11
 */
@RestController
public interface RoomController {
    @PostMapping(value = "/room",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    HashMap createRoom(@RequestBody String username);
}
