package com.collab.backendservice.model.response;

import com.collab.backendservice.model.Note;
import lombok.*;

/**
 * Created by karthik on 2019-05-01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteResponseObject {

    // These variable names reflect those being used in the UI for consistency
    private String uuid;
    private String title;
    private String content;
    private String userUUID;

    /**
     * Constructor that takes Note object and maps to values signified as in the UI
     * @param note
     */
    public NoteResponseObject(Note note) {
        this.uuid = note.getUuid();
        this.title = note.getName();
        this.content = note.getValue();
        this.userUUID = note.getUserUUID();
    }
}
