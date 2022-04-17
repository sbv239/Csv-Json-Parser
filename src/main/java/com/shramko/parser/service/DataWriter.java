package com.shramko.parser.service;

import com.shramko.parser.dto.Order;
import java.util.concurrent.BlockingQueue;

public interface DataWriter {
    void write(BlockingQueue<Order> orders) throws InterruptedException;
}
