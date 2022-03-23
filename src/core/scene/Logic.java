package core.scene;

import java.util.HashSet;

/**
 * Control logic class.
 * @param <T> T is a parent type.
 */
public abstract class Logic <T> {
    T parent;

    public boolean enableUpdate = true;
    public boolean enablePostUpdate  = false;
    public boolean receiveKeyDown = false;
    public boolean receiveKeyUp = false;

    private boolean delayedStartCalled_ = false;

    public Logic() {
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        if (parent == null) {
            stop();
        } else {
            this.parent = parent;
            start();
        }
    }

    /**
     * Called when node set.
     */
    protected void start() {}

    /**
     * Called before first update.
     */
    protected void delayedStart() {}

    /**
     * Called every frame.
     */
    protected void update(float dt) {}

    /**
     * Called every frame after all object updated.
     */
    protected void postUpdate(float dt) {}

    /**
     * Called when remove from node.
     */
    protected void stop() {}

    /**
     * Called if some key pressed at last frame.
     */
    protected void onKeyDown(HashSet<Integer> keys) {
    }

    /**
     * Called if some key released at last frame.
     */
    protected void onKeyUp(HashSet<Integer> keys) {
    }

    void handleUpdate(float dt) {
        if (!delayedStartCalled_) {
            delayedStart();
            delayedStartCalled_ = true;
        }
        update(dt);
    }

    void handlePostUpdate(float dt) {
        if (!delayedStartCalled_) {
            delayedStart();
            delayedStartCalled_ = true;
        }
        postUpdate(dt);
    }
}