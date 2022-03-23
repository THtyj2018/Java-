package core.graphics;

import java.util.ArrayList;

import core.Context;

public final class Renderer {
    private RenderTarget target_ = null;

    public void setTarget(RenderTarget target) {
        target_ = target;
    }

    public RenderTarget getTarget() {
        return target_;
    }

    public void render() {
        if (Context.sceneMgr.getCurrentScene() == null) {
            return;
        }

        ArrayList<Drawable> drawables = Context.sceneMgr.getCurrentScene().getAllDrawables();

        target_.beginDraw(Context.params.clearColor);
        for (Drawable drawable : drawables) {
            drawable.draw(target_);
        }
        target_.endDraw();

        Context.window.frame.repaint();
    }
}