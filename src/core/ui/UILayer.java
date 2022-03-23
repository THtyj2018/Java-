package core.ui;

import java.util.ArrayList;

import core.graphics.Drawable;
import core.scene.AutoUpdateable;
import core.scene.Logic;
import core.scene.Scene;
import core.scene.SceneElement;

public class UILayer extends SceneElement {
    public static final int MinLayer = (1 << 24) + 1;

    Scene scene;
    private ArrayList<UIElement> elements_ = new ArrayList<>();
    private ArrayList<Logic<UIElement>> logics_ = new ArrayList<>();

    public UILayer(Scene scene) {
        super(scene);
    }

    public Scene getScene() {
        return scene;
    }

    public UIElement createUIElement() {
        UIElement element = new UIElement(this);
        elements_.add(element);
        return element;
    }

    public void removeUIElement(UIElement element) {
        elements_.remove(element);
    }

    @Override
    protected ArrayList<Drawable> getAllDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.ensureCapacity(elements_.size() * 2);
        for (UIElement element : elements_) {
            if (element.shape != null) {
                element.shape.layer = MinLayer;
                drawables.add(element.shape);
            }
            if (element.img != null) {
                element.img.layer = MinLayer + 1;
                drawables.add(element.img);
            }
            if (element.text != null) {
                element.text.layer = MinLayer + 2;
                drawables.add(element.text);
            }
        }
        return drawables;
    }

    @Override
    protected ArrayList<Logic<?>> getAllLogics() {
        ArrayList<Logic<?>> ret = new ArrayList<>();
        ret.ensureCapacity(logics_.size());
        for (Logic<UIElement> elem : logics_) {
            ret.add(elem);
        }
        return ret;
    }

    void logicAdd(Logic<UIElement> logic) {
        logics_.add(logic);
    }

    void logicRemove(Logic<UIElement> logic) {
        logics_.remove(logic);
    }

    @Override
    protected ArrayList<AutoUpdateable> getAllAutoUpdateable() {
        ArrayList<AutoUpdateable> autos = new ArrayList<>();
        autos.ensureCapacity(elements_.size());
        for (UIElement elem : elements_) {
            if (elem.img != null) {
                if (elem.img instanceof AutoUpdateable) {
                    autos.add((AutoUpdateable) elem.img);
                }
            }
        }
        return autos;
    }
}