package com.collab.backendservice.exception;

/**
 * Created by karthik on 2019-04-23
 */
public class CollabException extends Exception {

    /**
     * Create CollabException instance
     * @param errorMessage The exception error message
     * @param err The root cause of exception
     */
    public CollabException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
