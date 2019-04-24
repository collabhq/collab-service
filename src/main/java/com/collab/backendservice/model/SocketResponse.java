package com.collab.backendservice.model;

/**
 * Created by sudeshgutta on 2019-04-23
 */
public class SocketResponse {

    public enum SocketResponseType {
        NOTE,
        USER
    }

    private SocketResponseType type;
    private Object payload;

    public SocketResponse(SocketResponseType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * Fetch socket response type
     * @return SocketResponseType
     */
    public SocketResponseType getType() {
        return type;
    }

    /**
     * Fetch socket response payload
     * @return Object
     */
    public Object getPayload() {
        return payload;
    }

}
