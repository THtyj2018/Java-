package core.math;

/**
 * Immutable int rectangle class.
 */
public final class IntRect {
    public static final IntRect EMPTY = new IntRect(IntVector2.INF, IntVector2.NEG_INF);
    public static final IntRect ZERO = new IntRect(IntVector2.ZERO, IntVector2.ZERO);
    public static final IntRect IDENTITY = new IntRect(IntVector2.ZERO, IntVector2.ONE);

    public final IntVector2 min;
    public final IntVector2 max;

    public IntRect(IntVector2 min, IntVector2 max) {
        this.min = min;
        this.max = max;
        toString();
    }

    public IntRect(int left, int top, int right, int bottom) {
        min = new IntVector2(left, top);
        max = new IntVector2(right, bottom);
    }

    public boolean equals(IntRect rect) {
        return min.equals(rect.min) && max.equals(rect.max);
    }

    public final int left() {
        return min.x;
    }

    public final int top() {
        return min.y;
    }

    public final int right() {
        return max.x;
    }

    public final int bottom() {
        return max.y;
    }

    public final int width() {
        return max.x - min.x;
    }

    public final int height() {
        return max.y - min.y;
    }

    public final IntVector2 size() {
        return max.sub(min);
    }

    public final IntVector2 center() {
        return min.add(max).div(2);
    }

    public final IntRect add(IntRect rect) {
        return new IntRect(min.add(rect.min), max.add(rect.max));
    }

    public final IntRect add(IntVector2 vec) {
        return new IntRect(min.add(vec), max.add(vec));
    }

    public final IntRect add(int v) {
        return new IntRect(min.add(v), max.add(v));
    }

    public final IntRect sub(int v) {
        return new IntRect(min.sub(v), max.sub(v));
    }

    public final IntRect sub(IntRect rect) {
        return new IntRect(min.sub(rect.min), max.sub(rect.max));
    }

    public final IntRect sub(IntVector2 vec) {
        return new IntRect(min.sub(vec), max.sub(vec));
    }

    public final IntRect mul(IntRect rect) {
        return new IntRect(min.mul(rect.min), max.mul(rect.max));
    }

    public final IntRect mul(int v) {
        return new IntRect(min.mul(v), max.mul(v));
    }

    public final IntRect div(IntRect rect) {
        return new IntRect(min.div(rect.min), max.div(rect.max));
    }

    public final IntRect div(int v) {
        return new IntRect(min.div(v), max.div(v));
    }

    public final boolean includes(IntVector2 vec) {
        return Mathf.inRange(vec.x, min.x, max.x) && Mathf.inRange(vec.y, min.y, max.y);
    }

    public final boolean includes(int x, int y) {
        return Mathf.inRange(x, min.x, max.x) && Mathf.inRange(y, min.y, max.y);
    }

    public final boolean intersects(IntRect rect) {
        int left = rect.left();
        int top = rect.top();
        int right = rect.right();
        int bottom = rect.bottom();
        return includes(left, top) || includes(left, bottom) || includes(right, top) || includes(right, bottom);
    }

    public final IntRect merge(IntVector2 point) {
        IntVector2 newmin = new IntVector2(Mathf.min(min.x, point.x), Mathf.min(min.y, point.y));
        IntVector2 newmax = new IntVector2(Mathf.max(max.x, point.x), Mathf.max(max.y, point.y));
        return new IntRect(newmin, newmax);
    }

    public final IntRect merge(IntRect rect) {
        IntVector2 newmin = new IntVector2(Mathf.min(min.x, rect.min.x), Mathf.min(min.y, rect.min.y));
        IntVector2 newmax = new IntVector2(Mathf.max(max.x, rect.max.x), Mathf.max(max.y, rect.max.y));
        return new IntRect(newmin, newmax);
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