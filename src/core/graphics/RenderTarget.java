package core.graphics;

import java.awt.image.VolatileImage;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;

import core.Context;
import core.math.IntVector2;
import core.math.Vector2;

public class RenderTarget {
    VolatileImage img;

    private Graphics2D gfx_;
    private Paint storedPaint_;
    private Stroke storedStroke_;

    public Vector2 viewcenter = Vector2.ZERO;
    public Vector2 viewscale;

    RenderTarget() {
    }

    final void create(int width, int height) {
        img = Context.window.frame.createVolatileImage(width, height);
        viewscale = new Vector2(Context.params.width / 32, Context.params.height / 32);
    }

    public RenderTarget(int width, int height) {
        create(width, height);
    }

    public final int getWidth() {
        return img.getWidth();
    }

    public final int getHeight() {
        return img.getHeight();
    }

    public final IntVector2 getSize() {
        return new IntVector2(img.getWidth(), img.getHeight());
    }

    public final IntVector2 toPixel(Vector2 position) {
        return toPixel(position.x, position.y);
    }

    public final IntVector2 toPixel(float x, float y) {
        return new IntVector2(
            (int)((viewscale.x / 2 + (x - viewcenter.x)) * getWidth() / viewscale.x),
            (int)((viewscale.y / 2 - (y - viewcenter.y)) * getHeight() / viewscale.y)
        );
    }

    public final Vector2 toWorld(IntVector2 coord) {
        return new Vector2(
            ((float)coord.x) / getWidth() * viewscale.x - viewscale.x / 2 + viewcenter.x,
            viewscale.y / 2 - ((float)coord.y) / getHeight() * viewscale.y + viewcenter.y
        );
    }

    public Texture getAsTexture() {
        return new Texture(img.getSnapshot());
    }

    public final void beginDraw(Color clearColor) {
        if (img.validate(Context.window.frame.getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
            create(img.getWidth(), img.getHeight());
        }
        gfx_ = img.createGraphics();
        if (clearColor != null) {
            gfx_.setPaint(Context.params.clearColor);
            gfx_.fillRect(0, 0, img.getWidth(), img.getHeight());
        }
        gfx_.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gfx_.getRenderingHints().put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    public final void endDraw() {
        gfx_.dispose();
    }

    private void storePaint() {
        storedPaint_ = gfx_.getPaint();
    }

    private void recoverPaint() {
        gfx_.setPaint(storedPaint_);
    }

    private void storeStroke() {
        storedStroke_ = gfx_.getStroke();
    }

    private void recoverStroke() {
        gfx_.setStroke(storedStroke_);
    }

    /**
     * Fill a rect. To use texture, set <code>state.texture</code> value
     * 
     * @param size  Rect size in local space.
     * @param state
     */
    public final void fillRect(Vector2 size, RenderState state) {
        if (state == null || state.position == null) {
            return;
        }
        IntVector2 coord = toPixel(state.position.x - size.x / 2, state.position.y  + size.y / 2);
        int sx = (int)(size.x * getWidth() / viewscale.x);
        int sy = (int)(size.y * getHeight() / viewscale.y);

        storeStroke();
        
        if (state.stroke != null) {
            gfx_.setStroke(state.stroke);
        }
        
        if (state.texture == null) {
            storePaint();
            gfx_.setPaint(state.color);
            gfx_.fillRect(coord.x, coord.y, sx, sy);
            recoverPaint();
        } else {
            gfx_.drawImage(state.texture.img, coord.x, coord.y, sx, sy, null);
        }
        
        recoverStroke();
    }

    public final void drawString(String str, TextState state) {
        storePaint();

        gfx_.setPaint(state.color);
        gfx_.setFont(state.font);
        FontMetrics m = gfx_.getFontMetrics();
        if (state.center) {
            gfx_.drawString(str, state.coord.x - m.stringWidth(str) / 2,
                    state.coord.y - m.getHeight() / 2 + m.getAscent());
        } else {
            gfx_.drawString(str, state.coord.x, state.coord.y + m.getAscent());
        }

        recoverPaint();
    }
}