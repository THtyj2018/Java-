package game.bnb;

import java.util.ArrayList;

import core.graphics.Drawable;
import core.math.IntVector2;
import core.math.Mathf;
import core.math.Vector2;
import core.scene.AutoUpdateable;

public final class BnbRole implements AutoUpdateable {
    final BnbMap bnbmap;
    IntVector2 coord;
    Vector2 fcoord = Vector2.ZERO;
    float speed = 2.5f;
    Vector2 vecSpeed = Vector2.ZERO;
    float remainTime = 0.0f;
    ArrayList<BnbRoleLogic> logics = new ArrayList<>();
    Drawable sprite;

    BnbRole(BnbMap map) {
        bnbmap = map;
    }

    private void setCoordImpl(int x, int y) {
        bnbmap.roleCoordSet(this, coord, x, y);
        coord = new IntVector2(x, y);
        fcoord = new Vector2(x, y);
    }

    public boolean setCoord(int x, int y) {
        if (bnbmap.reachable(x, y)) {
            setCoordImpl(x, y);
            if (isMoving()) {
                remainTime = 0;
                vecSpeed = Vector2.ZERO;
            }
            return true;
        }
        return false;
    }

    public void setSpeed(float speed) {
        if (speed <= 0.0f) {
            throw new IllegalArgumentException("Speed must be positive");
        }
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isMoving() {
        return (remainTime > 0);
    }

    public boolean moveRight() {
        if (!isMoving() && properties.state == IdleState) {
            IntVector2 newcoord = new IntVector2(coord.x + 1, coord.y);
            if (bnbmap.reachable(newcoord.x, newcoord.y)) {
                setCoordImpl(newcoord.x, newcoord.y);
                vecSpeed = new Vector2(speed, 0);
                remainTime = 1 / speed;
                return true;
            }
        }
        return false;
    }

    public boolean moveLeft() {
        if (!isMoving() && properties.state == IdleState) {
            IntVector2 newcoord = new IntVector2(coord.x - 1, coord.y);
            if (bnbmap.reachable(newcoord.x, newcoord.y)) {
                setCoordImpl(newcoord.x, newcoord.y);
                vecSpeed = new Vector2(-speed, 0);
                remainTime = 1 / speed;
                return true;
            }
        }
        return false;
    }

    public boolean moveUp() {
        if (!isMoving() && properties.state == IdleState) {
            IntVector2 newcoord = new IntVector2(coord.x, coord.y + 1);
            if (bnbmap.reachable(newcoord.x, newcoord.y)) {
                setCoordImpl(newcoord.x, newcoord.y);
                vecSpeed = new Vector2(0, speed);
                remainTime = 1 / speed;
                return true;
            }
        }
        return false;
    }

    public boolean moveDown() {
        if (!isMoving() && properties.state == IdleState) {
            IntVector2 newcoord = new IntVector2(coord.x, coord.y - 1);
            if (bnbmap.reachable(newcoord.x, newcoord.y)) {
                setCoordImpl(newcoord.x, newcoord.y);
                vecSpeed = new Vector2(0, -speed);
                remainTime = 1 / speed;
                return true;
            }
        }
        return false;
    }

    public IntVector2 getCoord() {
        return coord;
    }

    public boolean addLogic(BnbRoleLogic logic) {
        if (logic != null && !logics.contains(logic)) {
            if (logic.getParent() != null) {
                logic.getParent().removeLogic(logic);
            }
            logic.setParent(this);
            bnbmap.logicAdd(logic);
            return true;
        }
        return false;
    }

    public void removeLogic(BnbRoleLogic logic) {
        if (logics.remove(logic)) {
            logic.setParent(null);
            bnbmap.logicRemove(logic);
        }
    }

    public void setSprite(Drawable sprite) {
        this.sprite = sprite;
    }

    public Drawable getSprite() {
        return sprite;
    }

    @Override
    public boolean autoUpdateEnabled() {
        return true;
    }

    @Override
    public void autoUpdate(float dt) {
        if (remainTime > 0.0f) {
            remainTime -= dt;
            if (remainTime > Mathf.EPSILON) {
                fcoord = fcoord.add(vecSpeed);
            } else {
                remainTime = 0;
                fcoord = new Vector2(coord.x, coord.y);
            }
        }
        processState(dt);
    }

    BnbItemInfo[] items = new BnbItemInfo[4];

    boolean pickItem(BnbItemInfo item) {
        switch (item.name) {
            case "BombPlus":
                properties.bombLimit++;
                if (properties.bombLimit > 10) {
                    properties.bombLimit = 10;
                }
                return true;
            case "PowerPlus":
                properties.bombPower++;
                if (properties.bombPower > 10) {
                    properties.bombPower = 10;
                }
                return true;
            default:
                break;
        }
        for (int i = 0; i < 4; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    public BnbItemInfo[] getItems() {
        return items.clone();
    }

    public BnbItemInfo getItem(int index) {
        return items[index];
    }

    public boolean useItem(int index) {
        if (items[index] != null) {
            switch (items[index].name) {
                case "Needle": {
                    if (properties.state != DyingState) {
                        return false;
                    }
                    properties.state = IdleState;
                    dieBubble = null;
                    items[index] = null;
                }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    static final int BornState = 0;
    static final int IdleState = 1;
    static final int DyingState = 2;
    static final int BrokenState = 3;
    static final int DiedState = 4;

    static class BnbRoleProperties {
        int bombPower = 1;
        int bombLimit = 1;
        int bombCount = 0;

        int life = 5;
        int state = BrokenState;

        float bornTimer = 0.0f;
        float invincibleTimer = 0.0f;
        float dyingTimer = 0.0f;

        boolean invincible = false;
        IntVector2 bornCoord = IntVector2.ZERO;
    }

    final BnbRoleProperties properties = new BnbRoleProperties();

    public boolean putBomb() {
        if (properties.state != IdleState) {
            return false;
        }
        if (properties.bombCount < properties.bombLimit) {
            BnbBomb bomb = BnbBomb.instantiate(coord, bnbmap.bombsprite.getCopy(), properties.bombPower, this);
            if (bomb != null) {
                properties.bombCount++;
                return true;
            }
        }
        return false;
    }

    public int getBombLimit() {
        return properties.bombLimit;
    }

    public int getBombPower() {
        return properties.bombPower;
    }

    public int getLife() {
        return properties.life;
    }

    public void setBornCoord(IntVector2 coord) {
        if (coord == null) {
            throw new IllegalArgumentException();
        }
        properties.bornCoord = coord;
    }

    public IntVector2 getBornCoord() {
        return properties.bornCoord;
    }

    private void processState(float dt) {
        switch (properties.state) {
            case BornState: {
                properties.bornTimer += dt;
                if (properties.bornTimer > 1.0f) {
                    born();
                }
            }
                break;
            case IdleState: {
                if (properties.invincible) {
                    properties.invincibleTimer += dt;
                }
                if (properties.invincibleTimer > 2.0f) {
                    properties.invincible = false;
                }
            }
                break;
            case DyingState: {
                properties.dyingTimer += dt;
                if (properties.dyingTimer > 3.0f) {
                    properties.state = BrokenState;
                    dieBubble = null;
                }
            }
                break;
            case BrokenState: {
                properties.life--;
                if (properties.life > 0) {
                    properties.state = BornState;
                    properties.bornTimer = 0;
                    properties.bombLimit = 1;
                    properties.bombPower = 1;
                    setCoord(properties.bornCoord.x, properties.bornCoord.y);
                } else {
                    properties.state = DiedState;
                    for (BnbRoleLogic logic : logics) {
                        logic.onDied();
                    }
                }
            }
                break;
            default:
                break;
        }
    }

    boolean effectiveShow() {
        return properties.state == IdleState || properties.state == DyingState;
    }

    Drawable dieBubble;

    private void born() {
        properties.state = IdleState;
        properties.invincibleTimer = 0.0f;
        properties.invincible = true;
    }

    void hit() {
        if (properties.state == IdleState) {
            properties.state = DyingState;
            properties.dyingTimer = 0;
            dieBubble = bnbmap.dieBubble.getCopy();
        }
    }

    void contact() {
        if (properties.state == DyingState) {
            properties.state = BrokenState;
            dieBubble = null;
        }
    }
}