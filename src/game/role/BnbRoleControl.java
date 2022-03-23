package game.role;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import core.Context;
import game.bnb.BnbRoleLogic;

public class BnbRoleControl extends BnbRoleLogic {
    @Override
    protected void start() {
        receiveKeyDown = true;
    }

    @Override
    protected void update(float dt) {
        if (Context.keyboard.isKeyPressed(KeyEvent.VK_W)) {
            getParent().moveUp();
        }
        if (Context.keyboard.isKeyPressed(KeyEvent.VK_S)) {
            getParent().moveDown();
        }
        if (Context.keyboard.isKeyPressed(KeyEvent.VK_A)) {
            getParent().moveLeft();
        }
        if (Context.keyboard.isKeyPressed(KeyEvent.VK_D)) {
            getParent().moveRight();
        }
    }

    @Override
    protected void onKeyDown(HashSet<Integer> keys) {
        if (keys.contains(KeyEvent.VK_SPACE)) {
            getParent().putBomb();
        } else if (keys.contains(KeyEvent.VK_1)) {
            getParent().useItem(0);
        } else if (keys.contains(KeyEvent.VK_2)) {
            getParent().useItem(1);
        } else if (keys.contains(KeyEvent.VK_3)) {
            getParent().useItem(2);
        } else if (keys.contains(KeyEvent.VK_4)) {
            getParent().useItem(3);
        }
    }

    @Override
    protected void onDied() {
        // TODO
    }
}