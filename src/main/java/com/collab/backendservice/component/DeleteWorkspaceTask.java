package com.collab.backendservice.component;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.User;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.service.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by karthik on 2019-04-19
 */

/**
 * Class used to delete all Workspace, and subsequent User and Note objects based on scheduled time set by user.
 */
@Component
public class DeleteWorkspaceTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DeleteWorkspaceTask.class);

    private String workspaceUuid;

    private WorkspaceService workspaceService;
    private UserService userService;
    private NoteService noteService;

    /**
     * No-arg Constructor
     */
    public DeleteWorkspaceTask() {
    }

    /**
     * Constructor to init with workspace uuid
     * @param workspaceUuid
     */
    public DeleteWorkspaceTask(String workspaceUuid, WorkspaceService workspaceService, UserService userService, NoteService noteService) {
        this.workspaceUuid = workspaceUuid;
        this.workspaceService = workspaceService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @Override
    public void run() {
        // Perform scheduled delete

        // First retrieve list of User references from workspace
        List<User> userList = userService.listAllUsersByWorkspaceUuid(this.workspaceUuid);
        // Then retrieve list of Notes references from workspace
        List<Note> noteList = noteService.listAllNotesByWorkspaceUuid(this.workspaceUuid);

        // Handle Note deletion
        for (Note note: noteList) {
            noteService.deleteById(note.getId());
        }
        logger.info("Note objects for Workspace with "+workspaceUuid+" deleted at "+new Date());

        // Handle User deletion
        for (User user: userList) {
            userService.deleteById(user.getId());
        }
        logger.info("User objects for Workspace with "+workspaceUuid+" deleted at "+new Date());

        // Handle Workspace deletion
        workspaceService.delete(workspaceService.findByUuid(this.workspaceUuid));
        logger.info("Workspace with "+workspaceUuid+" deleted at "+new Date());
    }

}
