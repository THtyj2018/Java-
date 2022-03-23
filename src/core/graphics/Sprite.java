package core.graphics;

public final class Sprite extends Drawable {
    private final RenderState state_ = new RenderState();

    public Sprite() {
    }

    @Override
    protected void draw(RenderTarget target) {
        state_.position = getPosition();
        target.fillRect(getSize(), state_);
    }

    public Sprite setTexture(Texture texture) {
        state_.texture = texture;
        return this;
    }

    public Texture getTexture() {
        return state_.texture;
    }

    @Override
    public Drawable getCopy() {
        Sprite sprite = new Sprite();
        sprite.state_.assign(state_);
        sprite.visible = visible;
        sprite.setSize(getSize());
        return sprite;
    }
}