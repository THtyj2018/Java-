package core.math;

/**
 * Immutable int two-dimensional vector class.
 */
public final class IntVector2 {
    public static final IntVector2 ZERO = new IntVector2(0, 0);
    public static final IntVector2 ONE = new IntVector2(1, 1);
    public static final IntVector2 RIGHT = new IntVector2(1, 0);
    public static final IntVector2 UP = new IntVector2(0, 1);
    public static final IntVector2 LEFT = new IntVector2(-1, 0);
    public static final IntVector2 DOWN = new IntVector2(0, -1);
    public static final IntVector2 INF = new IntVector2(Mathf.MAX_INT, Mathf.MAX_INT);
    public static final IntVector2 NEG_INF = new IntVector2(Mathf.MIN_INT, Mathf.MIN_INT);

    public final int x;
    public final int y;

    public IntVector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final boolean equals(IntVector2 vec) {
        return (x == vec.x) && (y == vec.y);
    }

    public final IntVector2 opposite() {
        return new IntVector2(-x, -y);
    }

    public final IntVector2 add(IntVector2 vec) {
        return new IntVector2(x + vec.x, y + vec.y);
    }

    public final IntVector2 add(int v) {
        return new IntVector2(x + v, y + v);
    }

    public final IntVector2 sub(IntVector2 vec) {
        return new IntVector2(x - vec.x, y - vec.y);
    }

    public final IntVector2 sub(int v) {
        return new IntVector2(x - v, y - v);
    }

    public final IntVector2 mul(IntVector2 vec) {
        return new IntVector2(x * vec.x, y * vec.y);
    }

    public final IntVector2 mul(int v) {
        return new IntVector2(x * v, y * v);
    }

    public final IntVector2 div(IntVector2 vec) {
        return new IntVector2(x / vec.x, y / vec.y);
    }

    public final IntVector2 div(int v) {
        return new IntVector2(x / v, y / v);
    }

    public final int dotProduct(IntVector2 vec) {
        return x * vec.x + y * vec.y;
    }

    public final IntVector2 complexProduct(IntVector2 vec) {
        return new IntVector2(x * vec.x - y * vec.y, x * vec.y + y * vec.x);
    }

    public final float length() {
        return Mathf.sqrt(dotProduct(this));
    }

    /**
     * This method returns a string that equals to the value of
     * <blockquote>
     * <pre>
     * Integer.toString(x) + ' ' + Integer.toString(y)
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        return Integer.toString(x) + ' ' + Integer.toString(y);
    }
}