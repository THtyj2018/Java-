package core;

import java.awt.Color;

import core.graphics.Renderer;
import core.graphics.Window;
import core.input.Keyboard;
import core.scene.SceneManager;

public final class Context {
    public static final Window window = new Window();
    public static final Renderer renderer = new Renderer();
    public static final Keyboard keyboard = new Keyboard();
    public static final SceneManager sceneMgr = new SceneManager();
    public static final Game game = new Game();
    
    public static final Params params = new Params();

    public static class Params {
        public int width = 640;
        public int height = 480;
        public int xpos = 320;
        public int ypos = 240;
        public float fpslimit = 60.0f;
        public String title = "Game";
        public Color clearColor = Color.WHITE;
        public boolean opengl = true;
    }
}