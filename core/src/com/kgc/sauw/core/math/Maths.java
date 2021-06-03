package com.kgc.sauw.core.math;

public class Maths {
    public static double map(double inRangeStart, double inRangeEnd, double outRangeStart, double outRangeEnd, double value) {
        return outRangeStart + (value - inRangeStart) * (outRangeEnd - outRangeStart) / (inRangeEnd - inRangeStart);
    }

    public static boolean rectCrossing(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public static boolean rectCrossing(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public static boolean isLiesOnRect(float x, float y, float w, float h, float px, float py) {
        return px > x && px < x + w && py > y && py < y + h;
    }

    public static boolean isCircleCrossing(int d1, int d2, int x1, int y1, int x2, int y2) {
        int r1 = d1 / 2, r2 = d2 / 2,
                cx1 = x1 - r1, cy1 = y1 - r1,
                cx2 = x2 - r2, cy2 = y2 - r2,
                dx = cx1 - cx2, dy = cy1 - cy2,
                d = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
        if (r1 + r2 < d)
            return true;
        return false;
    }

    public static Vector2d norm(Vector2d v1, Vector2d v2) {
        double x = v2.x - v1.x;
        double y = v2.y - v1.y;
        double norm = x * x + y * y;
        if (norm > 0)
            norm = 0.1 / norm;
        return new Vector2d(Math.signum(x) * Math.sqrt(x * x * norm), Math.signum(y) * Math.sqrt(y * y * norm));
    }

    public static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static Vector2d normMultiplyLenghtD(Vector2d v1, Vector2d v2, int length) {
        return new Vector2d(norm(v1, v2).x * length, norm(v1, v2).y * length);
    }

    public static Vector2d normMultiplyLenghtD(Vector2d v1, Vector2d v2, double length) {
        return new Vector2d(norm(v1, v2).x * length, norm(v1, v2).y * length);
    }

    public static Vector2i normMultiplyLenghtI(Vector2d v1, Vector2d v2, int length) {
        return new Vector2i((int) Math.round(norm(v1, v2).x * length), (int) Math.round(norm(v1, v2).y * length));
    }

    public static Vector2i normMultiplyLenghtI(Vector2d v1, Vector2d v2, double length) {
        return new Vector2i((int) Math.round(norm(v1, v2).x * length), (int) Math.round(norm(v1, v2).y * length));
    }

    public static Vector2d norm(int x1, int y1, int x2, int y2) {
        double x = x2 - x1;
        double y = y2 - y1;
        double norm = x * x + y * y;
        if (norm > 0)
            norm = 0.1 / norm;
        return new Vector2d(Math.signum(x) * Math.sqrt(x * x * norm), Math.signum(y) * Math.sqrt(y * y * norm));
    }

    public static Vector2d normMultiplyLenghtD(int x1, int y1, int x2, int y2, int length) {
        return new Vector2d(norm(x1, y1, x2, y2).x * length, norm(x1, y1, x2, y2).y * length);
    }

    public static Vector2d normMultiplyLenghtD(int x1, int y1, int x2, int y2, double length) {
        return new Vector2d(norm(x1, y1, x2, y2).x * length, norm(x1, y1, x2, y2).y * length);
    }

    public static Vector2i normMultiplyLenghtI(int x1, int y1, int x2, int y2, int length) {
        return new Vector2i((int) Math.round(norm(x1, y1, x2, y2).x * length), (int) Math.round(norm(x1, y1, x2, y2).y * length));
    }

    public static Vector2i normMultiplyLenghtI(int x1, int y1, int x2, int y2, double length) {
        return new Vector2i((int) Math.round(norm(x1, y1, x2, y2).x * length), (int) Math.round(norm(x1, y1, x2, y2).y * length));
    }

    public static Vector2d nvect(double a) {
        return new Vector2d(
                Math.cos(a * Math.PI / 180),
                Math.sin(a * Math.PI / 180)
        );
    }

    public static Vector2i nvectMultiplyLengthI(double a, int length) {
        Vector2d n = nvect(a);
        return new Vector2i((int) Math.round(n.x * length), (int) Math.round(n.y * length));
    }

    public static int angleBetweenVectors(int x1, int y1, int x2, int y2) {
        return (int) Math.round(Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI + 180);
    }
    public static int angleBetweenVectors(float x1, float y1, float x2, float y2) {
        return (int) Math.round(Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI + 180);
    }
    public static int angleBetweenVectors(Vector2d v1, Vector2d v2) {
        return (int) Math.round(Math.atan2(v2.y - v1.y, v2.x - v1.x) * 180 / Math.PI + 180);
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.round(Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (int) Math.round(Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    public static int distance(Vector2d v1, Vector2d v2) {
        return (int) Math.round(Math.sqrt((v2.x - v1.x) * (v2.x - v1.x) + (v2.y - v1.y) * (v2.y - v1.y)));
    }

    public static double distanceD(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static double distanceD(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static double distanceD(Vector2d v1, Vector2d v2) {
        return Math.sqrt((v2.x - v1.x) * (v2.x - v1.x) + (v2.y - v1.y) * (v2.y - v1.y));
    }

    public static int module(int i) {
        if (i < 0)
            return -i;
        return i;
    }

    public static float module(float i) {
        if (i < 0)
            return -i;
        return i;
    }

    public static double module(double i) {
        if (i < 0)
            return -i;
        return i;
    }


}
