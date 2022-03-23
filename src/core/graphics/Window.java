package core.graphics;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import core.Context;

public final class Window extends RenderTarget {
    JFrame frame;

    public Window() {
        super();
    }

    public void create() {
        frame = new JFrame() {
            private static final long serialVersionUID = 8182529848469957665L;
            
            @Override
            public void paint(Graphics g) {
                if (Context.renderer != null && Context.renderer.getTarget() != null) {
                    g.drawImage(Context.renderer.getTarget().img, getInsets().left, getInsets().top, null);
                } else if (Context.params.clearColor != null) {
                    g.setColor(Context.params.clearColor);
                    g.fillRect(getInsets().left, getInsets().top, Context.params.width, Context.params.height);
                }
            }
        };
        frame.setLocation(Context.params.xpos, Context.params.ypos);
        frame.setTitle(Context.params.title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Context.game.stop();
            }
        });
        frame.addKeyListener(Context.keyboard);
        frame.addWindowFocusListener(Context.keyboard);
        frame.setSize(Context.params.width, Context.params.height);
        frame.setResizable(false);
        frame.setVisible(true);

        super.create(Context.params.width, Context.params.height);
    }

    public void close() {
        frame.dispose();
    }

    public boolean isFocused() {
        return frame.isFocused();
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public String getTitle() {
        return frame.getTitle();
    }
}