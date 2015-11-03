package jumpingcat;

import java.awt.*;
import java.awt.Image;
import java.util.*;
import javax.swing.ImageIcon;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 * @Author: Mehedi Hasan Nirob
 * @Reg. No. : 2012331030
 * @Class: This class describes the characteristic of different types of food.
 */
public class Food {

    Image[] food = new Image[30];
    int x[] = new int[30];
    int y[] = new int[30];
    int fX[] = new int[20];
    int fy[] = new int[20];
    int random[] = new int[8];
    long timePassed = 0;
    Collision vcol = new Collision();

    ScreenManager bk;

    /**
     * Initializing foods and their co-ordinates
     *
     * @param sc
     */
    Food(ScreenManager sc) {
        bk = sc;
        food[0] = new ImageIcon("images\\mouse1.png").getImage();
        food[1] = new ImageIcon("images\\mouse2.png").getImage();
        food[2] = new ImageIcon("images\\fruit1.png").getImage();
        food[3] = new ImageIcon("images\\fruit2.png").getImage();
        food[4] = new ImageIcon("images\\fruit3.png").getImage();
        food[5] = new ImageIcon("images\\fruit4.png").getImage();
        food[6] = new ImageIcon("images\\fruit5.png").getImage();
        food[7] = new ImageIcon("images\\fruit6.png").getImage();
        food[8] = new ImageIcon("images\\fruit7.png").getImage();
        food[9] = new ImageIcon("images\\milk.png").getImage();
        food[10] = new ImageIcon("images\\germ1.png").getImage();
        food[11] = new ImageIcon("images\\germ2.png").getImage();
        food[12] = new ImageIcon("images\\germ3.png").getImage();
        food[13] = new ImageIcon("images\\fast1.png").getImage();
        food[14] = new ImageIcon("images\\fast2.png").getImage();
        food[15] = new ImageIcon("images\\spider.png").getImage();
        food[16] = new ImageIcon("images\\fruit8.png").getImage();
        food[17] = new ImageIcon("images\\fruit9.png").getImage();
        food[18] = new ImageIcon("images\\mushroom.png").getImage();
        food[19] = new ImageIcon("images\\pizza.png").getImage();
        food[20] = new ImageIcon("images\\germ4.png").getImage();
        food[21] = new ImageIcon("images\\bomb.png").getImage();
        food[22] = new ImageIcon("images\\fast3.png").getImage();
        food[23] = new ImageIcon("images\\fast4.png").getImage();
        food[24] = new ImageIcon("images\\fast5.png").getImage();
        food[25] = new ImageIcon("images\\fast6.png").getImage();
        food[26] = new ImageIcon("images\\cupcake.png").getImage();

        x[0] = 100;
        x[1] = 200;
        x[2] = 300;
        x[3] = 400;
        x[4] = 500;
        x[5] = 600;
        x[6] = 700;
        x[7] = 50;
        x[8] = 150;
        x[9] = 250;
        x[10] = 350;
        x[11] = 450;
        x[12] = 550;
        x[13] = 650;
        x[14] = 25;
        x[15] = 75;
        x[16] = 125;
        x[17] = 175;
        x[18] = 225;
        x[19] = 275;
        x[20] = 325;
        x[21] = 375;
        x[22] = 425;
        x[23] = 475;
        x[24] = 525;
        x[25] = 575;
        x[26] = 625;

        y[0] = 20;
        y[1] = 40;
        y[2] = 60;
        y[3] = 80;
        y[4] = 100;
        y[5] = 120;
        y[6] = 140;
        y[7] = 160;
        y[8] = 180;
        y[9] = 200;
        y[10] = 220;
        y[11] = 240;
        y[12] = 260;
        y[13] = 280;
        y[14] = 300;
        y[15] = 30;
        y[16] = 50;
        y[17] = 70;
        y[18] = 90;
        y[19] = 110;
        y[20] = 130;
        y[21] = 150;
        y[22] = 170;
        y[23] = 190;
        y[24] = 210;
        y[25] = 230;
        y[26] = 275;

    }

    /**
     * Give random number of food and co-ordinate for platform
     *
     * @param sleepCycle
     * @param currentTime
     * @param g
     */
    public void update(long sleepCycle, long currentTime, Graphics2D g) {
        Random randomGenerator = new Random();

        if ((currentTime - sleepCycle * 3) % 1000 <= 10) {

            JumpingCat.b1 = true;
            JumpingCat.b2 = true;
            JumpingCat.b3 = true;

            for (int i = 0; i <= 2; i++) {
                random[i] = randomGenerator.nextInt(27);
                if (i == 2) {
                    if (random[i] == random[i - 1] || random[i] == random[i - 2]) {
                        i--;
                    }
                } else if (i == 1) {
                    if (random[i] == random[i - 1]) {
                        i--;
                    }
                }
            }

            for (int i = 3; i <= 5; i++) {
                random[i] = randomGenerator.nextInt(27);
            }
        }
    }

    /**
     * Paint food and platform
     *
     * @param g
     * @param vcat
     */
    public void paint(Graphics2D g, Cat vcat) {
        if (JumpingCat.b1 == true) {
            g.drawImage(food[random[3]], x[random[0]], y[random[0]], null);
            vcol.FoodCollision(random[3], 1, x[random[0]], y[random[0]], food[random[3]].getHeight(null), food[random[3]].getWidth(null), vcat, g);
        }
        if (JumpingCat.b2 == true) {
            g.drawImage(food[random[4]], x[random[1]], y[random[1]], null);
            vcol.FoodCollision(random[4], 2, x[random[1]], y[random[1]], food[random[4]].getHeight(null), food[random[4]].getWidth(null), vcat, g);
        }
        if (JumpingCat.b3 == true) {
            g.drawImage(food[random[5]], x[random[2]], y[random[2]], null);
            vcol.FoodCollision(random[5], 3, x[random[2]], y[random[2]], food[random[5]].getHeight(null), food[random[5]].getWidth(null), vcat, g);
        }

    }

}
