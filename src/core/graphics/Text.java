package core.graphics;

import java.awt.Color;
import java.awt.Font;

import core.math.IntVector2;

/**
 * Note that some methods from {@code Drawable} class is invalid since text is
 * rendered in user space coordinate.
 * <p>Invalid methods :
 * @see Drawable#setSize(engine.math.Vector2)
 */
public final class Text extends Drawable {
    private String text_ = "";
    private final TextState textstate_ = new TextState();

    @Override
    protected void draw(RenderTarget target) {
        target.drawString(text_, textstate_);
    }

    public Text setText(String text) {
        text_ = (text == null) ? "" : text;
        return this;
    }

    public String getText() {
        return text_;
    }

    public Text setCoord(IntVector2 coord) {
        textstate_.coord = coord;
        return this;
    }

    public Text setCoord(int x, int y) {
        textstate_.coord = new IntVector2(x, y);
        return this;
    }

    public IntVector2 getCoord() {
        return textstate_.coord;
    }

    public Text setUseCenter(boolean usecenter) {
        textstate_.center = usecenter;
        return this;
    }

    public boolean isUseCenter() {
        return textstate_.center;
    }

    public Text setColor(Color color) {
        textstate_.color = color;
        return this;
    }

    public Color getColor() {
        return textstate_.color;
    }

    public Text setFontName(String name) {
        Font font = textstate_.font;
        textstate_.font = new Font(name, font.getStyle(), font.getSize());
        return this;
    }

    public String getFontName() {
        return textstate_.font.getFontName();
    }

    public Text setFontSize(int size) {
        if (textstate_.font.getSize() != size) {
            textstate_.font = textstate_.font.deriveFont(textstate_.font.getStyle(), size);
        }
        return this;
    }

    public int getFontSize() {
        return textstate_.font.getSize();
    }

    public Text setBold(boolean bold) {
        if (textstate_.font.isBold() != bold) {
            textstate_.font = textstate_.font.deriveFont(textstate_.font.getStyle() ^ Font.BOLD);
        }
        return this;
    }

    public boolean isBold() {
        return textstate_.font.isBold();
    }

    public Text setItalic(boolean italic) {
        if (textstate_.font.isItalic() != italic) {
            textstate_.font = textstate_.font.deriveFont(textstate_.font.getStyle() ^ Font.ITALIC);
        }
        return this;
    }

    public boolean isItalic() {
        return textstate_.font.isItalic();
    }

    @Override
    public Drawable getCopy() {
        Text text = new Text();
        text.text_ = text_;
        text.textstate_.assign(textstate_);
        text.visible = visible;
        return text;
    }
}