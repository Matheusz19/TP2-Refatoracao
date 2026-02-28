package TP2.pipeline;

import TP2.dependencies.Logger;
import TP2.dependencies.Project;

public class Deployer {

    private final Logger log;

    public Deployer(Logger log) {
        this.log = log;
    }

    public boolean deploy(Project project, boolean testsPassed) {
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
}