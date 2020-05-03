package com.collab.backendservice.model.request;

import com.collab.backendservice.util.Constants;
import lombok.*;

/**
 * Created by karthik on 2019-03-17
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteRequestObject {

    private String userUUID;
    private String workspaceUUID;

    private String noteUUID;
    private String noteName;
    private String noteValue;

    private Constants.NoteOperation noteOperation;
}
