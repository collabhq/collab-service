package com.puffnote.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.puffnote.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
@JsonIgnoreProperties({"id", "userReferences"})
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;

    private String uuid;//Use separate UUID for reference
    private String name;

    //@DBRef
    private List<String> userReferences;//Use manual references to users stored in their collection

    /**
     * No-arg Constructor - Enforce UUID generation
     */
    public Room() {
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
    }

    /**
     * Constructor
     * @param name
     * @param userReferences
     */
    public Room(String name, List<String> userReferences) {
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = name;
        this.userReferences = userReferences;
    }

    /**
     * Get Object ID
     * @return ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get Manually generated custom UUID
     * @return UUID
     */
    public String getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUserReferences() {
        return this.userReferences;
    }

    @Override
    public String toString() {
        return String.format(
                "Room[id=%s, uuid=%s, name='%s']",
                id, uuid, name);
    }

}
