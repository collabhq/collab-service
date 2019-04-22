package com.collab.backendservice;

import com.collab.backendservice.model.Metrics;
import com.collab.backendservice.service.MetricsService;
import com.collab.backendservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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

        logger.info("Post-Startup Steps Complete");
        return;
    }

}