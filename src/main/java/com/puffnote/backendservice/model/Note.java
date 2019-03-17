package com.puffnote.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.puffnote.backendservice.util.Constants;
import com.puffnote.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Class that represents a note object
 */
@JsonIgnoreProperties({"id"})
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;//Use separate UUID for reference

    //We persist data only for 48 hours from creation
    @Indexed(name ="createdAt", expireAfterSeconds = Constants.DEFAULT_DOCUMENT_EXPIRY_TIME_SECONDS)
    private Date createdAt;

    private String name;
    private String value;//String containing markdown text

    /**
     * No-arg Constructor
     * UUID enforced
     */
    public Note() {
        this.createdAt = new Date();
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = "";
        this.value = "";
    }

    /**
     * Constructor
     * @param name
     * @param value
     */
    public Note(String name, String value) {
        this.createdAt = new Date();
        this.uuid = CustomUUIDGenerator.generateRandomUUID();
        this.name = name;
        this.value = value;
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
     * Get name of note
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set name for note
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get value of text given to note
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of text for note
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format(
                "Note[id=%s, uuid=%s, name='%s', value='%s']",
                id, uuid, name, value);
    }

}
