package com.boroborome.stledtor.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class IndicatorIterator extends AbstractBufferIterator<String> {
    private static final List<Character> OperatorChs = Arrays.asList('[', ']', '{', '}', ',', '\n');
    private final InputStream stream;
    private int nextCh;

    public IndicatorIterator(InputStream stream) {
        this.stream = stream;
        readChar();
        curItem = findNextIndicator();
    }

    @Override
    public boolean hasNext() {
        return curItem != null && !curItem.isEmpty();
    }

    private int readChar() {
        try {
            nextCh = stream.read();
        } catch (IOException e) {
            e.printStackTrace();
            nextCh = -1;
        }
        return nextCh;
    }

    @Override
    public String next() {
        String result = curItem;
        curItem = findNextIndicator();
        return result;
    }

    private String findNextIndicator() {
        StringBuilder buf = new StringBuilder();
        ignoreSpaceCh();
        if (nextCh < 0) {
            return null;
        }
        buf.append((char) nextCh);
        if (OperatorChs.contains((char) nextCh)) {
            readChar();
        } else {
            readToNextSpaceOrOperator(buf);
        }
        return buf.toString();
    }


    private void ignoreSpaceCh() {
        while (Character.isWhitespace(nextCh)) {
            readChar();
        }
        if (nextCh == '#') {
            gotoLineFinish();
        }
    }

    private void gotoLineFinish() {
        while (nextCh != -1 && nextCh != '\n') {
            readChar();
        }
    }

    private void readToNextSpaceOrOperator(StringBuilder buf) {
        while (true) {
            int ch = readChar();
            if (ch < 0
                || OperatorChs.contains((char) ch)
                || Character.isWhitespace(ch)) {
                break;
            }
            buf.append((char) nextCh);
        }
    }

    public void ignore(String indicator) {
        if (Objects.equals(current(), indicator)) {
            next();
        }
    }

}
