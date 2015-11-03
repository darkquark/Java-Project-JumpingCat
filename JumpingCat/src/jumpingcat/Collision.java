package jumpingcat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * @Author: Mehedi Hasan Nirob
 * @Reg. No. : 2012331030
 * @Class: This class handles collision.
 */
public class Collision {

    String str = "0";
    JumpingCat jVar = new JumpingCat();

    /**
     * This Method detects collision of cat with platform
     *
     * @param p
     * @param c
     */
    public void PlatformCollision(Platform p, Cat c) {
        int x = p.getX();
        int y = p.getY();
        int height = p.getHeight();
        int width = p.getWidth();

        int cx = c.getX();
        int cy = c.getY();

        int cheight = c.getHeight();
        int cwidth = c.getWidth();

        double lfrac = 1, rfrac = 0.1;

        if ((cx + lfrac * cwidth - 50 >= x && cx + rfrac * cwidth <= x + width)) {
            jVar.njumpTime = System.currentTimeMillis();
            if (cy + cheight - 50 >= y) {
                c.dy = 0;
                c.dx = 0;
            }

        }

        return;
    }

    /**
     * This Method detect collision of cat with food
     *
     * @param ind
     * @param n
     * @param x
     * @param y
     * @param height
     * @param width
     * @param c
     * @param g
     */
    public void FoodCollision(int ind, int n, int x, int y, int height, int width, Cat c, Graphics2D g) {

        int cx = c.getX();
        int cy = c.getY();
        int cheight = c.getHeight();
        int cwidth = c.getWidth();
        int eat = 40;
        if ((cx - eat <= x && cx + cwidth + eat >= x + width)) {
            if ((cy - eat <= y && cy + cheight + eat >= y + height)) {
                if (ind == 10 || ind == 11 || ind == 12 || ind == 20 || ind == 15 || ind == 21) {
                    JumpingCat.score--;
                } else {
                    JumpingCat.score++;
                }
                if (n == 1) {
                    JumpingCat.b1 = false;
                } else if (n == 2) {
                    JumpingCat.b2 = false;
                } else {
                    JumpingCat.b3 = false;
                }
            }

        }

        return;
    }

    /**
     * This Method detect collision of cat with Enemy platform (Level 4)
     *
     * @param c
     * @param p
     * @return
     */
    public boolean EnemyCollision(Cat c, Platform p) {
        int cx = c.getX();
        int cy = c.getY();
        int cheight = c.getHeight();
        int cwidth = c.getWidth();

        int px = p.getX();
        int py = p.getY();
        int pheight = p.getHeight();
        int pwidth = p.getWidth();

        if (px >= cx && px + pwidth <= cx + cwidth) {
            if (py <= cy && py + pheight <= cy + cheight) {
                return true;
            }
        }
        return false;
    }

    /**
     * Paint Score
     *
     * @param g
     */
    public void paint(Graphics2D g) {
        str = Integer.toString(JumpingCat.score);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("score : ", 640, 27);
        g.drawString(str, 745, 30);
    }

}
