package com.collab.backendservice.component;

import com.collab.backendservice.model.Note;
import com.collab.backendservice.model.User;
import com.collab.backendservice.model.response.SocketResponseObject;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.service.WorkspaceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DeleteWorkspaceTask implements Runnable {

    private String workspaceUuid;

    private WorkspaceService workspaceService;
    private UserService userService;
    private NoteService noteService;
    private SimpMessagingTemplate simpMessagingTemplate;

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
        log.info("Note objects for Workspace with "+workspaceUuid+" deleted at "+new Date());

        // Handle User deletion
        for (User user: userList) {
            userService.deleteById(user.getId());
        }
        log.info("User objects for Workspace with "+workspaceUuid+" deleted at "+new Date());

        // Handle Workspace deletion
        workspaceService.delete(workspaceService.findByUuid(this.workspaceUuid));
        log.info("Workspace with "+workspaceUuid+" deleted at "+new Date());

        //Notify when a workspace is deleted to connected clients
        this.simpMessagingTemplate.convertAndSend("/topic/workspace/"+this.workspaceUuid,
                new SocketResponseObject(SocketResponseObject.SocketResponseType.DELETE_WORKSPACE, this.workspaceUuid));
    }

}
