package game.bnb;

import java.util.ArrayList;

import core.graphics.Drawable;
import core.math.IntVector2;
import core.math.Vector2;
import core.scene.AutoUpdateable;
import core.scene.Logic;
import core.scene.Scene;
import core.scene.SceneElement;

public final class BnbMap extends SceneElement {
    Vector2 position = Vector2.ZERO;
    Vector2 tilesize = Vector2.ONE;
    private boolean tilesizedirty_ = true;

    private BnbNode[][] nodes_;

    private ArrayList<Logic<?>> allLogics_ = new ArrayList<>();

    public static final int GroundLayer = 0;
    public static final int MaxLayer = (1 << 16);

    public BnbMap(int width, int height, Scene scene) {
        super(scene);
        nodes_ = new BnbNode[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                nodes_[j][i] = new BnbNode(i, j, this);
            }
        }
    }

    public BnbNode getNode(int x, int y) {
        return nodes_[y][x];
    }

    public BnbNode getNode(IntVector2 grid) {
        return nodes_[grid.y][grid.x];
    }

    public int getWidth() {
        return nodes_[0].length;
    }

    public int getHeight() {
        return nodes_.length;
    }

    public BnbRole createRole() {
        return new BnbRole(this);
    }

    void roleCoordSet(BnbRole role, IntVector2 oldGrid, int x, int y) {
        if (oldGrid != null) {
            getNode(oldGrid).roles.remove(role);
        }
        BnbNode node = getNode(x, y);
        if (role.properties.state == BnbRole.IdleState) {
            for (BnbRole r : node.roles) {
                r.contact();
            }
            if (node.item != null) {
                if (role.pickItem(node.item.item)) {
                    node.item = null;
                }
            }
        }
        getNode(x, y).roles.add(role);
    }

    int calculateEntityLayer(IntVector2 gridCoord) {
        return ((nodes_.length - gridCoord.y) << 16) / nodes_.length;
    }

    int calculateRoleLayer(Vector2 position) {
        float ymin = this.position.y - tilesize.y * nodes_.length / 2.0f;
        float yrate = (position.y - ymin) / tilesize.y;
        return (int) ((nodes_.length - yrate) * MaxLayer / nodes_.length);
    }

    Vector2 calculatePosition(float coordx, float coordy) {
        return new Vector2(coordx - nodes_[0].length / 2.0f, coordy - nodes_.length / 2.0f).mul(tilesize).add(position);
    }

    void logicAdd(Logic<?> logic) {
        allLogics_.add(logic);
    }

    void logicRemove(Logic<?> logic) {
        allLogics_.remove(logic);
    }

    public void setPosition(Vector2 position) {
        if (position == null) {
            throw new IllegalArgumentException("Position is nullptr!");
        }
        this.position = position;
    }

    public int getNodesCount() {
        return nodes_.length * nodes_[0].length;
    }

    public void setTileSize(Vector2 tilesize) {
        if (tilesize == null) {
            throw new IllegalArgumentException("Tile size is nullptr!");
        }
        this.tilesize = tilesize;
        tilesizedirty_ = true;
    }

    public void updateDrawablesSize() {
        if (tilesizedirty_) {
            for (BnbNode[] row : nodes_) {
                for (BnbNode node : row) {
                    node.updateDrawablesSize();
                }
            }
            tilesizedirty_ = false;
        }
    }

    public Vector2 getTileSize() {
        return tilesize;
    }

    public boolean reachable(int x, int y) {
        if (x >= 0 && x < nodes_[0].length && y >= 0 && y < nodes_.length) {
            return !getNode(x, y).isBody();
        }
        return false;
    }

    @Override
    protected ArrayList<Drawable> getAllDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.ensureCapacity(getNodesCount() * 2);
        for (BnbNode[] row : nodes_) {
            for (BnbNode node : row) {
                drawables.addAll(node.getAllDrawables());
            }
        }
        return drawables;
    }

    @Override
    protected ArrayList<Logic<?>> getAllLogics() {
        return allLogics_;
    }

    @Override
    protected ArrayList<AutoUpdateable> getAllAutoUpdateable() {
        ArrayList<AutoUpdateable> autos = new ArrayList<>();
        autos.ensureCapacity(getNodesCount());
        for (BnbNode[] row : nodes_) {
            for (BnbNode node : row) {
                autos.addAll(node.getAllAutoUpdateable());
            }
        }
        return autos;
    }

    public Drawable bombsprite;

    public Drawable dieBubble;

    public Drawable waveLeft;
    public Drawable waveLeftEnd;
    public Drawable waveUp;
    public Drawable waveUpEnd;
    public Drawable waveRight;
    public Drawable waveRightEnd;
    public Drawable waveDown;
    public Drawable waveDownEnd;
}