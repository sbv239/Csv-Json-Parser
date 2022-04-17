package com.shramko.parser.monitors;

public class Monitor {
    volatile boolean isDone = true;

    public boolean isDone() {
        return isDone;
    }
    public void setDone(boolean done) {
        isDone = done;
    }
}
