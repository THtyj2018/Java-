package core.scene;

import java.util.ArrayList;

import core.graphics.Drawable;

public abstract class SceneElement {
    Scene scene;

    public SceneElement(Scene scene) {
        scene.setSceneElement(getClass(), this);
    }

    public Scene getScene() {
        return scene;
    }

    protected abstract ArrayList<Drawable> getAllDrawables();

    protected abstract ArrayList<Logic<?>> getAllLogics();

    protected abstract ArrayList<AutoUpdateable> getAllAutoUpdateable();
}