package utils.canvas;
import java.awt.*;
import javax.swing.*;
import utils.Array;

public class Canvas extends JPanel {
    public Array<Renderable> rend;
    public int delta;
    public Timer timer;
    public Canvas() {
        this(true);
    }
    public Canvas(int delay) {
        this(delay, true);
    }
    public Canvas(boolean focusable) {
        this(100, focusable);
    }
    public Canvas(int delay, boolean focusable) {
        this.setFocusable(focusable);
        this.rend = new Array<Renderable>();
        this.timer = new Timer(delay, (e) -> {
            this.delta++;
            this.rend.forEach(r -> r.tick());
            this.repaint();
        });
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.rend.forEach(r -> r.render(new Brush(g)));
    }
}