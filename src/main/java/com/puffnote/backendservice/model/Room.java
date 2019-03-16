package com.puffnote.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.puffnote.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Class that represents a room object
 */
@JsonIgnoreProperties({"id", "userReferences"})
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;

    private String uuid;//Use separate UUID for reference
    private String name;

    private List<String> userReferences;//Use manual references to users stored in their collection

    /**
     * No-arg Constructor
     * UUID enforced and uses a short ID format
     */
    public Room() {
        this.uuid = CustomUUIDGenerator.generateShortUUID();
        this.name = "";
        this.userReferences = new ArrayList<String>();
    }

    /**
     * Constructor
     * UUID enforced and uses a short ID format
     * @param name Name of room
     */
    public Room(String name) {
        this.uuid = CustomUUIDGenerator.generateShortUUID();
        this.name = name;
        this.userReferences = new ArrayList<String>();
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

    /**
     * Get name of room
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set name for room
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a list of DB references of all users linked to this room
     * @return List of references
     */
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
