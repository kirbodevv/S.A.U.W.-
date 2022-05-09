package com.kgc.sauw.os.terminal;

import java.io.InputStream;
import java.io.OutputStream;

public interface Terminal {
    OutputStream output();

    InputStream input();
}
