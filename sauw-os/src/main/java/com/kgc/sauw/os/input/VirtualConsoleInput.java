package com.kgc.sauw.os.input;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.CharBuffer;

public class VirtualConsoleInput implements Readable {
    @Override
    public int read(@NotNull CharBuffer cb) throws IOException {
        return 0;
    }
}
