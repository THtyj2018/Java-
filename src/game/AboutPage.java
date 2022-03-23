package game;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import core.Context;
import core.graphics.Text;
import core.math.IntVector2;
import core.scene.Logic;
import core.scene.Scene;
import core.ui.UIElement;
import core.ui.UILayer;

public class AboutPage extends Scene {
    @Override
    protected void start() {
        new UILayer(this);

        int halfW = Context.window.getWidth() / 2;

        createLine("About", 36, halfW, 80);
        createLine("Author: Tu Yijie", 24, halfW, 160);
        createLine("Student ID: 2018011132", 24, halfW, 220);
        createLine("Version: 3.0", 24, halfW, 280);
        createLine("2020.6", 24, halfW, 340);

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

    void createLine(String line, int fontsize, int x, int y) {
        Text text = new Text().setText(line).setBold(true).setFontName("Times New Roman").setFontSize(fontsize)
                .setUseCenter(true);
        UIElement elem = getSceneElement(UILayer.class).createUIElement();
        elem.setText(text);
        elem.setPosition(new IntVector2(x, y));
    }
}