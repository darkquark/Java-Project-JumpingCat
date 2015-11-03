package jumpingcat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

/**
 * @Author: Mehedi Hasan Nirob
 * @Reg. No. : 2012331030
 * @Class: This class describes the characteristic of Platform object.
 */
public class Platform {

    int x, y, width, height;
    int dy = -1;
    int dx = -1;
    Image platform;

    public Platform(int i, int j) {
        x = i;
        y = j;
        width = 120;
        height = 20;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int h) {
        height = h;
    }

    public void setWidth(int w) {
        width = w;
    }

    /**
     * these update class change co-ordinate of platform
     *
     * @param dx
     * @param ind
     */
    public void updateX3(int dx, int ind) {
        if (x == -1 * width || x == -1 * width + 1 || x == -1 * width + 2) {
            x = 800;
        }
        x += dx;
    }

    public void updateX4(int dx, int ind) {
        if (x == -1 * width || x == -1 * width + 1 || x == -1 * width + 2) {
            x = 800;
        }
        x += dx;
    }

    public void updateX(int dx, int ind) {
        if (x == -1 * width) {
            x = 800;
        }
        x += dx;
    }

    public void updateY(int ind) {
        if (y <= 400) {
            dy = 1;
        }
        if (y >= 500) {
            dy = -1;
        }
        y += dy;
    }

    public void updateEnemyX() {
        x += dx;
        if (x <= -30 || x >= 830) {
            dx *= -1;
        }
    }

    public void updateEnemyY() {
        if (y >= 350) {
            dy = -1;
        }
        if (y <= 20) {
            dy = 1;
        }
        y += dy;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void paintEnemy(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    void setX(int i) {
        x = i;
    }

    void setY(int i) {
        y = i;;
    }

}
