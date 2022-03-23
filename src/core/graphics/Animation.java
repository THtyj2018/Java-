package core.graphics;

import core.scene.AutoUpdateable;

public interface Animation extends AutoUpdateable {
    public void play();

    public void stop();
}