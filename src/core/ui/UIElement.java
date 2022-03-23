package core.ui;

import core.Context;
import core.graphics.Drawable;
import core.graphics.Shape;
import core.graphics.Text;
import core.math.IntVector2;
import core.math.Vector2;
import core.scene.Logic;

public final class UIElement {
    protected UILayer uilayer;
    Shape shape;
    Drawable img;
    Text text;
    Logic<UIElement> logic;
    private IntVector2 position = IntVector2.ZERO;
    private IntVector2 size = IntVector2.ONE;

    UIElement(UILayer uilayer) {
        this.uilayer = uilayer;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setImage(Drawable img) {
        this.img = img;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Shape getShape() {
        return shape;
    }

    public Drawable getImage() {
        return img;
    }

    public Text getText() {
        return text;
    }

    public void setPosition(IntVector2 position) {
        if (position == null) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        Vector2 pos = Context.renderer.getTarget().toWorld(position);
        if (shape != null) {
            shape.setPosition(pos);
        }
        if (text != null) {
            text.setCoord(position);
        }
        if (img != null) {
            img.setPosition(pos);
        }
    }

    public void setSize(IntVector2 size) {
        if (size == null) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        Vector2 worldsize = new Vector2(size.x / 32.0f, size.y / 32.0f);
        if (shape != null) {
            shape.setSize(worldsize);
        }
        if (img != null) {
            img.setSize(worldsize);
        }
    }

    public IntVector2 getPosition() {
        return position;
    }

    public IntVector2 getSize() {
        return size;
    }

    public void setLogic(Logic<UIElement> logic) {
        if (this.logic != logic) {
            if (this.logic != null) {
                uilayer.logicRemove(this.logic);
            }
            this.logic = logic;
            if (logic != null) {
                uilayer.logicAdd(logic);
                if (logic.getParent() != null) {
                    logic.getParent().setLogic(null);
                }
                logic.setParent(this);
            }
        }
    }

    public Logic<UIElement> getLogic() {
        return logic;
    }
}