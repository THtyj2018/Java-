package core.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.HashSet;

public final class Keyboard implements KeyListener, WindowFocusListener {
    private HashSet<Integer> pressedKeys_ = new HashSet<>();
    private HashSet<Integer> downKeys_ = new HashSet<>();
    private HashSet<Integer> upKeys_ = new HashSet<>();
    private StringBuffer typedString_ = new StringBuffer();

    @Override
    public void keyTyped(KeyEvent e) {
        typedString_.append(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (!pressedKeys_.contains(keycode)) {
            downKeys_.add(keycode);
        }
        pressedKeys_.add(keycode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys_.remove(e.getKeyCode());
        upKeys_.add(e.getKeyCode());
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        downKeys_.clear();
        pressedKeys_.clear();
    }

    public boolean isKeyPressed(int keycode) {
        return pressedKeys_.contains(keycode);
    }

    public HashSet<Integer> pollKeyDowns() {
        HashSet<Integer> ret = downKeys_;
        downKeys_ = new HashSet<>();
        return ret;
    }

    public HashSet<Integer> pollKeyUps() {
        HashSet<Integer> ret = upKeys_;
        upKeys_ = new HashSet<>();
        return ret;
    }
}