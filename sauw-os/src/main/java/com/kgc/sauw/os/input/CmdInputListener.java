package com.kgc.sauw.os.input;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.os.SAUWOS;

import java.util.Scanner;

public class CmdInputListener extends Thread {
    public Scanner scanner;
    private boolean closed = false;
    private SAUWOS os;

    public CmdInputListener(SAUWOS os) {
        this.os = os;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        super.run();
        Gdx.app.log("S.A.U.W. OS", "input listener started");
        while (true) {
            if (!closed) {
                if (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    os.console.execute(line, os);
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
