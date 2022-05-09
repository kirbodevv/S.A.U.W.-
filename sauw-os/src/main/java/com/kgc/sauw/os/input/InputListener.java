package com.kgc.sauw.os.input;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.os.SAUWOS;
import com.kgc.sauw.os.terminal.Terminal;

import java.util.Scanner;

public class InputListener extends Thread {
    public Scanner scanner;
    private boolean closed = false;
    private final SAUWOS os;

    public InputListener(SAUWOS os, Terminal terminal) {
        this.os = os;
        scanner = new Scanner(terminal.input());
    }

    @Override
    public void run() {
        super.run();
        Gdx.app.log("S.A.U.W. OS", "input listener started");
        while (true) {
            if (!closed) {
                if (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    os.commandProcessor.execute(line, os);
                }
            } else break;
        }
        scanner.close();
        Gdx.app.log("S.A.U.W. OS", "input listener closed");
    }

    public void close() {
        closed = true;
    }
}
