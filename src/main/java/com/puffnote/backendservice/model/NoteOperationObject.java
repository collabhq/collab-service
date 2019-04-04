package com.puffnote.backendservice.model;

import com.puffnote.backendservice.util.Constants;

/**
 * Created by karthik on 2019-03-17
 */
public class NoteOperationObject {

    private String userUUID;
    private String roomUUID;

    private String noteUUID;
    private String noteName;
    private String noteValue;

    private Constants.NoteOperation noteOperation;

    /**
     * Get User UUID
     * @return User UUID
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * Get Workspace UUID
     * @return Workspace UUID
     */
    public String getRoomUUID() {
        return roomUUID;
    }

    /**
     * Get Note UUID
     * @return Note UUID
     */
    public String getNoteUUID() {
        return noteUUID;
    }

    /**
     * Get Note Name
     * @return Note Name
     */
    public String getNoteName() {
        return noteName;
    }

    /**
     * Get Note Value
     * @return Note Value
     */
    public String getNoteValue() {
        return noteValue;
    }

    /**
     * Get Note Operation ENUM Value
     * @return Note Operation ENUM Value
     */
    public Constants.NoteOperation getNoteOperation() {
        return noteOperation;
    }
}
