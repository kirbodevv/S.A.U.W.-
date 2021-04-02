package com.kgc.sauw.environment;

import com.kgc.sauw.map.World;

import java.util.Timer;
import java.util.TimerTask;

public class Time {
    public class time extends Thread {
        public int time = 360;
        private Timer timer;

        @Override
        public void run() {
            super.run();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    time += 1;
                    if (time > 24 * 60) {
                        time = 0;
                    }
                }
            }, 0, 1000);
        }
    }

    time time;

    public Time() {
        time = new time();
        time.run();
    }

    public void setTime(int time) {
        this.time.time = time;
    }

    public int getTime() {
        return time.time;
    }

    public int getHours() {
        int h = (time.time - (time.time % 60)) / 60;
        return h;
    }

    public int getMin() {
        int m = time.time % 60;
        return m;
    }

    public String getTimeString() {
        String time = "";
        if (getHours() < 10) {
            time += "0" + getHours();
        } else {
            time += getHours();
        }
        time += ":";
        if (getMin() < 10) {
            time += "0" + getMin();
        } else {
            time += getMin();
        }
        return time;
    }
}
