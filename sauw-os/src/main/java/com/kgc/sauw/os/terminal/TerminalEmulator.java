package com.kgc.sauw.os.terminal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//TODO: короче, эмулятора терминала нет, так как нет гуи поэтому не трогать :D
public class TerminalEmulator implements Terminal {
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public OutputStream output() {
        return outputStream;
    }

    @Override
    public InputStream input() {
        return inputStream;
    }

    private class TerminalOutputStream extends InputStream {
        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    private class TerminalInputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {

        }
    }
}
