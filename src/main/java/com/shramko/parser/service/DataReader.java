package com.shramko.parser.service;

import com.shramko.parser.monitors.Monitor;

import java.io.IOException;

public interface DataReader {
    void read(String filePath) throws InterruptedException, IOException;
    String getName();
    Monitor getMonitor();
}
