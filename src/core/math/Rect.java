package core.math;

public final class Rect {
    public static final Rect ZERO = new Rect(Vector2.ZERO, Vector2.ZERO);
    public static final Rect EMPTY = new Rect(Vector2.INF, Vector2.NEG_INF);
    public static final Rect IDENTITY = new Rect(Vector2.ZERO, Vector2.ONE);
    
    public final Vector2 min;
    public final Vector2 max;

    public Rect(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
    }

    public Rect(float x, float y, float w, float h) {
        min = new Vector2(x, y);
        max = new Vector2(x + w, y + h);
    }

    public boolean equals(Rect rect) {
        return min.equals(rect.min) && max.equals(rect.max);
    }

    public final float x() {
        return min.x;
    }

    public final float y() {
        return min.y;
    }

    public final float width() {
        return max.x - min.x;
    }

    public final float height() {
        return max.y - min.y;
    }

    public final Vector2 size() {
        return max.sub(min);
    }

    public final Vector2 center() {
        return max.add(min).div(2);
    }

    public final Rect add(Rect rect) {
        return new Rect(min.add(rect.min), max.add(rect.max));
    }

    public final Rect add(Vector2 vec) {
        return new Rect(min.add(vec), max.add(vec));
    }

    public final Rect add(float v) {
        return new Rect(min.add(v), max.add(v));
    }

    public final Rect sub(Rect rect) {
        return new Rect(min.sub(rect.min), max.sub(rect.max));
    }

    public final Rect sub(Vector2 vec) {
        return new Rect(min.sub(vec), max.sub(vec));
    }

    public final Rect sub(float v) {
        return new Rect(min.sub(v), max.sub(v));
    }

    public final Rect mul(Rect rect) {
        return new Rect(min.mul(rect.min), max.mul(rect.max));
    }

    public final Rect mul(float v) {
        return new Rect(min.mul(v), max.mul(v));
    }

    public final Rect div(Rect rect) {
        return new Rect(min.div(rect.min), max.div(rect.max));
    }

    public final Rect div(float c) {
        return new Rect(min.div(c), max.div(c));
    }

    public final boolean includes(Vector2 vec) {
        return Mathf.inRange(vec.x, min.x, max.x) && Mathf.inRange(vec.y, min.y, max.y);
    }

    public final boolean includes(float x, float y) {
        return Mathf.inRange(x, min.x, max.x) && Mathf.inRange(y, min.y, max.y);
    }

    public final boolean intersects(Rect rect) {
        float x = rect.x();
        float y = rect.y();
        float xw = rect.max.x;
        float yh = rect.max.y;
        return includes(x, y) || includes(x, yh) || includes(xw, y) || includes(xw, yh);
    }

    public final Rect merge(Vector2 point) {
        Vector2 newmin = new Vector2(Mathf.min(min.x, point.x), Mathf.min(min.y, point.y));
        Vector2 newmax = new Vector2(Mathf.max(max.x, point.x), Mathf.max(max.y, point.y));
        return new Rect(newmin, newmax);
    }

    public final Rect merge(Rect rect) {
        Vector2 newmin = new Vector2(Mathf.min(min.x, rect.min.x), Mathf.min(min.y, rect.min.y));
        Vector2 newmax = new Vector2(Mathf.max(max.x, rect.max.x), Mathf.max(max.y, rect.max.y));
        return new Rect(newmin, newmax);
    }

    /**
     * This method returns a string that equals to the value of
     * <blockquote>
     * <pre>
     * min.toString() + ' ' + max.toString()
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        return min.toString() + ' ' + max.toString();
    }
}