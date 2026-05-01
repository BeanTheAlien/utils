package utils.canvas;

public interface Renderable {
    /**
     * Renders a {@code Renderable} object.
     * @param brush The {@code Graphics} object, as a {@code Brush}.
     */
    public void render(Brush brush);
    /**
     * The action to be ran, on every new tick.
     */
    public default void tick() {}
    /**
     * The action to be ran, before the window is terminated.
     */
    public default void kill() {}
}