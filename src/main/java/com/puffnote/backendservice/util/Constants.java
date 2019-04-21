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
    public static final int DEFAULT_DOCUMENT_EXPIRY_TIME_MILLISECONDS = 172800 * 1000;//~48 hours

    //Time constants in milliseconds
    public static final int DOCUMENT_EXPIRY_TIME_1HOUR = 3600 * 1000;//1 hour
    public static final int DOCUMENT_EXPIRY_TIME_12HOUR = 3600 * 12 * 1000;//12 hour
    public static final int DOCUMENT_EXPIRY_TIME_24HOUR = 3600 * 24 * 1000;//24 hour

    //Constant for Metrics Document
    public static final String METRICS_UNIQUE_INDEX = "ZkHjqI9H";

    //Methods
    public static int getDocumentDeletionTime(String time){
        // Use default time of 48 hours.
        int result;

        switch (time){
            case "HOUR1":
                result = DOCUMENT_EXPIRY_TIME_1HOUR;
                break;
            case "HOUR12":
                result = DOCUMENT_EXPIRY_TIME_12HOUR;
                break;
            case "HOUR24":
                result = DOCUMENT_EXPIRY_TIME_24HOUR;
                break;
            case "HOUR48":
                result = DEFAULT_DOCUMENT_EXPIRY_TIME_MILLISECONDS;
                break;
            default:
                result = DEFAULT_DOCUMENT_EXPIRY_TIME_MILLISECONDS;
                break;// Breaking here for consistency.
        }
        return result;
    }
}
