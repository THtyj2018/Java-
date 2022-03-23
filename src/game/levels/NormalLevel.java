package game.levels;

import core.graphics.Sprite;
import core.math.Mathf;
import core.math.Vector2;
import game.Resources;
import game.role.BnbRoleControl;

public class NormalLevel extends LevelBase {
    @Override
    protected void start() {
        initBnbMap(19, 15);

        setBlocks(new Sprite().setTexture(Resources.Cactus), 1, 1, 2, 2, true);
        setBlocks(new Sprite().setTexture(Resources.Cactus), 16, 1, 2, 2, true);
        setBlocks(new Sprite().setTexture(Resources.Cactus), 1, 12, 2, 2, true);
        setBlocks(new Sprite().setTexture(Resources.Cactus), 16, 12, 2, 2, true);

        bmap.getNode(2, 0).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(0, 2).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(16, 0).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(18, 2).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(16, 14).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(18, 12).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(2, 14).setBlock(new Sprite().setTexture(Resources.Tree), true);
        bmap.getNode(0, 12).setBlock(new Sprite().setTexture(Resources.Tree), true);

        setBlocks(new Sprite().setTexture(Resources.Tree), 2, 7, 1, 3, true);
        setBlocks(new Sprite().setTexture(Resources.Tree), 16, 7, 1, 3, true);
        setBlocks(new Sprite().setTexture(Resources.Tree), 8, 1, 3, 2, true);
        setBlocks(new Sprite().setTexture(Resources.Tree), 8, 12, 3, 2, true);

        setBlocks(new Sprite().setTexture(Resources.BlockRed), 0, 3, 2, 9, false);
        setBlocks(new Sprite().setTexture(Resources.BlockRed), 17, 3, 2, 9, false);
        setBlocks(new Sprite().setTexture(Resources.BlockYellow), 3, 0, 13, 1, false);
        setBlocks(new Sprite().setTexture(Resources.BlockYellow), 3, 14, 13, 1, false);
        
        Sprite[] sprites = new Sprite[] {
            new Sprite().setTexture(Resources.HouseBlue),
            new Sprite().setTexture(Resources.HouseGreen),
            new Sprite().setTexture(Resources.VendingBlue),
            new Sprite().setTexture(Resources.VendingRed),
            new Sprite().setTexture(Resources.VendingYellow),
            null
        };

        for (int x = 3; x < 16; x++) {
            for (int y = 3; y < 12; y++) {
                Sprite sprite = sprites[Mathf.randInt() % sprites.length];
                if (sprite != null) {
                    bmap.getNode(x, y).setBlock(sprite.getCopy(), true);
                }
            }
        }

        setBlocks(new Sprite().setTexture(Resources.BlockYellow), 7, 7, 5, 1, false);
        setBlocks(new Sprite().setTexture(Resources.BlockYellow), 9, 5, 1, 5, false);
        
        player = role0;
        player.addLogic(new BnbRoleControl());
        bmap.updateDrawablesSize();
        bmap.setPosition(new Vector2(-3, 2));

        initUI();

        addEscapeQuit();
    }
}