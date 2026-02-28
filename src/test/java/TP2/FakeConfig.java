package TP2;

import TP2.dependencies.Config;

class FakeConfig implements Config {
    private final boolean sendEmail;

    public FakeConfig(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public boolean sendEmailSummary() {
        return sendEmail;
    }
}