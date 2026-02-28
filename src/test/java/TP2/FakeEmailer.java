package TP2;

import TP2.dependencies.Emailer;

class FakeEmailer implements Emailer {
    String messageSent;

    @Override
    public void send(String message) {
        this.messageSent = message;
    }
}