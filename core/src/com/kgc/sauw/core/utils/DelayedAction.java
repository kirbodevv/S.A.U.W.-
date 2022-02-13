package com.kgc.sauw.core.utils;

public class DelayedAction {
    public interface Action {
        void run();
    }

    public DelayedAction(long millis, Action action) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                action.run();
            }
        }.start();
    }
}
