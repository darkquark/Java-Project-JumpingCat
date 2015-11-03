package jumpingcat;

import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @Author : Mehedi Hasan Nirob
 * @Reg. No. : 2012331030
 * @Class: This class describes the characteristic of Cat object.
 */
public class Cat {

    int dx = 0;
    int dy = 0;
    int x;
    int y;
    int vx;
    int vy;
    int gravity = 0;
    double energeyloss = 0;
    double dt = .2;
    Image cat;

    ScreenManager bk;

    Cat(int a, int b, ScreenManager bk) {
        x = a;
        y = b;
        this.bk = bk;

        cat = new ImageIcon("cat2.png").getImage();
    }

    public int getX() {
        return (x);
    }

    public int getY() {
        return (y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGravity(int y) {
        gravity = y;
    }

    public int getdx() {
        return (dx);
    }

    public int getdy() {
        return (dy);
    }

    public void setdx(int dx) {
        this.dx = dx;
    }

    public void setdy(int dy) {
        this.dy = dy;
    }

    public int getRad() {
        return (cat.getWidth(null) / 2);
    }

    int getHeight() {
        return (cat.getHeight(null));
    }

    int getWidth() {
        return (cat.getWidth(null));
    }

    /**
     * This Method is for gravitational motion of cat object
     */
    public void update() {
        if (x + dx > bk.getWidth() - (cat.getWidth(null) / 2)) {
            x = bk.getWidth() - (cat.getWidth(null) / 2) - 1;
            dx = -dx;
        } else if (x + dx < 0 + (cat.getWidth(null)) / 2) {
            x = 0 + (cat.getWidth(null) / 2);
            dx = -dx;
        } else {
            x += dx;
        }
        if (y < 0 + cat.getWidth(null) / 2) {
            y = 0 + cat.getWidth(null) / 2;
        } else if (y > 600) {
            y = 600 - cat.getHeight(null) / 2 - 1;
            gravity = 0;
            dy = 0;
            dx = 0;
        } else {
            dy += gravity * dt;
            y += dy * dt - .5 * gravity * dt * dt;
        }
    }

    public void paint(Graphics2D g) {
        g.drawImage(cat, x - (cat.getWidth(null) / 2), y - (cat.getHeight(null) / 2), null);
    }

}
