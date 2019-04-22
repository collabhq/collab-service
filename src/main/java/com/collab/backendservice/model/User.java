package com.collab.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.collab.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Class that represents a user object
 */
@JsonIgnoreProperties({"id", "notesReferences"})
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;//Use separate UUID for reference

    private Date createdAt;

    private String name;

    private List<String> notesReferences;//Use manual references to notes stored in their collection

    /**
     * No-arg Constructor
     * UUID enforced
     */
    public User() {
        this.createdAt = new Date();
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = "";
        this.notesReferences = new ArrayList<String>();
    }

    /**
     * Constructor
     * UUID enforced
     * @param name Name of user
     */
    public User(String name) {
        this.createdAt = new Date();
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = name;
        this.notesReferences = new ArrayList<String>();
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
     * Get name of user
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set name for user
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a list of DB references of all notes linked to this user
     * @return List of references
     */
    public List<String> getNotesReferences() {
        return this.notesReferences;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, uuid=%s, name='%s']",
                id, uuid, name);
    }

}
