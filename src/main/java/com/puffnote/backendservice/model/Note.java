package com.puffnote.backendservice.model;

import com.puffnote.backendservice.util.CustomUUIDGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by karthik on 2019-03-11
 */
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    private String uuid;//Use separate UUID for reference
    private String name;
    private List<String> value;//List of strings determined based on type of note-text or list
    private String type;

    /**
     * No-arg Constructor - Enforce UUID generation
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public Note() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.uuid = new CustomUUIDGenerator().generateSHABasedUUID();
    }

    /**
     * Constructor
     * @param name
     * @param value
     * @param type
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public Note(String name, List<String> value, String type) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.uuid = new CustomUUIDGenerator().generateSHABasedUUID();
        this.name = name;
        this.value = value;
        this.type = type;
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

    public List<String> getValue() {
        return this.value;
    }
    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Note[id=%s, uuid=%s, name='%s', value='%s', type='%s]",
                id, uuid, name, value, type);
    }

}
