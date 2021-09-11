package com.kgc.sauw.core.environment.world;

public class Time {
    private int time = 360;
    private int msTime = 0;

    public void updateTime() {
        msTime++;
        if (msTime >= 20) {
            time += 1;
            if (time > 24 * 60) {
                time = 0;
            }
            msTime = 0;
        }
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public int getHours() {
        return (time - (time % 60)) / 60;
    }

    public int getMin() {
        return time % 60;
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
