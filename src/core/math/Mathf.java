package core.math;

import java.util.Random;

/**
 * Math functions class.
 * <p>
 * All the functions use angle in degree.
 */
public final class Mathf {
    public static final float PI = (float) java.lang.Math.PI;
    public static final float EPSILON = 1e-6f;
    public static final float RAD_TO_DEG = (float) (180 / java.lang.Math.PI);
    public static final float DEG_TO_RAD = (float) (java.lang.Math.PI / 180);
    public static final float INF = Float.MAX_VALUE;
    public static final int MAX_INT = Integer.MAX_VALUE;
    public static final int MIN_INT = Integer.MIN_VALUE;

    /**
     * Compare two float value with an epsilon.
     */
    public static boolean equals(float a, float b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static int min(int a, int b) {
        return (a > b) ? b : a;
    }

    public static float min(float a, float b) {
        return (a > b) ? b : a;
    }

    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static float max(float a, float b) {
        return (a > b) ? a : b;
    }

    public static int clamp(int v, int min, int max) {
        if (v < min) {
            return min;
        } else if (v > max) {
            return max;
        } else {
            return v;
        }
    }

    public static float clamp(float v, float min, float max) {
        if (v < min) {
            return min;
        } else if (v > max) {
            return max;
        } else {
            return v;
        }
    }

    public static float lerp(float v1, float v2, float factor) {
        return v1 * (1 - factor) + v2 * factor;
    }

    public static float inverseLerp(float v1, float v2, float v) {
        return (v - v1) / (v2 - v1);
    }

    public static boolean inRange(int v, int min, int max) {
        if (v >= min && v < max) {
            return true;
        }
        return false;
    }

    public static boolean inRange(float v, float min, float max) {
        if (v >= min && v < max) {
            return true;
        }
        return false;
    }

    public static float sqrt(int v) {
        return (float) java.lang.Math.sqrt(v);
    }

    public static float sqrt(float v) {
        return (float) java.lang.Math.sqrt(v);
    }

    public static float sin(float a) {
        return (float) java.lang.Math.sin(a * DEG_TO_RAD);
    }

    public static float cos(float a) {
        return (float) java.lang.Math.cos(a * DEG_TO_RAD);
    }

    public static float tan(float a) {
        return (float) java.lang.Math.tan(a * DEG_TO_RAD);
    }

    public static float asin(float s) {
        return (float) java.lang.Math.asin(s) * RAD_TO_DEG;
    }

    public static float acos(float c) {
        return (float) java.lang.Math.acos(c) * RAD_TO_DEG;
    }

    public static float atan(float t) {
        return (float) java.lang.Math.atan(t) * RAD_TO_DEG;
    }

    public static float atan2(float y, float x) {
        return (float) java.lang.Math.atan2(y, x) * RAD_TO_DEG;
    }

    private static final Random  randInst = new Random(System.nanoTime());

    public static float random() {
        return (float) randInst.nextDouble();
    }

    public static int randInt() {
        return randInst.nextInt(65536);
    }
}