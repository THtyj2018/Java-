package core.graphics;

import java.util.Comparator;

import core.math.Vector2;

public abstract class Drawable {
    public boolean visible = true;
    public int layer = 0;

    private Vector2 size_ = Vector2.ONE;
    private Vector2 position_ = Vector2.ZERO;

    public static final Comparator<? super Drawable> comparator = (a, b) -> {
        return Integer.compare(a.layer, b.layer);
    };

    public Drawable() {
    }

    public abstract Drawable getCopy();

    protected abstract void draw(RenderTarget target);

    public final Drawable setSize(Vector2 size) {
        if (size != null) {
            size_ = size.promiseEpsilon();
            onSizeChanged();
        }
        return this;
    }

    public final Drawable setSize(float w, float h) {
        size_ = new Vector2(w, h).promiseEpsilon();
        onSizeChanged();
        return this;
    }

    protected void onSizeChanged() {
    }

    public final Vector2 getSize() {
        return size_;
    }

    public final Drawable setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public final Drawable setPosition(Vector2 position) {
        if (position == null) {
            throw new NullPointerException();
        }
        position_ = position;
        return this;
    }

    public final Drawable setPosition(float x, float y) {
        position_ = new Vector2(x, y);
        return this;
    }

    public Vector2 getPosition() {
        return position_;
    }
}