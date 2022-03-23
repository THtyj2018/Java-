package core.graphics;

import java.awt.Color;

public abstract class Shape extends Drawable {
    protected final RenderState state = new RenderState();

    public Shape setColor(Color color) {
        state.color = color;
        return this;
    }

    public Color getColor() {
        return state.color;
    }
}