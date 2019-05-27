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
 * Class that represents a workspace object
 */
@JsonIgnoreProperties({"id", "userReferences"})
@Document(collection = "workspaces")
public class Workspace {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;//Use separate UUID for reference

    private Date createdAt;

    private int expiry;//Used for indicating expiry in milliseconds post createdAt Date value

    private String name;

    private List<String> userReferences;//Use manual references to users stored in their collection

    /**
     * No-arg Constructor
     * UUID enforced and uses a short ID format
     */
    public Workspace() {
        this.createdAt = new Date();
        this.uuid = CustomUUIDGenerator.generateShortUUID();
        this.name = "My Workspace";
        this.userReferences = new ArrayList<String>();
    }

    /**
     * Constructor
     * UUID enforced and uses a short ID format
     * @param name Name of workspace
     */
    public Workspace(String name) {
        this.createdAt = new Date();
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
     * Get name of workspace
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set name for workspace
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get created at Date value
     * @return createdAt Date value
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Get expiry in milliseconds as String
     * @return expiry
     */
    public int getExpiry() {
        return expiry;
    }

    /**
     * Set expiry in milliseconds as String
     * @param expiry
     */
    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    /**
     * Get a list of DB references of all users linked to this workspace
     * @return List of references
     */
    public List<String> getUserReferences() {
        return this.userReferences;
    }

    @Override
    public String toString() {
        return String.format(
                "Workspace[id=%s, uuid=%s, name='%s']",
                id, uuid, name);
    }

}
