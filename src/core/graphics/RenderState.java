package core.graphics;

import java.awt.Color;
import java.awt.Stroke;

import core.Context;
import core.math.Vector2;

public class RenderState {
    public Texture texture = null;
    public Color color = (Context.params.clearColor == null) ? Color.BLACK : Context.params.clearColor;
    public Stroke stroke = null;
    public Vector2 position = Vector2.ZERO;

    public void assign(RenderState state) {
        texture = state.texture;
        color = state.color;
        stroke = state.stroke;
    }
}