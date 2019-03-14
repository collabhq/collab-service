package com.puffnote.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.puffnote.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
@JsonIgnoreProperties({"id", "notesReferences"})
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String uuid;//Use separate UUID for reference
    private String name;

    //@DBRef
    private List<String> notesReferences;//Use manual references to notes stored in their collection

    /**
     * No-arg Constructor - Enforce UUID generation
     */
    public User() {
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = "";
        this.notesReferences = new ArrayList<String>();
    }

    /**
     * Constructor
     * @param name
     */
    public User(String name) {
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

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
