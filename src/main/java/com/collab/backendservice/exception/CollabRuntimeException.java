package com.collab.backendservice.exception;

/**
 * Created by karthik on 2019-04-23
 */
public class CollabRuntimeException extends RuntimeException {

    /**
     * Create CollabRuntimeException instance
     * @param errorMessage The exception error message
     * @param err The root cause of exception
     */
    public CollabRuntimeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}