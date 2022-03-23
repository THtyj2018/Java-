package game.levels;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import core.Context;
import core.graphics.AnimatedSprite;
import core.graphics.Animation;
import core.graphics.Drawable;
import core.graphics.Rectangle;
import core.graphics.Sprite;
import core.graphics.Text;
import core.math.IntVector2;
import core.math.Vector2;
import core.scene.Logic;
import core.scene.Scene;
import core.ui.UIElement;
import core.ui.UILayer;
import game.MainMenu;
import game.Resources;
import game.bnb.BnbItemInfo;
import game.bnb.BnbMap;
import game.bnb.BnbRole;

public class LevelBase extends Scene {
    protected BnbMap bmap;
    protected UILayer ui;

    protected BnbRole role0;
    protected BnbRole role1;
    protected BnbRole role2;
    protected BnbRole role3;
    protected BnbRole player;

    protected BnbMap initBnbMap(int width, int height) {
        bmap = new BnbMap(width, height, this);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmap.getNode(x, y).setGround(new Sprite().setTexture(Resources.GroundSand));
            }
        }

        AnimatedSprite bombsprite = new AnimatedSprite().setFrameTextures(Resources.Bomb.getAllTiles(false))
                .setPeriod(0.5f);
        bombsprite.play();
        bmap.bombsprite = bombsprite;
        bmap.dieBubble = new Sprite().setTexture(Resources.EffectBubble);
        bmap.waveUpEnd = new Sprite().setTexture(Resources.EffectWave.getTile(0, 0));
        bmap.waveUp = new Sprite().setTexture(Resources.EffectWave.getTile(0, 3));
        bmap.waveDownEnd = new Sprite().setTexture(Resources.EffectWave.getTile(1, 0));
        bmap.waveDown = new Sprite().setTexture(Resources.EffectWave.getTile(1, 3));
        bmap.waveLeftEnd = new Sprite().setTexture(Resources.EffectWave.getTile(2, 0));
        bmap.waveLeft = new Sprite().setTexture(Resources.EffectWave.getTile(2, 3));
        bmap.waveRightEnd = new Sprite().setTexture(Resources.EffectWave.getTile(3, 0));
        bmap.waveRight = new Sprite().setTexture(Resources.EffectWave.getTile(3, 3));

        generateRoles();

        return bmap;
    }

    private void initItemBar(int index) {
        UIElement item = ui.createUIElement();
        item.setShape(new Rectangle().setColor(Color.MAGENTA));
        item.setText(new Text().setText(Integer.toString(index)).setBold(true).setFontSize(18)
                .setFontName("Times New Roman").setUseCenter(true));
        item.setSize(new IntVector2(32, 32));
        item.setPosition(new IntVector2(Context.window.getWidth() / 2 - (2 - index) * 32 - 100,
                Context.window.getHeight() - 64));
        item.setLogic(new Logic<UIElement>() {
            @Override
            protected void update(float dt) {
                BnbItemInfo it = player.getItem(index - 1);
                if (it == null) {
                    getParent().setImage(null);
                } else {
                    getParent().setImage(it.getSprite());
                    if (it.getSprite() != null) {
                        getParent().setPosition(getParent().getPosition());
                    }
                }
            }
        });
    }

    private void initRoleStatusBar(int index, String name) {
        int ww = Context.window.getWidth();

        BnbRole currole = new BnbRole[]{role0, role1, role2, role3}[index];

        UIElement panel = ui.createUIElement();
        panel.setShape(new Rectangle().setColor(Color.GRAY));
        panel.setSize(new IntVector2(160, 80));
        panel.setPosition(new IntVector2(ww - 80, 60 + 100 * index));

        UIElement role = ui.createUIElement();
        role.setImage(((AnimatedSprite) (currole.getSprite())).getCopy());
        role.setSize(new IntVector2(64, 64));
        role.setPosition(new IntVector2(ww - 120, 60 + 100 * index));

        UIElement rolename = ui.createUIElement();
        rolename.setText(new Text().setBold(true).setText(name).setColor(Color.MAGENTA).setFontSize(16));
        rolename.setPosition(new IntVector2(ww - 88, 22 + 100 * index));

        UIElement bomb = ui.createUIElement();
        bomb.setText(new Text().setBold(true).setText("Bombs: ").setColor(Color.RED).setFontSize(14));
        bomb.setPosition(new IntVector2(ww - 88, 44 + 100 * index));
        bomb.setLogic(new Logic<UIElement>() {
            @Override
            protected void update(float dt) {
                getParent().getText().setText("Bombs: " + Integer.toString(currole.getBombLimit()));
            }
        });

        UIElement power = ui.createUIElement();
        power.setText(new Text().setBold(true).setText("Power: ").setColor(Color.RED).setFontSize(14));
        power.setPosition(new IntVector2(ww - 88, 62 + 100 * index));
        power.setLogic(new Logic<UIElement>() {
            @Override
            protected void update(float dt) {
                getParent().getText().setText("Power: " + Integer.toString(currole.getBombPower()));
            }
        });

        UIElement health = ui.createUIElement();
        health.setText(new Text().setBold(true).setText("HP: ").setColor(Color.RED).setFontSize(14));
        health.setPosition(new IntVector2(ww - 88, 80 + 100 * index));
        health.setLogic(new Logic<UIElement>() {
            @Override
            protected void update(float dt) {
                getParent().getText().setText("HP: " + Integer.toString(currole.getLife()));
            }
        });
    }

    protected UILayer initUI() {
        ui = new UILayer(this);

        initItemBar(1);
        initItemBar(2);
        initItemBar(3);
        initItemBar(4);

        initRoleStatusBar(0, "Reimi");
        initRoleStatusBar(1, "Komachi");
        initRoleStatusBar(2, "Marisa");
        initRoleStatusBar(3, "Iku");

        return ui;
    }

    protected void addEscapeQuit() {
        addSceneLogic(new Logic<Scene>() {
            @Override
            protected void start() {
                receiveKeyDown = true;
            }

            @Override
            protected void onKeyDown(HashSet<Integer> keys) {
                if (keys.contains(KeyEvent.VK_ESCAPE)) {
                    Context.sceneMgr.setNextScene(new MainMenu());
                }
            }
        });
    }

    protected void generateRoles() {
        role0 = initRole(0, 0);
        role0.setSprite(new AnimatedSprite().setFrameTextures(Resources.Reimi.getAllTiles(false)));
        ((Animation) (role0.getSprite())).play();

        role1 = initRole(bmap.getWidth() - 1, 0);
        role1.setSprite(new AnimatedSprite().setFrameTextures(Resources.Komachi.getAllTiles(false))
                .setSize(new Vector2(0.7f, 0.8f)));
        ((Animation) (role1.getSprite())).play();

        role2 = initRole(bmap.getWidth() - 1, bmap.getHeight() - 1);
        role2.setSprite(new AnimatedSprite().setFrameTextures(Resources.Marisa.getAllTiles(false))
                .setSize(new Vector2(0.55f, 1.0f)));
        ((Animation) (role2.getSprite())).play();

        role3 = initRole(0, bmap.getHeight() - 1);
        role3.setSprite(new AnimatedSprite().setFrameTextures(Resources.Iku.getAllTiles(false))
                .setSize(new Vector2(0.19f, 0.4f)));
        ((Animation) (role3.getSprite())).play();
    }

    protected BnbRole initRole(int x, int y) {
        BnbRole role = bmap.createRole();
        role.setCoord(x, y);
        role.setBornCoord(role.getCoord());
        return role;
    }

    protected void setBlocks(Drawable block, int x, int y, int width, int height, boolean destroyable) {
        for (int xx = x; xx < x + width; xx++) {
            for (int yy = y; yy < y + height; yy++) {
                bmap.getNode(xx, yy).setBlock(block.getCopy(), destroyable);
            }
        }
    }
}