package com.collab.backendservice;

import com.collab.backendservice.component.DeleteWorkspaceTask;
import com.collab.backendservice.model.Metrics;
import com.collab.backendservice.model.Workspace;
import com.collab.backendservice.service.MetricsService;
import com.collab.backendservice.service.NoteService;
import com.collab.backendservice.service.UserService;
import com.collab.backendservice.service.WorkspaceService;
import com.collab.backendservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by karthik on 2019-04-19
 */

/**
 * This Class is to used to run code once Spring context has been initialised.
 */
@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("Running Post-Startup Steps");

        // Check for and create Metrics document
        logger.info("Checking for Metrics document");

        Metrics metricObject;
        if((metricObject = metricsService.findByUniqueIndex(Constants.METRICS_UNIQUE_INDEX)) == null){
            logger.info("Metrics document does not exist, creating document");
            metricObject = new Metrics();
            metricsService.saveOrUpdate(metricObject);
        }

        // Reschedule workspace deletion tasks based on timestamps
        logger.info("Rescheduling delete tasks on Workspaces");
        rescheduleWorkspaceDeletionTasks();

        logger.info("Post-Startup Steps Complete");
        return;
    }

    /**
     * Method to reschedule pending delete tasks on Workspace objects based on timestamps
     */
    private void rescheduleWorkspaceDeletionTasks() {
        // Fetch all workspace objects
        Iterable<Workspace> workspaces = workspaceService.listAll();
        for (Workspace workspace: workspaces) {
            Date workspaceDeletionDate = new Date(workspace.getCreatedAt().getTime() + workspace.getExpiry());
            taskScheduler.schedule(
                    new DeleteWorkspaceTask(
                            workspace.getUUID(),
                            workspaceService,
                            userService,
                            noteService),
                    workspaceDeletionDate);
            logger.info("Task scheduled for Workspace deletion at "+ workspaceDeletionDate);
        }

        logger.info("Workspace deletion tasks rescheduled successfully");
    }

}