package com.boroborome.stledtor.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

public class LocatedStream extends InputStream {
    private final InputStream stream;

    private int lineNum;
    private int column;
    private StringBuilder readChInLine = new StringBuilder();

    public LocatedStream(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public int read() throws IOException {
        int ch = stream.read();
        if (ch < 0) {
            return ch;
        }

        if (ch == '\n') {
            column = 0;
            lineNum++;
            readChInLine.setLength(0);
        } else {
            column++;
            readChInLine.append((char) ch);
        }
        return ch;
    }

    public String locateMessage() {
        return MessageFormat.format("Line:{0}; Column:{1}; Consumed message:{2}", lineNum, column, readChInLine);
    }
}
