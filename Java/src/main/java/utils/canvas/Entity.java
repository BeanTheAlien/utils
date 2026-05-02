package utils.canvas;
import java.util.function.Consumer;

public class Entity implements Renderable {
    public int x;
    public int y;
    public int w;
    public int h;
    public double rot;
    public Runnable upd;
    public Consumer<Entity> collide;
    public Entity() {
        this(0, 0);
    }
    public Entity(int x, int y) {
        this(x, y, 0, 0);
    }
    public Entity(Runnable upd) {
        this(0, 0, upd);
    }
    public Entity(Consumer<Entity> collide) {
        this(0, 0, collide);
    }
    public Entity(int x, int y, int w, int h) {
        this(x, y, w, h, null, null);
    }
    public Entity(int x, int y, int w, int h, Runnable upd) {
        this(x, y, w, h, upd, null);
    }
    public Entity(int x, int y, int w, int h, Consumer<Entity> collide) {
        this(x, y, w, h, null, collide);
    }
    public Entity(int x, int y, int w, int h, Runnable upd, Consumer<Entity> collide) {
        this(x, y, w, h, 0, upd, collide);
    }
    public Entity(int x, int y, Runnable upd) {
        this(x, y, 0, 0, upd);
    }
    public Entity(int x, int y, Consumer<Entity> collide) {
        this(x, y, 0, 0, collide);
    }
    public Entity(int x, int y, Runnable upd, Consumer<Entity> collide) {
        this(x, y, 0, 0, upd, collide);
    }
    public Entity(Runnable upd, Consumer<Entity> collide) {
        this(0, 0, upd, collide);
    }
    public Entity(int x, int y, int w, int h, double rot) {
        this(x, y, w, h, rot, null, null);
    }
    public Entity(int x, int y, int w, int h, double rot, Runnable upd) {
        this(x, y, w, h, rot, upd, null);
    }
    public Entity(int x, int y, int w, int h, double rot, Consumer<Entity> collide) {
        this(x, y, w, h, rot, null, collide);
    }
    public Entity(int x, int y, int w, int h, double rot, Runnable upd, Consumer<Entity> collide) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rot = rot;
        this.upd = upd;
        this.collide = collide;
    }
    public Entity(int x, int y, double rot, Runnable upd) {
        this(x, y, 0, 0, rot, upd, null);
    }
    public Entity(int x, int y, double rot, Consumer<Entity> collide) {
        this(x, y, 0, 0, null, collide);
    }
    public Entity(int x, int y, double rot, Runnable upd, Consumer<Entity> collide) {
        this(x, y, 0, 0, upd, collide);
    }
    public Entity(double rot, Runnable upd, Consumer<Entity> collide) {
        this(0, 0, 0, 0, rot, upd, collide);
    }
    public Entity(double rot) {
        this(0, 0, rot);
    }
    public Entity(int x, int y, double rot) {
        this(x, y, 0, 0, rot);
    }
    public void render(Brush b) {
        //
    }
}