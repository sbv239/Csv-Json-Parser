package com.shramko.parser.service.impl;

import com.shramko.parser.exceptions.ParserApplicationException;
import com.shramko.parser.service.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorImplTest {

    private static final String[] WRONG_FILE_FORMATS = {"src/main/resources/Files/datas.xml",
            "src/main/resources/Files/datas.php"};
    private static final String[] EMPTY_FILES = new String[0];
    private static Processor processor;

    @BeforeEach
    void beforeTest() {
        processor = new ProcessorImpl(null, null, null, null);
    }

    @Test
    void assertThrowsParserApplicationException() {
        assertThrows(ParserApplicationException.class, () -> processor.convert(null));
        assertThrows(ParserApplicationException.class, () -> processor.convert(EMPTY_FILES));

        ParserApplicationException exc = assertThrows(ParserApplicationException.class,
                () -> processor.convert(WRONG_FILE_FORMATS));
        assertEquals("Parser application: Unknown file format", exc.getMessage());

    }
}