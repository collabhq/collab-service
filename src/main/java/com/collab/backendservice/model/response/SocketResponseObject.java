package com.collab.backendservice.model.response;

import lombok.*;

/**
 * Created by sudeshgutta on 2019-04-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SocketResponseObject {

    public enum SocketResponseType {
        SAVE_NOTE,
        USER,
        DELETE_NOTE,
        DELETE_WORKSPACE
    }

    private SocketResponseType type;
    private Object payload;
}
