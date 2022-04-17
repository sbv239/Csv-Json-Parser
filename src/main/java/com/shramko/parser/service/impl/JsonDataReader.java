package com.shramko.parser.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shramko.parser.dto.Order;
import com.shramko.parser.components.OrdersQueue;
import com.shramko.parser.monitors.JsonMonitor;
import com.shramko.parser.monitors.Monitor;
import com.shramko.parser.service.DataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonDataReader implements DataReader {

    private static final String name = "JsonReader";
    private final ObjectMapper mapper = new ObjectMapper();

    private final OrdersQueue ordersQueue;
    private final JsonMonitor jsonMonitor;

    @Autowired
    public JsonDataReader(OrdersQueue ordersQueue, JsonMonitor jsonMonitor) {
        this.ordersQueue = ordersQueue;
        this.jsonMonitor = jsonMonitor;
    }

    @Override
    public void read(String filePath) throws RuntimeException {
        try {
            File jsonFile = new File(filePath);
            Order[] orderList = mapper.readValue(jsonFile, Order[].class);
            int line = 1;
            for (Order order : orderList) {
                order.setLine(line);
                order.setFileName(jsonFile.getName());
                ordersQueue.saveOrder(order);
                line++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        jsonMonitor.setDone(true);
    }

    public String getName() {
        return name;
    }

    public Monitor getMonitor() {
        return jsonMonitor;
    }
}
