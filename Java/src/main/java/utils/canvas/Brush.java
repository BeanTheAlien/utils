import java.awt.*;

public class Brush {
    public Graphics g;
    public Graphics2D g2d;
    public Brush(Graphics g) {
        this.g = g;
        this.g2d = (Graphics2D)g;
    }
}