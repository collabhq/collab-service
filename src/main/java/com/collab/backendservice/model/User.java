package com.collab.backendservice.model;

import com.collab.backendservice.util.CustomUUIDGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid = CustomUUIDGenerator.generateRandomUUID();//Use separate UUID for reference

    @Setter(AccessLevel.NONE)
    private Date createdAt = new Date();
    private String workspaceUUID;//Workspace user belongs to

    private String name = "User";

    private List<String> notesReferences = new ArrayList<>();//Use manual references to notes stored in their collection
}
