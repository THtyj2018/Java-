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

public class MainMenu extends Scene {
    @Override
    protected void start() {
        UILayer ui = new UILayer(this);

        int halfW = Context.window.getWidth() / 2;
        int halfH = Context.window.getHeight() / 2;
        
        Text titleText = new Text().setText("≈› ≈› Ã√").setBold(true).setFontName("¡• È").setFontSize(64)
                .setUseCenter(true);
        UIElement titleElem = getSceneElement(UILayer.class).createUIElement();
        titleElem.setText(titleText);
        titleElem.setPosition(new IntVector2(halfW, halfH / 2));

        Text startText = new Text().setText("Start").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(36);
        UIElement startlabel = ui.createUIElement();
        startlabel.setText(startText);
        startlabel.setPosition(new IntVector2(halfW, halfH));

        Text aboutText = new Text().setText("About").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(24);
        UIElement aboutlabel = ui.createUIElement();
        aboutlabel.setText(aboutText);
        aboutlabel.setPosition(new IntVector2(halfW, halfH + 60));

        Text exitText = new Text().setText("Exit").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(24);
        UIElement exitlabel = ui.createUIElement();
        exitlabel.setText(exitText);
        exitlabel.setPosition(new IntVector2(halfW, halfH + 120));

        Text[] texts = new Text[3];
        texts[0] = startText;
        texts[1] = aboutText;
        texts[2] = exitText;

        addSceneLogic(new Logic<Scene>() {
            int curtextid = 0;

            @Override
            protected void start() {
                receiveKeyDown = true;
            }

            @Override
            protected void onKeyDown(HashSet<Integer> keys) {
                if (keys.contains(KeyEvent.VK_S) || keys.contains(KeyEvent.VK_DOWN)) {
                    texts[curtextid].setFontSize(24);
                    curtextid = (curtextid + 1) % texts.length;
                    texts[curtextid].setFontSize(36);
                }
                if (keys.contains(KeyEvent.VK_W) || keys.contains(KeyEvent.VK_UP)) {
                    texts[curtextid].setFontSize(24);
                    curtextid = (curtextid + texts.length - 1) % texts.length;
                    texts[curtextid].setFontSize(36);
                }
                if (keys.contains(KeyEvent.VK_ESCAPE)) {
                    texts[curtextid].setFontSize(24);
                    curtextid = texts.length - 1;
                    texts[curtextid].setFontSize(36);
                }
                if (keys.contains(KeyEvent.VK_ENTER)) {
                    switch (curtextid) {
                        case 0:
                            Context.sceneMgr.setNextScene(new SelectPage());
                            break;
                        case 1:
                            Context.sceneMgr.setNextScene(new AboutPage());
                            break;
                        case 2:
                            Context.game.stop();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}