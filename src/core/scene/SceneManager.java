package core.scene;

import core.Context;

public final class SceneManager {
    private Scene curScene_ = new Scene();
    private Scene nextScene_ = null;
    private boolean needChange_ = false;

    public void setNextScene(Scene nextScene) {
        if (nextScene == curScene_) {
            return;
        }
        nextScene_ = nextScene;
        needChange_ = true;
    }

    public Scene getNextScene() {
        return nextScene_;
    }

    public Scene getCurrentScene() {
        return curScene_;
    }

    public void update(float dt) {
        if (needChange_) {
            curScene_.stop();
            if (nextScene_ == null) {
                Context.game.stop();
            } else {
                nextScene_.start();
            }
            curScene_.beforeDestroy();
            curScene_ = nextScene_;
            nextScene_ = null;
            needChange_ = false;
        }
        curScene_.update(dt);
    }
}