package com.kgc.sauw.os.terminal;

import java.io.InputStream;
import java.io.OutputStream;

public class SystemTerminal implements Terminal {
    @Override
    public OutputStream output() {
        return System.out;
    }

    @Override
    public InputStream input() {
        return System.in;
    }
}
