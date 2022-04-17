package com.shramko.parser.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shramko.parser.dto.Order;
import com.shramko.parser.monitors.CsvMonitor;
import com.shramko.parser.monitors.JsonMonitor;
import com.shramko.parser.service.DataWriter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;


@Service
public class JsonDataWriter implements DataWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    private final JsonMonitor jsonMonitor;
    private final CsvMonitor csvMonitor;

    @Autowired
    public JsonDataWriter(JsonMonitor jsonMonitor, CsvMonitor csvMonitor) {
        this.jsonMonitor = jsonMonitor;
        this.csvMonitor = csvMonitor;
    }

    @Override
    public void write(BlockingQueue<Order> orders) {
        int i = 0;
        while (!orders.isEmpty() || isReadFinished()) {
            try {
                Order order = orders.take();
                processingOutputOrder(++i, order);
                System.out.println(mapper.writeValueAsString(order));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private void processingOutputOrder(int i, Order order) {
        boolean hasErrors = false;
        String result = "Ok";
        StringBuilder sb = new StringBuilder("Field are incorrect:");
        if (order.getAmount() == null || order.getAmount().isEmpty() || !NumberUtils.isParsable(order.getAmount())) {
            sb.append(" ammount,");
            hasErrors = true;
        }
        if (order.getCurrency() == null || order.getCurrency().isEmpty()) {
            sb.append(" currency,");
            hasErrors = true;
        }
        result = hasErrors ? sb.deleteCharAt(sb.length() - 1).toString() : result;
        order.setResult(result);
        order.setId(i);
    }

    private boolean isReadFinished() {
        return !(jsonMonitor.isDone() && csvMonitor.isDone());
    }
}
