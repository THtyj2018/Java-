package game.bnb;

import java.util.ArrayList;

import core.graphics.Drawable;
import core.math.IntVector2;
import core.math.Mathf;
import core.math.Vector2;
import core.scene.AutoUpdateable;

public final class BnbNode {
    final BnbMap bnbmap;
    public final int entityLayer;

    IntVector2 coord = IntVector2.ZERO;
    Drawable ground;
    Drawable block;
    boolean destroyable = false;
    BnbBomb bomb;
    ArrayList<BnbEffect> effects = new ArrayList<>();
    BnbItem item;
    ArrayList<BnbRole> roles = new ArrayList<>();
    ArrayList<BnbNodeLogic> logics = new ArrayList<>();
    float itemrate = 0.167f;

    BnbNode(int x, int y, BnbMap map) {
        bnbmap = map;
        coord = new IntVector2(x, y);
        entityLayer = bnbmap.calculateEntityLayer(coord);
    }

    private void updateDrawableSize(Drawable drawable) {
        Vector2 ts = bnbmap.getTileSize();
        if (drawable != null) {
            Vector2 size = drawable.getSize();
            drawable.setSize(size.mul(ts.y / size.y));
        }
    }

    void updateDrawablesSize() {
        updateDrawableSize(ground);
        updateDrawableSize(block);
        if (bomb != null) {
            updateDrawableSize(bomb.sprite);
        }
        // TODO item
        for (BnbRole role : roles) {
            updateDrawableSize(role.sprite);
        }
    }

    ArrayList<Drawable> getAllDrawables() {
        Vector2 pos = bnbmap.calculatePosition(coord.x, coord.y);
        ArrayList<Drawable> ret = new ArrayList<Drawable>();
        if (ground != null) {
            ret.add(ground.setPosition(pos).setLayer(BnbMap.GroundLayer));
        }
        if (block != null) {
            ret.add(block.setPosition(pos).setLayer(entityLayer));
        }
        if (item != null) {
            if (item.item.sprite != null) {
                ret.add(item.item.sprite.setPosition(pos).setLayer(entityLayer));
            }
        }
        for (BnbEffect effect : effects) {
            if (effect.effect != null) {
                ret.add(effect.effect.setPosition(pos).setLayer(entityLayer));
            }
        }
        if (bomb != null && bomb.living) {
            if (bomb.sprite != null) {
                ret.add(bomb.sprite.setPosition(pos).setLayer(entityLayer));
            }
        }
        for (BnbRole role : roles) {
            if (role.sprite != null && role.effectiveShow()) {
                int layer = bnbmap.calculateRoleLayer(role.sprite.getPosition());
                if (role.dieBubble != null) {
                    ret.add(role.dieBubble.setPosition(pos).setLayer(role.sprite.layer));
                }
                ret.add(role.sprite.setPosition(pos).setLayer(layer));
            }
        }
        return ret;
    }

    ArrayList<AutoUpdateable> getAllAutoUpdateable() {
        ArrayList<AutoUpdateable> ret = new ArrayList<>();
        if (ground != null && ground instanceof AutoUpdateable) {
            ret.add((AutoUpdateable) ground);
        }
        if (block != null && block instanceof AutoUpdateable) {
            ret.add((AutoUpdateable) block);
        }
        if (item != null) {
            if (item.item.sprite instanceof AutoUpdateable) {
                ret.add((AutoUpdateable) item.item.sprite);
            }
        }
        for (BnbEffect effect : effects) {
            ret.add(effect);
            if (effect != null && effect instanceof AutoUpdateable) {
                ret.add((AutoUpdateable) effect);
            }
        }
        if (bomb != null && bomb.living) {
            ret.add(bomb);
            if (bomb.sprite instanceof AutoUpdateable) {
                ret.add((AutoUpdateable) bomb.sprite);
            }
        }
        for (BnbRole role : roles) {
            ret.add(role);
            if (role.effectiveShow()) {
                if (role.sprite != null && role.sprite instanceof AutoUpdateable) {
                    ret.add((AutoUpdateable) role.sprite);
                }
            }
        }
        return ret;
    }

    public BnbMap getMap() {
        return bnbmap;
    }

    public IntVector2 getCoord() {
        return coord;
    }

    public ArrayList<BnbRole> getRoles() {
        return new ArrayList<>(roles);
    }

    public boolean addLogic(BnbNodeLogic logic) {
        if (logic == null || logics.contains(logic)) {
            return false;
        }
        logics.add(logic);
        if (logic.getParent() != null) {
            logic.getParent().removeLogic(logic);
        }
        bnbmap.logicAdd(logic);
        logic.setParent(this);
        return true;
    }

    public void removeLogic(BnbNodeLogic logic) {
        if (logics.remove(logic)) {
            bnbmap.logicRemove(logic);
            logic.setParent(null);
        }
    }

    public boolean isBody() {
        if (block != null || bomb != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tryDestroyBody() {
        if (destroyable) {
            block = null;
            destroyable = false;
            generateItem();
            return true;
        }
        return false;
    }

    private void generateItem() {
        if (Mathf.random() < itemrate) {
            new BnbItem(coord, BnbItemInfo.getRandomItemType(), bnbmap);
        }
    }

    public void setItemDropRate(float rate) {
        itemrate = Mathf.clamp(rate, 0, 1);
    }

    public float getItemDropRate() {
        return itemrate;
    }

    public void setGround(Drawable ground) {
        this.ground = ground;
    }

    public Drawable getGround() {
        return ground;
    }

    public void setBlock(Drawable block, boolean destroyable) {
        this.block = block;
        this.destroyable = destroyable && (this.block != null);
    }

    public Drawable getBlock() {
        return block;
    }
}