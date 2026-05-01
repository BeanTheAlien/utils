package utils.canvas;
import java.awt.*;
import java.awt.geom.AffineTransform;

import utils.Parse;

public class Brush {
    public Graphics g;
    public Graphics2D g2d;
    protected AffineTransform __transform;
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
    public Font font() {
        return this.g.getFont();
    }
    public void font(Font font) {
        this.g.setFont(font);
    }
    public void font(String fontFamily, int fontStyle, int fontSize) {
        this.font(new Font(fontFamily, fontStyle, fontSize));
    }
    public void rect(int x, int y, int w, int h) {
        this.g.fillRect(x, y, w, h);
    }
    public void rect(int x, int y, int w, int h, Color color) {
        this.color(color);
        this.rect(x, y, w, h);
    }
    public void rect(int x, int y, int w, int h, String hex) {
        this.color(hex);
        this.rect(x, y, w, h);
    }
    public void circle(int x, int y, int rad) {
        this.g.fillOval(x, y, rad, rad);
    }
    public void circle(int x, int y, int rad, Color color) {
        this.color(color);
        this.circle(x, y, rad);
    }
    public void circle(int x, int y, int rad, String hex) {
        this.color(hex);
        this.circle(x, y, rad);
    }
    public void save() {
        this.__transform = this.g2d.getTransform();
    }
    public void restore() {
        this.g2d.setTransform(this.__transform);
        this.__transform = null;
    }
    public void text(String tx, int x, int y) {
        this.g.drawString(tx, x, y);
    }
    public void text(String tx, int x, int y, Color color) {
        this.color(color);
        this.text(tx, x, y);
    }
    public void text(String tx, int x, int y, String hex) {
        this.color(hex);
        this.text(tx, x, y);
    }
    public void rotate(int x, int y, double deg) {
        double rad = Math.toRadians(deg);
        this.g2d.rotate(x, y, rad);
    }
    public void move(int x, int y) {
        this.g2d.translate(x, y);
    }
    public void ln(int x1, int y1, int x2, int y2) {
        this.g.drawLine(x1, y1, x2, y2);
    }
    public void ln(int x1, int y1, int x2, int y2, Color color) {
        this.color(color);
        this.g.drawLine(x1, y1, x2, y2);
    }
    public void ln(int x1, int y1, int x2, int y2, String hex) {
        this.color(hex);
        this.g.drawLine(x1, y1, x2, y2);
    }
    public void img(Image img, int x, int y) {
        this.g.drawImage(img, x, y, null);
    }
    public void img(Image img, int x, int y, int w, int h) {
        this.g.drawImage(img, x, y, w, h, null);
    }
}