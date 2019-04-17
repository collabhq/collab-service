package com.puffnote.backendservice.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by sudeshgutta on 2019-03-11
 */
@RestController
public interface WorkspaceController {
    @PostMapping(value = "/workspace",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    HashMap createWorkspace(@RequestBody HashMap reqBody);

    @PostMapping(value = "/workspace/{identifier}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    HashMap joinWorkspace(@PathVariable String identifier, @RequestBody HashMap reqBody);
}
