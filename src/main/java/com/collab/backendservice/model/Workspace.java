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
 * Class that represents a workspace object
 */
@JsonIgnoreProperties({"id", "userReferences"})
@Document(collection = "workspaces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Workspace {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid = CustomUUIDGenerator.generateShortUUID();//Use separate UUID for reference

    @Setter(AccessLevel.NONE)
    private Date createdAt = new Date();

    private int expiry;//Used for indicating expiry in milliseconds post createdAt Date value

    private String name = "My Workspace";

    private List<String> userReferences = new ArrayList<>();//Use manual references to users stored in their collection
}
