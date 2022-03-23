package game;

import java.awt.Color;

import core.Context;

public class GameMain {
    public static void main(String[] args) {
        Context.params.clearColor = new Color(0xBBFFFF);
        Context.params.fpslimit = 60;
        Context.params.width = 960;
        Context.params.height = 704;
        Context.params.xpos = 200;
        Context.params.ypos = 100;
        Context.params.title = "≈›≈›Ã√";
        
        Context.game.initialise();
        Context.sceneMgr.setNextScene(new LoadScene());
        Context.game.run();
    }
}