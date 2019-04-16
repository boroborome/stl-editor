package com.boroborome.stledtor.util;

import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.io.PrintStream;

@Getter
@Setter
public class TabPrintStream extends PrintStream {
    private int tabs;

    public TabPrintStream(OutputStream out) {
        super(out);
    }

    public TabPrintStream(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public void increaseTab() {
        tabs++;
    }xxxxxxx

    public void descreaseTab() {
        tabs--;
    }

    @Override
    public void write(int b) {
        super.write(b);
        if (b == '\n') {
            for (int i = 0;i < tabs; i++) {
                super.write('\t');
            }
        }
    }

    private static class TabBufferedStream {
        
    }
}
