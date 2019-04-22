package com.collab.backendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.collab.backendservice.util.Constants;
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
public class Metrics {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uniqueIndex = Constants.METRICS_UNIQUE_INDEX;

    private int numberOfUsers;
    private int numberOfWorkspaces;

    /**
     * No-arg Constructor
     */
    public Metrics() {
        this.numberOfUsers = 0;
        this.numberOfWorkspaces = 0;
    }

    /**
     * Get Object ID
     * @return ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get Number of Users Created
     * @return numberOfUsers
     */
    public int getUsers() {
        return this.numberOfUsers;
    }

    /**
     * Get Number of Workspaces Created
     * @return numberOfWorkspaces
     */
    public int getWorkspaces() {
        return this.numberOfWorkspaces;
    }

    /**
     * Increment User metric
     */
    public void incrementUserValue() {
        this.numberOfUsers++;
    }

    /**
     * Increment Workspace metric
     */
    public void incrementWorkspaceValue() {
        this.numberOfWorkspaces++;
    }

    @Override
    public String toString() {
        return String.format(
                "Metrics[id=%s, numberOfUsers=%d, numberOfWorkspaces=%d]",
                id, numberOfUsers, numberOfWorkspaces);
    }

}

