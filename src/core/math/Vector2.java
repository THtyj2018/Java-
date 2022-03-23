package core.math;

/**
 * Immutable float two-dimensional vector class.
 */
public final class Vector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 HALF = new Vector2(0.5f, 0.5f);
    public static final Vector2 ONE = new Vector2(1, 1);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 UP = new Vector2(0, 1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 DOWN = new Vector2(0, -1);
    public static final Vector2 INF = new Vector2(Mathf.INF, Mathf.INF);
    public static final Vector2 NEG_INF = INF.opposite();

    public final float x;
    public final float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final boolean equals(Vector2 vec) {
        return Mathf.equals(x, vec.x) && Mathf.equals(y, vec.y);
    }

    public final Vector2 opposite() {
        return new Vector2(-x, -y);
    }

    public final Vector2 add(Vector2 vec) {
        return new Vector2(x + vec.x, y + vec.y);
    }

    public final Vector2 add(float v) {
        return new Vector2(x + v, y + v); 
    }

    public final Vector2 sub(Vector2 vec) {
        return new Vector2(x - vec.x, y - vec.y);
    }

    public final Vector2 sub(IntVector2 vec) {
        return new Vector2(x - vec.x, y - vec.y);
    }

    public final Vector2 sub(float v) {
        return new Vector2(x - v, y - v); 
    }

    public final Vector2 mul(IntVector2 vec) {
        return new Vector2(x * vec.x, y * vec.y);
    }

    public final Vector2 mul(Vector2 vec) {
        return new Vector2(x * vec.x, y * vec.y);
    }

    public final Vector2 mul(float v) {
        return new Vector2(x * v, y * v); 
    }

    public final Vector2 div(Vector2 vec) {
        return new Vector2(x / vec.x, y / vec.y);
    }

    public final Vector2 div(float v) {
        return new Vector2(x / v, y / v); 
    }

    public final float dotProduct(Vector2 vec) {
        return x * vec.x + y * vec.y;
    }

    public final Vector2 complexProduct(Vector2 vec) {
        return new Vector2(x * vec.x - y * vec.y, x * vec.y + y * vec.x);
    }

    public final float length() {
        return Mathf.sqrt(dotProduct(this));
    }

    public final float angle() {
        return Mathf.atan2(y, x);
    }

    /**
     * Get a unit Vector2 by angle in degree.
     */
    public static Vector2 unit(float angle) {
        return new Vector2(Mathf.cos(angle),Mathf.sin(angle));
    }

    public final Vector2 rotate(float a) {
        return complexProduct(Vector2.unit(a));
    }

    public final Vector2 normalize() {
        float len = length();
        if (Mathf.equals(len, 0) || Mathf.equals(len, 1)) {
            return this;
        } else {
            return div(len);
        }
    }

    public final Vector2 promiseEpsilon() {
        float nx = x, ny = y;
        boolean change = false;
        if (Mathf.equals(x, 0)) {
            nx = x < 0 ? -Mathf.EPSILON : Mathf.EPSILON;
            change = true;
        }
        if (Mathf.equals(y, 0)) {
            ny = y < 0 ? -Mathf.EPSILON : Mathf.EPSILON;
            change = true;
        }
        return change ? new Vector2(nx, ny) : this;
    }

    /**
     * This method returns a string that equals to the value of
     * <blockquote>
     * <pre>
     * Float.toString(x) + ' ' + Float.toString(y)
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        return Float.toString(x) + ' ' + Float.toString(y);
    }
}