package TP2;

import TP2.dependencies.Config;
import TP2.dependencies.Emailer;
import TP2.dependencies.Logger;
import TP2.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsPassed = executeTests(project);
        boolean deploySuccessful = executeDeployment(project, testsPassed);
        handleEmail(testsPassed, deploySuccessful);
    }

    private boolean executeTests(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return true;
        }

        if (project.testsPassed()) {
            log.info("Tests passed");
            return true;
        }

        log.error("Tests failed");
        return false;
    }

    private boolean executeDeployment(Project project, boolean testsPassed) {
        if (!testsPassed) {
            return false;
        }

        if (project.deploySuccessful()) {
            log.info("Deployment successful");
            return true;
        }

        log.error("Deployment failed");
        return false;
    }

    private void handleEmail(boolean testsPassed, boolean deploySuccessful) {
        if (config.sendEmailSummary()) {
            log.info("Sending email");

            if (!testsPassed) {
                emailer.send("Tests failed");
                return;
            }

            if (deploySuccessful) {
                emailer.send("Deployment completed successfully");
            } else {
                emailer.send("Deployment failed");
            }
        } else {
            log.info("Email disabled");
        }
    }
}