package game;

import core.Context;
import core.graphics.AnimatedSprite;
import core.graphics.Sprite;
import core.graphics.Text;
import core.graphics.Texture;
import core.graphics.TiledTexture;
import core.math.IntVector2;
import core.scene.Logic;
import core.scene.Scene;
import core.ui.UIElement;
import core.ui.UILayer;
import game.bnb.BnbItemInfo;

public class LoadScene extends Scene {
    boolean loadcomplete = false;

    @Override
    protected void start() {
        UILayer ui = new UILayer(this);
        UIElement loadingText = ui.createUIElement();
        Text text = new Text().setText("loading").setBold(true).setFontSize(36).setFontName("Times New Roman")
                .setUseCenter(true);
        loadingText.setText(text);
        loadingText.setPosition(Context.window.getSize().div(2));
        loadingText.setLogic(new Logic<UIElement>() {
            int dots = 0;
            float timer = 0;

            @Override
            protected void update(float dt) {
                timer += dt;
                if (timer > 0.5f) {
                    timer -= 0.5f;
                    dots += 1;
                    if (dots == 4) {
                        dots = 0;
                    }
                    switch (dots) {
                        case 0:
                            getParent().getText().setText("loading");
                            break;
                        case 1:
                            getParent().getText().setText("loading.");
                            break;
                        case 2:
                            getParent().getText().setText("loading..");
                            break;
                        case 3:
                            getParent().getText().setText("loading...");
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        addSceneLogic(new Logic<Scene>() {
            @Override
            protected void update(float dt) {
                if (loadcomplete) {
                    Context.sceneMgr.setNextScene(new MainMenu());
                }
            }
        });

        new Thread() {
            @Override
            public void run() {
                Resources.GroundSand = new TiledTexture("res/tileset/sandground.bmp", new IntVector2(40, 40));

                Resources.BlockRed = new Texture("res/tileset/redblock.bmp");
                Resources.BlockYellow = new Texture("res/tileset/yellowblock.bmp");
                
                Resources.HouseBlue = new Texture("res/tileset/bluehouse.png");
                Resources.HouseGreen = new Texture("res/tileset/greenhouse.png");

                Resources.VendingBlue = new Texture("res/tileset/bluevending.png");
                Resources.VendingRed = new Texture("res/tileset/redvending.png");
                Resources.VendingYellow = new Texture("res/tileset/yellowvending.png");

                Resources.Tree = new Texture("res/tileset/tree.png");
                Resources.Cactus = new Texture("res/tileset/cactus.png");

                Resources.ItemBomb = new TiledTexture("res/tileset/bomb_plus.png", new IntVector2(42, 45));
                Resources.ItemPower = new TiledTexture("res/tileset/power_plus.png", new IntVector2(42, 45));
                Resources.ItemNeedle = new Texture("res/tileset/needle.png");
                AnimatedSprite itembomb = new AnimatedSprite().setFrameTextures(Resources.ItemBomb.getAllTiles(false));
                itembomb.play();
                BnbItemInfo.regisetrItemType("BombPlus", itembomb);
                AnimatedSprite itempower = new AnimatedSprite().setFrameTextures(Resources.ItemPower.getAllTiles(false));
                itempower.play();
                BnbItemInfo.regisetrItemType("PowerPlus", itempower);
                BnbItemInfo.regisetrItemType("Needle", new Sprite().setTexture(Resources.ItemNeedle));

                Resources.Bomb = new TiledTexture("res/tileset/bomb.png", new IntVector2(44, 41));

                Resources.EffectWave = new TiledTexture("res/tileset/wave.png", new IntVector2(40, 40));
                Resources.EffectBubble = new Texture("res/tileset/bubble.png");

                Resources.Reimi = new TiledTexture("res/th/reimi.png", new IntVector2(64, 64));
                Resources.Komachi = new TiledTexture("res/th/komachi.png", new IntVector2(35, 40));
                Resources.Marisa = new TiledTexture("res/th/marisa.png", new IntVector2(22, 40));
                Resources.Iku = new TiledTexture("res/th/iku.png", new IntVector2(19, 40));

                loadcomplete = true;
            }
        }.start();
    }
}