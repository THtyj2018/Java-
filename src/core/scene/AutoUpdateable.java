package core.scene;

/**
 * If a component is auto updateable, the scene will call its {@code autoUpdate(dt)} method
 * every frame.
 */
public interface AutoUpdateable {
    public boolean autoUpdateEnabled();

    public void autoUpdate(float dt);
}