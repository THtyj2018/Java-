package game.levels;

import core.Context;
import core.graphics.Sprite;
import core.math.Mathf;
import core.math.Vector2;
import core.scene.Logic;
import core.scene.Scene;
import game.Resources;
import game.bnb.BnbNode;
import game.role.BnbRoleControl;

public class ComplexLevel extends LevelBase {
    @Override
    protected void start() {
        initBnbMap(23, 17);

        Sprite[] sprites = new Sprite[] { new Sprite().setTexture(Resources.HouseBlue),
                new Sprite().setTexture(Resources.HouseGreen), new Sprite().setTexture(Resources.VendingBlue),
                new Sprite().setTexture(Resources.VendingRed), new Sprite().setTexture(Resources.VendingYellow), null};

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y < 13; y++) {
                Sprite sprite = sprites[Mathf.randInt() % sprites.length];
                if (sprite != null) {
                    bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                }
            }
        }
        for (int x = 19; x < 23; x++) {
            for (int y = 3; y < 13; y++) {
                Sprite sprite = sprites[Mathf.randInt() % sprites.length];
                if (sprite != null) {
                    bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                }
            }
        }
        for (int x = 3; x < 19; x++) {
            for (int y = 0; y < 4; y++) {
                Sprite sprite = sprites[Mathf.randInt() % sprites.length];
                if (sprite != null) {
                    bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                }
            }
        }
        for (int x = 3; x < 19; x++) {
            for (int y = 13; y < 17; y++) {
                Sprite sprite = sprites[Mathf.randInt() % sprites.length];
                if (sprite != null) {
                    bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                }
            }
        }

        bmap.getNode(1, 1).setBlock(new Sprite().setTexture(Resources.BlockRed), false);
        bmap.getNode(21, 1).setBlock(new Sprite().setTexture(Resources.BlockRed), false);
        bmap.getNode(21, 15).setBlock(new Sprite().setTexture(Resources.BlockRed), false);
        bmap.getNode(1, 15).setBlock(new Sprite().setTexture(Resources.BlockRed), false);

        bmap.getNode(11, 8).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(10, 7).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(10, 9).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(12, 7).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(12, 9).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(9, 6).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(9, 10).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(13, 6).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        bmap.getNode(13, 10).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);

        Sprite[] plants = new Sprite[] { new Sprite().setTexture(Resources.Tree),
                new Sprite().setTexture(Resources.Cactus), null, new Sprite().setTexture(Resources.Cactus), null };

        for (int x = 4; x < 19; x++) {
            for (int y = 4; y < 13; y++) {
                BnbNode node = bmap.getNode(x, y);
                if (node.getBlock() == null) {
                    Sprite sprite = plants[Mathf.randInt() % plants.length];
                    if (sprite != null) {
                        bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                    }
                }
            }
        }

        player = role0;
        player.addLogic(new BnbRoleControl());
        bmap.updateDrawablesSize();
        bmap.setPosition(new Vector2(-2, 1));

        initUI();

        addEscapeQuit();

        addSceneLogic(new Logic<Scene>() {
            float timer = 0;
            @Override
            protected void update(float dt) {
                timer += dt;
                if (timer > 2.0f) {
                    timer -= 2.0f;
                    System.out.println("Current fps : " + Context.game.getRealFps());
                }
            }
        });
    }
}