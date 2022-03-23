package core.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import core.Context;
import core.graphics.Drawable;

public class Scene {
    private HashMap<Class<? extends SceneElement>, SceneElement> elements_ = new HashMap<>();
    private ArrayList<Logic<Scene>> sceneLogics_ = new ArrayList<>();

    public boolean addSceneLogic(Logic<Scene> logic) {
        if (logic == null || sceneLogics_.contains(logic)) {
            return false;
        }
        sceneLogics_.add(logic);
        logic.setParent(this);
        return true;
    }

    public void removeSceneLogic(Logic<Scene> logic) {
        if (sceneLogics_.remove(logic)) {
            logic.setParent(null);
        }
    }

    void setSceneElement(Class<? extends SceneElement> clz, SceneElement element) {
        elements_.put(clz, element);
        element.scene = this;
    }

    @SuppressWarnings("unchecked")
    public <T extends SceneElement> T getSceneElement(Class<T> clz) {
        return (T) elements_.get(clz);
    }

    protected void start() {
    }

    void update(float dt) {
        HashSet<Integer> downs = Context.keyboard.pollKeyDowns();
        HashSet<Integer> ups = Context.keyboard.pollKeyUps();
        ArrayList<Logic<?>> logics = new ArrayList<>(sceneLogics_);
        ArrayList<AutoUpdateable> autos = new ArrayList<>();

        for (SceneElement elem : elements_.values()) {
            ArrayList<Logic<?>> templogics = elem.getAllLogics();
            ArrayList<AutoUpdateable> tempautos = elem.getAllAutoUpdateable();
            if (templogics != null) {
                logics.addAll(templogics);
            }
            if (tempautos != null) {
                autos.addAll(tempautos);
            }
        }

        for (Logic<?> logic : logics) {
            if (logic.receiveKeyDown) {
                logic.onKeyDown(downs);
            }
            if (logic.receiveKeyUp) {
                logic.onKeyUp(ups);
            }
        }

        for (Logic<?> logic : logics) {
            if (logic.enableUpdate) {
                logic.update(dt);
            }
        }

        for (AutoUpdateable auto : autos) {
            if (auto.autoUpdateEnabled()) {
                auto.autoUpdate(dt);
            }
        }

        for (Logic<?> logic : logics) {
            if (logic.enablePostUpdate) {
                logic.postUpdate(dt);
            }
        }
    }

    protected void stop() {
    }

    void beforeDestroy() {
    }

    public final ArrayList<Drawable> getAllDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        for (SceneElement elem : elements_.values()) {
            ArrayList<Drawable> temp = elem.getAllDrawables();
            if (temp != null) {
                drawables.addAll(temp);
            }
        }
        return drawables;
    }
}