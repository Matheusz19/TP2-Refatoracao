package TP2.pipeline;

import TP2.dependencies.Logger;
import TP2.dependencies.Project;

public class TestRunner {

    private final Logger log;

    public TestRunner(Logger log) {
        this.log = log;
    }

    public boolean run(Project project) {
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
}