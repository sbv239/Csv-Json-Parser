package com.shramko.parser.service;

import com.shramko.parser.exceptions.ParserApplicationException;

import java.io.IOException;

public interface Processor {
    void convert(String[] files) throws InterruptedException, IOException, ParserApplicationException;
}
