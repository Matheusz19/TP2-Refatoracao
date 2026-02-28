package TP2.pipeline;

public class PipelineResult {

    private final boolean testsPassed;
    private final boolean deploymentSuccessful;

    public PipelineResult(boolean testsPassed, boolean deploymentSuccessful) {
        this.testsPassed = testsPassed;
        this.deploymentSuccessful = deploymentSuccessful;
    }

    public boolean testsPassed() {
        return testsPassed;
    }

    public boolean deploymentSuccessful() {
        return deploymentSuccessful;
    }

    public boolean isSuccess() {
        return testsPassed && deploymentSuccessful;
    }

    public String getEmailMessage() {
        if (!testsPassed) {
            return "Tests failed";
        }

        if (deploymentSuccessful) {
            return "Deployment completed successfully";
        }

        return "Deployment failed";
    }
}