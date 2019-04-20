package com.puffnote.backendservice.util;

/**
 * Created by karthik on 2019-03-11
 */
public final class Constants {

    /**
     * Constructor - Restrict instantiation with explicit private access modifier
     */
    private Constants() {}

    /**
     * ENUMS-->
     */

    //ENUMS for Note Operation Object
    public static enum NoteOperation{
        ADD,
        EDIT,
        DELETE
    }

    /**
     * Constants-->
     */

    //Constants for UUIDs
    public static final int SHORT_UUID_LENGTH = 8;
    public static final int MED_UUID_LENGTH = 10;

    //Constants for time
    public static final int DEFAULT_DOCUMENT_EXPIRY_TIME_SECONDS = 172800;//~48 hours

    //Constant for Metrics Document
    public static final String METRICS_UNIQUE_INDEX = "ZkHjqI9H";
}
