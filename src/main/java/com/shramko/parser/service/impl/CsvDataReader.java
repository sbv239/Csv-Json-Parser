package com.shramko.parser.service.impl;

import com.shramko.parser.dto.Order;
import com.shramko.parser.components.OrdersQueue;
import com.shramko.parser.exceptions.ParserApplicationException;
import com.shramko.parser.monitors.CsvMonitor;
import com.shramko.parser.monitors.Monitor;
import com.shramko.parser.service.DataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Service
public class CsvDataReader implements DataReader {

    private static final String name = "CsvReader";
    private static final int FIELDS_COUNT = 4;

    private final CsvMonitor csvMonitor;
    private final OrdersQueue ordersQueue;

    @Autowired
    public CsvDataReader(CsvMonitor csvMonitor, OrdersQueue ordersQueue) {
        this.csvMonitor = csvMonitor;
        this.ordersQueue = ordersQueue;
    }

    @Override
    public void read(String filePath) {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != FIELDS_COUNT) {
                    throw new ParserApplicationException("Data format is incorrect in line: " +
                            lineNumber + ", " + file.getName());
                }
                createOrder(file.getName(), lineNumber, values);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        csvMonitor.setDone(true);
    }

    private void createOrder(String fileName, int lineNumber, String[] values) {
        Order order = new Order();
        order.setOrderId(Integer.parseInt(values[0]));
        order.setAmount(values[1]);
        order.setCurrency(values[2]);
        order.setComment(values[3]);
        order.setFileName(fileName);
        order.setLine(lineNumber);
        ordersQueue.saveOrder(order);
    }

    public String getName() {
        return name;
    }

    public Monitor getMonitor() {
        return csvMonitor;
    }
}
