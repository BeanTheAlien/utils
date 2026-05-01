package utils.canvas;

public interface Renderable {
    public void render(Brush brush);
    public default void tick() {}
    public default void kill() {}
}