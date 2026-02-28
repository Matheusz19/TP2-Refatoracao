package TP2;

import TP2.dependencies.*;
import TP2.pipeline.Deployer;
import TP2.pipeline.PipelineResult;
import TP2.pipeline.TestRunner;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;
    private final TestRunner testRunner;
    private final Deployer deployer;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
        this.testRunner = new TestRunner(log);
        this.deployer = new Deployer(log);
    }

    public void run(Project project) {
        PipelineResult result = process(project);
        handleEmail(result);
    }

    private PipelineResult process(Project project) {
        boolean testsPassed = testRunner.run(project);
        boolean deploySuccessful = deployer.deploy(project, testsPassed);

        return new PipelineResult(testsPassed, deploySuccessful);
    }

    private void handleEmail(PipelineResult result) {
        if (config.sendEmailSummary()) {
            log.info("Sending email");
            emailer.send(result.getEmailMessage());
        } else {
            log.info("Email disabled");
        }
    }
}