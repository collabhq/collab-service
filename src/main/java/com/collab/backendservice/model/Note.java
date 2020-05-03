package com.collab.backendservice.model;

import com.collab.backendservice.util.CustomUUIDGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Note {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid = CustomUUIDGenerator.generateRandomUUID();//Use separate UUID for reference

    @Setter(AccessLevel.NONE)
    private Date createdAt = new Date();
    private String userUUID;//UUID of user that has created note

    private String name;
    private String value;//String containing markdown text

}
