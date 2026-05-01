package utils.canvas;
import java.awt.*;

import utils.Parse;

public class Brush {
    public Graphics g;
    public Graphics2D g2d;
    public Brush(Graphics g) {
        this.g = g;
        this.g2d = (Graphics2D)g;
    }
    public Color color() {
        return this.g.getColor();
    }
    public void color(Color color) {
        this.g.setColor(color);
    }
    public void color(int r, int g, int b) {
        this.color(new Color(r, g, b));
    }
    public void color(String hex) {
        String x = hex.substring(1);
        this.color(new Color(Parse.toInt(x.substring(0, 2), 16), Parse.toInt(x.substring(2, 4), 16), Parse.toInt(x.substring(4, 6), 16)));
    }
}