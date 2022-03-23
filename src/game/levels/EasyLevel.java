package game.levels;

import core.graphics.Sprite;
import core.math.Vector2;
import game.Resources;
import game.role.BnbRoleControl;

public class EasyLevel extends LevelBase {
    @Override
    protected void start() {
        initBnbMap(15, 13);

        setBlocks(new Sprite().setTexture(Resources.BlockRed), 6, 5, 3, 3, false);
        bmap.getNode(7, 6).setBlock(new Sprite().setTexture(Resources.BlockYellow), false);
        setBlocks(new Sprite().setTexture(Resources.HouseBlue), 0, 2, 15, 1, true);
        setBlocks(new Sprite().setTexture(Resources.HouseGreen), 0, 10, 15, 1, true);
        setBlocks(new Sprite().setTexture(Resources.VendingBlue), 2, 0, 2, 13, true);
        setBlocks(new Sprite().setTexture(Resources.VendingYellow), 11, 0, 2, 13, true);
        setBlocks(new Sprite().setTexture(Resources.VendingRed), 4, 0, 7, 1, true);
        setBlocks(new Sprite().setTexture(Resources.VendingRed), 4, 12, 7, 1, true);
        
        player = role0;
        player.addLogic(new BnbRoleControl());
        bmap.updateDrawablesSize();
        bmap.setPosition(new Vector2(-4, 2));

        initUI();

        addEscapeQuit();
    }
}