package com.shramko.parser.service.impl;

import com.shramko.parser.components.OrdersQueue;
import com.shramko.parser.dto.Extension;
import com.shramko.parser.exceptions.ParserApplicationException;
import com.shramko.parser.service.DataReader;
import com.shramko.parser.service.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProcessorImpl implements Processor {

    private final CsvDataReader csvDataReader;
    private final JsonDataReader jsonDataReader;
    private final JsonDataWriter jsonDataWriter;
    private final OrdersQueue ordersQueue;

    @Autowired
    public ProcessorImpl(CsvDataReader csvDataReader, JsonDataReader jsonDataReader,
                         JsonDataWriter jsonDataWriter, OrdersQueue ordersQueue) {
        this.csvDataReader = csvDataReader;
        this.jsonDataReader = jsonDataReader;
        this.jsonDataWriter = jsonDataWriter;
        this.ordersQueue = ordersQueue;
    }

    @Override
    public void convert(String[] fileNames) throws ParserApplicationException {

//        fileNames[0] = "src/main/resources/Files/datas.csv";
//        fileNames[1] = "src/main/resources/Files/datas.json";

        if (fileNames == null || fileNames.length == 0) {
            throw new ParserApplicationException("There are no files to convert");
        }
        int validFiles = 0;
        for (String fileName : fileNames) {
            switch (Extension.getFileExtension(fileName)) {
                case JSON:
                    readFile(jsonDataReader, fileName);
                    validFiles++;
                    break;
                case CSV:
                    readFile(csvDataReader, fileName);
                    validFiles++;
                    break;
                default:
            }
        }
        if (validFiles == 0) {
            throw new ParserApplicationException("Unknown file format");
        }
        Thread threadWriter = new Thread(() -> jsonDataWriter.write(ordersQueue.getOrders()));
        threadWriter.start();
    }

    private void readFile(DataReader dataReader, String fileName) {
        Thread thread = new Thread(() -> {
            try {
                dataReader.read(fileName);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }, dataReader.getName());
        dataReader.getMonitor().setDone(false);
        thread.start();
    }
}
