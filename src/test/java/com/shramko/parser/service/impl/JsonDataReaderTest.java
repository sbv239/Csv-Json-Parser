package com.shramko.parser.service.impl;

import com.shramko.parser.components.OrdersQueue;
import com.shramko.parser.monitors.JsonMonitor;
import com.shramko.parser.service.DataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataReaderTest {
    private static final String PATH = "src/test/resources/datas.json";
    private static DataReader dataReader;
    OrdersQueue queue;

    @BeforeEach
    void setUp() {
        queue = new OrdersQueue();
        dataReader = new JsonDataReader(queue, new JsonMonitor());
    }

    @Test
    void read() throws InterruptedException, IOException {
        dataReader.read(PATH);
    }
}