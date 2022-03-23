package core.graphics;

public class Rectangle extends Shape {
    @Override
    protected void draw(RenderTarget target) {
        state.position = getPosition();
        target.fillRect(getSize(), state);
    }

    @Override
    public Drawable getCopy() {
        Rectangle rect = new Rectangle();
        rect.state.assign(state);
        rect.visible = visible;
        rect.setSize(getSize());
        return rect;
    }
}