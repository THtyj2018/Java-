package core.graphics;

import java.util.ArrayList;

public final class AnimatedSprite extends Drawable implements Animation {
    private ArrayList<Texture> texframes_ = new ArrayList<>();
    private float period_ = 1.0f;
    private int curFrameIndex_ = 0;
    private float timer_ = 0.0f;
    private boolean playing_ = false;
    private final RenderState state_ = new RenderState();

    public AnimatedSprite() {
    }

    @Override
    protected void draw(RenderTarget target) {
        state_.position = getPosition();
        target.fillRect(getSize(), state_);
    }

    @Override
    public boolean autoUpdateEnabled() {
        return playing_ && (texframes_.size() > 0);
    }

    @Override
    public void autoUpdate(float dt) {
        if (!visible) {
            return;
        }
        int count = texframes_.size();
        float framespan = period_ / count;
        timer_ += dt;
        while (timer_ >= framespan) {
            timer_ -= framespan;
            curFrameIndex_++;
            if (curFrameIndex_ == count) {
                curFrameIndex_ = 0;
            }
            state_.texture = texframes_.get(curFrameIndex_);
        }
    }

    @Override
    public void play() {
        playing_ = true;
        timer_ = 0.0f;
        state_.texture = texframes_.get(curFrameIndex_);
    }

    @Override
    public void stop() {
        playing_ = false;
    }

    /**
     * Equals {@code AnimatedSprite.setCurrentFrameIndex(0)}.
     */
    public void reset() {
        curFrameIndex_ = 0;
        timer_ = 0;
    }

    public AnimatedSprite setPeriod(float period) {
        if (period < 0.1f) {
            period = 0.1f;
        }
        period_ = period;
        return this;
    }

    public float getPeriod() {
        return period_;
    }

    public AnimatedSprite addFrame(Texture texture) {
        texframes_.add(texture);
        return this;
    }

    public Texture getFrameTexture(int index) {
        return texframes_.get(index);
    }

    public AnimatedSprite setFrameTexture(int index, Texture texture) {
        texframes_.set(index, texture);
        return this;
    }

    public AnimatedSprite setFrameTextures(ArrayList<Texture> textures) {
        texframes_ = new ArrayList<>(textures);
        return this;
    }

    public AnimatedSprite setFrameTextures(Texture[] textures) {
        texframes_.clear();
        for (Texture tex : textures) {
            texframes_.add(tex);
        }
        return this;
    }

    public AnimatedSprite setCurrentFrameIndex(int frameIndex) {
        if (frameIndex < 0 || frameIndex >= texframes_.size()) {
            throw new IndexOutOfBoundsException("Frame index out of range");
        }
        curFrameIndex_ = frameIndex;
        timer_ = 0.0f;
        return this;
    }

    public int getCurrentFrameIndex() {
        return curFrameIndex_;
    }

    @Override
    public Drawable getCopy() {
        AnimatedSprite sprite = new AnimatedSprite();
        sprite.period_ = period_;
        sprite.playing_ = playing_;
        sprite.state_.assign(state_);
        sprite.state_.texture = texframes_.get(0);
        sprite.texframes_ = new ArrayList<>(texframes_);
        sprite.visible = visible;
        sprite.setSize(getSize());
        return sprite;
    }
}