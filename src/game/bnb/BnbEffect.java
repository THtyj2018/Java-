package game.bnb;

import core.graphics.Drawable;
import core.math.IntVector2;
import core.scene.AutoUpdateable;

public final class BnbEffect implements AutoUpdateable {
    final BnbMap bnbmap;
    final Drawable effect;
    final IntVector2 coord;

    private float remainTime;

    BnbEffect(Drawable effect, int x, int y, float time, BnbMap map) {
        bnbmap = map;
        this.effect = effect.getCopy();
        this.coord = new IntVector2(x, y);
        bnbmap.getNode(coord).effects.add(this);
        this.remainTime = time;
    }

    @Override
    public boolean autoUpdateEnabled() {
        return true;
    }

    @Override
    public void autoUpdate(float dt) {
        remainTime -= dt;
        if (remainTime < 0) {
            bnbmap.getNode(coord).effects.remove(this);
        }
    }
}