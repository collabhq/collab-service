package com.collab.backendservice.model;

import com.collab.backendservice.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by karthik on 2019-04-19
 */

/**
 * Class that represents metrics
 */
@JsonIgnoreProperties({"id"})
@Document(collection = "metrics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Metrics {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uniqueIndex = Constants.METRICS_UNIQUE_INDEX;

    private int users;
    private int workspaces;

    /**
     * Increment User metric
     */
    public void incrementUserValue() {
        this.users++;
    }

    /**
     * Increment Workspace metric
     */
    public void incrementWorkspaceValue() {
        this.workspaces++;
    }

}

