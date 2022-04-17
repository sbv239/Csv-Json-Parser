package com.shramko.parser.exceptions;

public class ParserApplicationException extends RuntimeException {
    private static final String PREFIX = "Parser application: ";

    public ParserApplicationException(String message) {
        super(PREFIX + message);
    }


}
