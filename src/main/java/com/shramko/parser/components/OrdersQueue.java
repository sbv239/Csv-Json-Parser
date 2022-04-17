package com.shramko.parser.components;

import com.shramko.parser.dto.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class OrdersQueue {
    private static final int CAPACITY = 30;
    private final BlockingQueue<Order> orders = new ArrayBlockingQueue<>(CAPACITY);

    public void saveOrder(Order order) {
        try {
            orders.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    public BlockingQueue<Order> getOrders() {
        return orders;
    }
}
