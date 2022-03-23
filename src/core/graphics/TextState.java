package core.graphics;

import java.awt.Color;
import java.awt.Font;

import core.Context;
import core.math.IntVector2;

public class TextState {
    public Color color = (Context.params.clearColor == null) ? Color.WHITE : new Color(0xFFFFFF - Context.params.clearColor.getRGB());
    public Font font  = new Font(null, Font.PLAIN, 12);
    public IntVector2 coord = IntVector2.ZERO;
    public boolean center = false;

    public void assign(TextState state) {
        color = state.color;
        font = state.font;
        coord = state.coord;
        center = state.center;
    }
}