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
import game.levels.ComplexLevel;
import game.levels.EasyLevel;
import game.levels.NormalLevel;

public class SelectPage extends Scene {
    @Override
    protected void start() {
        UILayer ui = new UILayer(this);

        int halfW = Context.window.getWidth() / 2;
        int halfH = Context.window.getHeight() / 2;

        Text selectText = new Text().setText("Level Select").setBold(true).setFontName("Consolas").setFontSize(48)
                .setUseCenter(true);
        UIElement selectElem = getSceneElement(UILayer.class).createUIElement();
        selectElem.setText(selectText);
        selectElem.setPosition(new IntVector2(halfW, 100));


        Text easyText = new Text().setText("Easy").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(36);
        UIElement easyOpt = ui.createUIElement();
        easyOpt.setText(easyText);
        easyOpt.setPosition(new IntVector2(halfW, halfH));

        Text normalText = new Text().setText("Normal").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(24);
        UIElement normalOpt = ui.createUIElement();
        normalOpt.setText(normalText);
        normalOpt.setPosition(new IntVector2(halfW, halfH + 60));

        Text complexText = new Text().setText("Complex").setBold(true).setItalic(true).setFontName("Times New Roman")
                .setUseCenter(true).setFontSize(24);
        UIElement complexOpt = ui.createUIElement();
        complexOpt.setText(complexText);
        complexOpt.setPosition(new IntVector2(halfW, halfH + 120));

        Text[] texts = new Text[3];
        texts[0] = easyText;
        texts[1] = normalText;
        texts[2] = complexText;

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
                    Context.sceneMgr.setNextScene(new MainMenu());
                }
                if (keys.contains(KeyEvent.VK_ENTER)) {
                    switch (curtextid) {
                        case 0:
                            Context.sceneMgr.setNextScene(new EasyLevel());
                            break;
                        case 1:
                            Context.sceneMgr.setNextScene(new NormalLevel());
                            break;
                        case 2:
                            Context.sceneMgr.setNextScene(new ComplexLevel());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}