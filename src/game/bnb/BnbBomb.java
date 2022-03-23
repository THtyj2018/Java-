package game.bnb;

import core.graphics.Drawable;
import core.math.IntVector2;
import core.scene.AutoUpdateable;

public final class BnbBomb implements AutoUpdateable {
    public final BnbRole owner;
    public final BnbMap bnbmap;
    public final int power;
    final Drawable sprite;
    final IntVector2 coord;

    public static final float BombTime = 3.0f;

    float remainTime = BombTime;
    boolean living = true;

    private BnbBomb(IntVector2 coord, Drawable sprite, int power, BnbRole owner) {
        this.owner = owner;
        this.bnbmap = owner.bnbmap;
        this.coord = coord;
        owner.bnbmap.getNode(coord).bomb = this;
        this.sprite = sprite;
        this.power = power;
    }

    static BnbBomb instantiate(IntVector2 coord, Drawable sprite, int power, BnbRole owner) {
        if (owner.bnbmap.reachable(coord.x, coord.y) && sprite != null) {
            return new BnbBomb(coord, sprite.getCopy(), power, owner);
        }
        return null;
    }

    public void immExplosion() {
        remainTime = 0;
        explosion();
    }

    public boolean isLiving() {
        return living;
    }

    boolean explodeInNode(int x, int y) {
        BnbNode node;
        try {
            node = bnbmap.getNode(x, y);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        for (BnbRole role : node.roles) {
            role.hit();
        }
        node.item = null;
        if (node.bomb != null) {
            node.bomb.immExplosion();
        }
        if (node.block != null) {
            node.tryDestroyBody();
            return false;
        }
        return true;
    }

    void explosion() {
        if (!living) {
            return;
        }
        living = false;
        bnbmap.getNode(coord).bomb = null;
        owner.properties.bombCount--;

        // Explosion
        for (int x = coord.x; x >= coord.x - power; x--) {
            if (!explodeInNode(x, coord.y)) {
                break;
            } else {
                new BnbEffect((x == coord.x - power) ? bnbmap.waveLeftEnd : bnbmap.waveLeft, x, coord.y, 0.5f, bnbmap);
            }
        }
        for (int x = coord.x + 1; x <= coord.x + power; x++) {
            if (!explodeInNode(x, coord.y)) {
                break;
            } else {
                new BnbEffect((x == coord.x + power) ? bnbmap.waveRightEnd : bnbmap.waveRight, x, coord.y, 0.5f,
                        bnbmap);
            }
        }
        for (int y = coord.y - 1; y >= coord.y - power; y--) {
            if (!explodeInNode(coord.x, y)) {
                break;
            } else {
                new BnbEffect((y == coord.y - power) ? bnbmap.waveDownEnd : bnbmap.waveDown, coord.x, y, 0.5f, bnbmap);
            }
        }
        for (int y = coord.y + 1; y <= coord.y + power; y++) {
            if (!explodeInNode(coord.x, y)) {
                break;
            } else {
                new BnbEffect((y == coord.y + power) ? bnbmap.waveUpEnd : bnbmap.waveUp, coord.x, y, 0.5f, bnbmap);
            }
        }
    }

    @Override
    public boolean autoUpdateEnabled() {
        return living;
    }

    @Override
    public void autoUpdate(float dt) {
        remainTime -= dt;
        if (remainTime <= 0) {
            explosion();
        }
    }
}