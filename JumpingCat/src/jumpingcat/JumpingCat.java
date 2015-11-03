package jumpingcat;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import jumpingcat.ScreenManager;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 * @Author:Mehedi Hasan Nirob
 * @Reg. No.: 2012331030
 * @Project Name: Jumping Bird
 */
/**
 * This is the main class of this project.
 */
public class JumpingCat implements KeyListener, MouseListener, MouseMotionListener {

    /**
     * s = Main ScreenManager Object. Where the game window runs.
     */
    ScreenManager screen;

    /**
     * gm = Main Graphics2D Object. Where the game window runs.
     */
    Graphics2D g2d;

    /**
     * Images Used to display the background of each and every new window.
     */
    Image background, mainMenu, blackBack, pauseScreen, level1, level2, level3, level4, lFailed;

    /**
     * These are the boolean variables to control the whole gameplay.
     */
    boolean GameRunning = true;
    boolean openingScreen = true;
    boolean option = false;
    boolean practiceLevel = false;
    boolean finalScore = false;
    boolean highScore = false;
    boolean instruct = false;
    boolean startLevel = false;
    boolean screenLevel = false;
    boolean failed = false;
    static boolean b1 = true;
    static boolean b2 = true;
    static boolean b3 = true;
    boolean pause = false;

    /**
     * Objects of different Classes used in this project
     */
    Cat vcat;
    Food food;
    Platform platform[] = new Platform[7];
    Collision vcol;

    /**
     * All about score
     */
    static int score = 0, score1, score2, score3, score4;

    /**
     * These long integers are used to store the HighScore of different level
     */
    long level1High, level2High, level3High, level4High;

    int level = 1;

    /**
     * These variables control the timings in various difficulty levels.
     */
    long prevTime, currentTime, timePassed, ttimePassed, timeLevel;
    long sleepCycle = 0;
    static long pjumpTime, njumpTime;
    long second;

    /**
     * This static object initiates the Background Sound at the very beginning.
     */
    final static SoundThread bsound = new SoundThread();

    /**
     * The main method of the game. Here the main object to run the game is
     * created. It also initiates the run method by which every single task of
     * the game is performed.
     *
     * @param args = arguments from the terminal. Not needed.
     */
    public static void main(String args[]) {
        JumpingCat objmain = new JumpingCat();
        objmain.run();
    }

    /**
     * DisplayMode to choose for full screen. Note: This is initiated only for
     * my own pc. Haven't got the opportunity to check if it works on every pc.
     * But in most cases it works fine.
     */
    private static final DisplayMode modes1[]
            = {
                new DisplayMode(800, 600, 32, 0), // new DisplayMode(800,600,24,0),
            //new DisplayMode(800,600,16,0),
            //new DisplayMode(640,480,32,0),
            //new DisplayMode(640,480,24,0),
            //new DisplayMode(640,480,16,0),
            //new DisplayMode(1366,768,16,0),
            };

    /**
     * This method initializes everything to start the game.
     */
    void init() {
        background = new ImageIcon("background.png").getImage();
        mainMenu = new ImageIcon("menu.jpg").getImage();
        blackBack = new ImageIcon("black.jpg").getImage();
        pauseScreen = new ImageIcon("pausedCat.jpg").getImage();
        level1 = new ImageIcon("level1.jpg").getImage();
        level2 = new ImageIcon("level2.jpg").getImage();
        level3 = new ImageIcon("level3.jpg").getImage();
        level4 = new ImageIcon("level4.jpg").getImage();
        lFailed = new ImageIcon("Failed.jpg").getImage();

        screen = new ScreenManager();

        vcat = new Cat(400, 390, screen);
        food = new Food(screen);
        vcol = new Collision();

        timeLevel = 60;

        platform[0] = new Platform(110, 500);
        platform[1] = new Platform(340, 440);
        platform[2] = new Platform(570, 500);
        platform[3] = new Platform(800, 440);
        platform[4] = new Platform(-15, 20);
        platform[4].setHeight(120);
        platform[4].setWidth(10);
        platform[5] = new Platform(800, 20);
        platform[5].setHeight(120);
        platform[5].setWidth(10);

    }

    /**
     * This is the main run method. Where the gameplay begins. Every single task
     * of this game is handled by this method.
     */
    public void run() {
        init();

        try {
            DisplayMode dm = screen.findFirstCompatibleMode(modes1);
            screen.setFullScreen(dm);
            Window w = screen.getFullScreenWindow();
            w.addKeyListener(this);
            w.addMouseListener(this);

            /**
             * Here the game menu initializes.
             */
            while (GameRunning == true) {
                g2d = screen.getGraphics();

                /**
                 * This while loop displays Opening Screen
                 */
                while (openingScreen == true) {
                    g2d = screen.getGraphics();
                    paintMenu(g2d);
                    screen.update();
                    g2d.dispose();
                }

                /**
                 * This while loop displays Highscores
                 */
                while (highScore == true) {
                    g2d = screen.getGraphics();
                    paintHighScore(g2d);
                    screen.update();
                    g2d.dispose();
                }

                /**
                 * This while loop displays instructions
                 */
                while (instruct == true) {
                    g2d = screen.getGraphics();
                    paintInstructions(g2d);
                    screen.update();
                    g2d.dispose();
                }

                /**
                 * This if condition is to quit the game
                 */
                if (GameRunning == false) {
                    break;
                }

                /**
                 * This while loop display Screen for choosing option
                 */
                while (option == true) {
                    g2d = screen.getGraphics();
                    paintOption1(g2d);
                    screen.update();
                    g2d.dispose();

                }
                if (GameRunning == false) {
                    break;
                }
                /**
                 * This while loop display Screen for choosing level in practice
                 * mood
                 */
                while (practiceLevel == true) {
                    g2d = screen.getGraphics();
                    paintOption2(g2d);
                    screen.update();
                    g2d.dispose();
                }

                if (GameRunning == false) {
                    break;
                }

                /**
                 * Gameplay for Level 1
                 */
                if (level == 1) {

                    /**
                     * Option for starting this level
                     */
                    while (screenLevel) {
                        g2d = screen.getGraphics();
                        paintLevel1(g2d);
                        screen.update();
                        g2d.dispose();

                    }

                    if (startLevel) {
                        second = timeLevel + 1;
                        score = 0;
                        vcat.setX(400);
                        vcat.setY(390);
                        platform[0].setX(110);
                        platform[0].setY(500);
                        platform[1].setX(340);
                        platform[1].setY(440);
                        platform[2].setX(570);
                        platform[2].setY(500);
                        platform[3].setX(800);
                        platform[3].setY(440);
                        platform[4].setX(-15);
                        platform[4].setY(20);
                        platform[5].setX(800);
                        platform[5].setY(20);
                        startLevel = false;
                        prevTime = System.currentTimeMillis();
                    }

                    /**
                     * Show remaining time
                     */
                    paintOpeningScreen(g2d);
                    String str = Long.toString(second);
                    g2d.setColor(Color.BLACK);
                    Font f1 = new Font("Monotype Corsiva", Font.BOLD, 40);
                    g2d.setFont(f1);
                    g2d.drawString(str, 35, 35);

                    /**
                     * Show if level failed or passed and save highscore
                     */
                    if (second <= 0 && score >= 20) {
                        level = 2;
                        score1 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level1High < score1) {
                            level1High = score1;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level1.txt"));
                                outputWriter.write(Integer.toString((int) level1High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                    } else if (second == 0 || vcat.getHeight() + vcat.getY() >= 600) {
                        failed = true;
                        score1 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level1High < score1) {
                            level1High = score1;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level1.txt"));
                                outputWriter.write(Integer.toString((int) level1High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                        while (failed && openingScreen == false) {
                            g2d = screen.getGraphics();
                            paintFailed(g2d, 1);
                            screen.update();
                            g2d.dispose();
                        }
                    }

                    /**
                     * Show Pausing Screen
                     */
                    while (pause) {
                        g2d = screen.getGraphics();
                        paintPause(g2d);
                        screen.update();
                        g2d.dispose();
                    }

                    /**
                     * Updating time by 1 second
                     */
                    currentTime = System.currentTimeMillis();
                    timePassed = currentTime - prevTime;
                    ttimePassed += timePassed;

                    if (ttimePassed >= 1000) {
                        second--;
                        screen.update();
                        g2d.dispose();
                        ttimePassed = 0;
                    }
                    prevTime += timePassed;

                }

                if (GameRunning == false) {
                    break;
                }

                /**
                 * Gameplay for Level 2
                 */
                if (level == 2) {

                    /**
                     * Option for starting this level
                     */
                    while (screenLevel) {
                        g2d = screen.getGraphics();
                        paintLevel2(g2d);
                        screen.update();
                        g2d.dispose();
                        startLevel = true;
                    }

                    if (startLevel) {
                        second = timeLevel + 1;
                        score = 0;

                        vcat.setX(400);
                        vcat.setY(390);
                        vcat.dy = 1;
                        vcat.gravity = 6;
                        platform[0].setX(110);
                        platform[0].setY(500);
                        platform[1].setX(340);
                        platform[1].setY(440);
                        platform[2].setX(570);
                        platform[2].setY(500);
                        platform[3].setX(800);
                        platform[3].setY(440);
                        platform[4].setX(-15);
                        platform[4].setY(20);
                        platform[5].setX(800);
                        platform[5].setY(20);
                        startLevel = false;
                    }

                    paintOpeningScreen(g2d);
                    String str = Long.toString(second);
                    g2d.setColor(Color.BLACK);
                    Font f1 = new Font("Monotype Corsiva", Font.BOLD, 40);
                    g2d.setFont(f1);
                    g2d.drawString(str, 35, 35);

                    /**
                     * Show if level failed or passed and save highscore
                     */
                    if (second <= 0) {
                        level = 3;
                        score2 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level2High < score2) {
                            level2High = score2;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level2.txt"));
                                outputWriter.write(Integer.toString((int) level2High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }

                    } else if (vcat.getHeight() + vcat.getY() >= 600) {
                        failed = true;
                        score1 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level2High < score1) {
                            level2High = score1;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level2.txt"));
                                outputWriter.write(Integer.toString((int) level2High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                        while (failed && openingScreen == false) {
                            g2d = screen.getGraphics();
                            paintFailed(g2d, 2);
                            screen.update();
                            g2d.dispose();
                        }
                    }

                    while (pause) {
                        g2d = screen.getGraphics();
                        paintPause(g2d);
                        screen.update();
                        g2d.dispose();
                    }

                    /**
                     * Updating platform
                     */
                    platform[0].updateX(-1, 0);
                    platform[1].updateX(-1, 1);
                    platform[2].updateX(-1, 2);
                    platform[3].updateX(-1, 3);

                    /**
                     * Updating time by 1 second
                     */
                    currentTime = System.currentTimeMillis();
                    timePassed = currentTime - prevTime;
                    ttimePassed += timePassed;

                    if (ttimePassed >= 1000) {
                        second--;
                        screen.update();
                        g2d.dispose();
                        ttimePassed = 0;
                    }
                    prevTime += timePassed;
                }

                if (GameRunning == false) {
                    break;
                }

                /**
                 * Gameplay for Level 3
                 */
                if (level == 3) {

                    /**
                     * Option for starting this level
                     */
                    while (screenLevel) {
                        g2d = screen.getGraphics();
                        paintLevel3(g2d);
                        screen.update();
                        g2d.dispose();

                    }

                    if (startLevel) {
                        second = timeLevel + 1;
                        score = 0;

                        vcat.setX(400);
                        vcat.setY(390);
                        vcat.dy = 1;
                        vcat.setGravity(6);
                        platform[0].setX(110);
                        platform[0].setY(500);
                        platform[1].setX(340);
                        platform[1].setY(440);
                        platform[2].setX(570);
                        platform[2].setY(500);
                        platform[3].setX(800);
                        platform[3].setY(440);
                        platform[4].setX(-15);
                        platform[4].setY(20);
                        platform[5].setX(800);
                        platform[5].setY(20);
                        startLevel = false;
                    }

                    paintOpeningScreen(g2d);
                    String str = Long.toString(second);
                    g2d.setColor(Color.BLACK);
                    Font f1 = new Font("Monotype Corsiva", Font.BOLD, 40);
                    g2d.setFont(f1);
                    g2d.drawString(str, 35, 35);

                    /**
                     * Show if level failed or passed and save highscore
                     */
                    if (second <= 0) {
                        level = 4;
                        score3 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level3High < score3) {
                            level3High = score3;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level3.txt"));
                                outputWriter.write(Integer.toString((int) level3High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                    } else if (vcat.getHeight() + vcat.getY() >= 600) {
                        failed = true;
                        score1 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level3High < score1) {
                            level3High = score1;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level3.txt"));
                                outputWriter.write(Integer.toString((int) level3High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                        while (failed && openingScreen == false) {
                            g2d = screen.getGraphics();
                            paintFailed(g2d, 3);
                            screen.update();
                            g2d.dispose();
                        }
                    }

                    while (pause) {
                        g2d = screen.getGraphics();
                        paintPause(g2d);
                        screen.update();
                        g2d.dispose();
                    }

                    /**
                     * Updating platform
                     */
                    platform[0].updateX3(-2, 0);
                    platform[1].updateX3(-2, 1);
                    platform[2].updateX3(-2, 2);
                    platform[3].updateX3(-2, 3);

                    /**
                     * Updating time by 1 second
                     */
                    currentTime = System.currentTimeMillis();
                    timePassed = currentTime - prevTime;
                    ttimePassed += timePassed;

                    if (ttimePassed >= 1000) {
                        second--;
                        screen.update();
                        g2d.dispose();
                        ttimePassed = 0;
                    }
                    prevTime += timePassed;

                }

                if (GameRunning == false) {
                    break;
                }

                /**
                 * Gameplay for Level 4
                 */
                if (level == 4) {

                    /**
                     * Option for starting this level
                     */
                    while (screenLevel) {
                        g2d = screen.getGraphics();
                        paintLevel4(g2d);
                        screen.update();
                        g2d.dispose();

                    }

                    if (startLevel) {
                        second = timeLevel + 1;
                        score = 0;

                        vcat.setX(400);
                        vcat.setY(390);
                        vcat.dy = 1;
                        vcat.setGravity(6);
                        platform[0].setX(110);
                        platform[0].setY(500);
                        platform[1].setX(340);
                        platform[1].setY(440);
                        platform[2].setX(570);
                        platform[2].setY(500);
                        platform[3].setX(800);
                        platform[3].setY(440);
                        platform[4].setX(-15);
                        platform[4].setY(20);
                        platform[5].setX(800);
                        platform[5].setY(20);
                        startLevel = false;
                    }

                    paintOpeningScreen(g2d);
                    String str = Long.toString(second);
                    g2d.setColor(Color.BLACK);
                    Font f1 = new Font("Monotype Corsiva", Font.BOLD, 40);
                    g2d.setFont(f1);
                    g2d.drawString(str, 35, 35);

                    /**
                     * Show if level failed or passed and save highscore
                     */
                    if (second <= 0) {
                        score4 = score;
                        BufferedWriter outputWriter = null;
                        if (level4High < score4) {
                            level4High = score4;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level4.txt"));
                                outputWriter.write(Integer.toString((int) level4High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                        finalScore = true;
                        while (finalScore) {
                            g2d = screen.getGraphics();
                            paintfinalScore(g2d);
                            screen.update();
                            g2d.dispose();
                        }
                    } else if (vcat.getHeight() + vcat.getY() >= 600 || vcol.EnemyCollision(vcat, platform[4])) {
                        failed = true;
                        score1 = score;
                        screenLevel = true;
                        BufferedWriter outputWriter = null;
                        if (level4High < score1) {
                            level4High = score1;
                            try {
                                outputWriter = new BufferedWriter(new FileWriter("Scores/Level4.txt"));
                                outputWriter.write(Integer.toString((int) level4High));
                                outputWriter.flush();
                                outputWriter.close();
                            } catch (Exception e) {
                            }
                        }
                        while (failed && openingScreen == false) {
                            g2d = screen.getGraphics();
                            paintFailed(g2d, 4);
                            screen.update();
                            g2d.dispose();
                        }
                    }

                    while (pause) {
                        g2d = screen.getGraphics();
                        paintPause(g2d);
                        screen.update();
                        g2d.dispose();
                    }

                    /**
                     * Updating platform
                     */
                    platform[0].updateX4(-2, 0);
                    platform[1].updateX4(-2, 1);
                    platform[2].updateX4(-2, 2);
                    platform[3].updateX4(-2, 3);
                    platform[4].updateEnemyX();
                    platform[4].updateEnemyY();

                    /**
                     * Updating time by 1 second
                     */
                    currentTime = System.currentTimeMillis();
                    timePassed = currentTime - prevTime;
                    ttimePassed += timePassed;

                    if (ttimePassed >= 1000) {
                        second--;
                        screen.update();
                        g2d.dispose();
                        ttimePassed = 0;
                    }
                    prevTime += timePassed;

                }

                screen.update();
                g2d.dispose();
                vcat.update();
                food.update(sleepCycle, currentTime, g2d);
                for (int i = 0; i <= 3; i++) {
                    vcol.PlatformCollision(platform[i], vcat);
                }

                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }
                sleepCycle = sleepCycle + 1;

            }

        } finally {
            screen.restoreScreen();
        }

    }

    /**
     * This method reads the stored HighScores from the consecutive files.
     */
    public void getHighScore() {
        try {
            File Fe = new File("Scores/Level1.txt");
            File Fm = new File("Scores/Level2.txt");
            File Fh = new File("Scores/Level3.txt");
            File Fk = new File("Scores/Level4.txt");
            Scanner Se = new Scanner(Fe);
            Scanner Sm = new Scanner(Fm);
            Scanner Sh = new Scanner(Fh);
            Scanner Sk = new Scanner(Fk);
            level1High = Se.nextInt();
            level2High = Sm.nextInt();
            level3High = Sh.nextInt();
            level4High = Sk.nextInt();
            Se.close();
            Sm.close();
            Sh.close();
            Sk.close();
        } catch (Exception e) {
        }
    }

    /**
     * Paint opening screen
     *
     * @param g1 refers to the main Graphics2D object.
     */
    public void paintOpeningScreen(Graphics2D g1) {
        g1.drawImage(background, 0, 0, null);

        food.paint(g1, vcat);
        vcat.paint(g1);
        vcol.paint(g1);

        platform[0].paint(g1);
        platform[1].paint(g1);
        platform[2].paint(g1);
        platform[3].paint(g1);
        platform[4].paintEnemy(g1);
        platform[5].paintEnemy(g1);
    }

    /**
     * Paint Main Menu
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintMenu(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, null);
        g.drawImage(mainMenu, -100, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 40);
        g.setFont(f);
        g.drawString("Play", 630, 270);
        g.drawString("High Scores", 560, 320);
        g.drawString("Instructions", 565, 370);
        g.drawString("Quit", 630, 420);
    }

    /**
     * Paint Option screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintOption1(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 40);
        g.setFont(f);
        g.drawString("Career", 400, 270);
        g.drawString("Practice", 400, 320);
        g.drawString("Main Menu", 400, 370);
    }

    /**
     * Paint Option screen (Level)
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintOption2(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 40);
        g.setFont(f);
        g.drawString("Level 1", 180, 250);
        g.drawString("Level 2", 180, 300);
        g.drawString("Level 3", 180, 350);
        g.drawString("Level 4", 180, 400);
    }

    /**
     * Paint Level 1 starting screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintLevel1(Graphics2D g) {
        g.drawImage(level1, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 60);
        g.setFont(f);
        g.drawString("LEVEL 1", 300, 150);
        g.drawString("Start", 335, 340);
    }

    /**
     * Paint Level 2 starting screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintLevel2(Graphics2D g) {
        g.drawImage(level2, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 60);
        g.setFont(f);
        g.drawString("LEVEL 2", 300, 150);
        g.drawString("Start", 335, 340);
    }

    /**
     * Paint Level 3 starting screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintLevel3(Graphics2D g) {
        g.drawImage(level3, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 60);
        g.setFont(f);
        g.drawString("LEVEL 3", 300, 150);
        g.drawString("Start", 335, 340);
    }

    /**
     * Paint Level 4 starting screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintLevel4(Graphics2D g) {
        g.drawImage(level4, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 60);
        g.setFont(f);
        g.drawString("LEVEL 4", 300, 150);
        g.drawString("Start", 335, 340);
    }

    /**
     * Paint Final Score screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintfinalScore(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.BOLD, 60);
        g.setFont(f);
        g.drawString("Level 1 : " + score1, 250, 150);
        g.drawString("Level 2 : " + score2, 250, 220);
        g.drawString("Level 3 : " + score3, 250, 290);
        g.drawString("Level 4 : " + score4, 250, 360);
        g.drawString("Main Menu", 250, 500);
    }

    /**
     * Paint screen when level failed
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintFailed(Graphics2D g, int l) {
        g.drawImage(lFailed, 0, 0, 800, 600, null);
        g.setColor(Color.red);
        Font f = new Font("Monotype Corsiva", Font.ITALIC, 60);
        g.setFont(f);
        g.drawString("LEVEL FAILED", 180, 150);
        g.setColor(Color.BLACK);
        Font mf = new Font("Monotype Corsiva", Font.BOLD, 40);
        g.setFont(mf);
        if (l == 1) {
            String str = Integer.toString(JumpingCat.score);
            g.drawString("score : " + str, 305, 250);
            score1 = score;
        } else if (l == 2) {
            String str = Integer.toString(JumpingCat.score);
            g.drawString("score : " + str, 305, 250);
            score2 = score;
        } else if (l == 3) {
            String str = Integer.toString(JumpingCat.score);
            g.drawString("score : " + str, 305, 250);
            score3 = score;
        } else {
            String str = Integer.toString(JumpingCat.score);
            g.drawString("score : " + str, 305, 250);
            score4 = score;
        }
        g.setColor(Color.BLUE);
        g.drawString("Restart Level", 265, 340);
        g.drawString("Main Menu", 290, 400);
    }

    /**
     * Paint pause Screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintPause(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, null);
        g.drawImage(pauseScreen, -180, 0, 900, 700, null);
        g.setColor(Color.WHITE);
        Font f1 = new Font("Bernard MT Condensed", Font.ITALIC, 70);
        g.setFont(f1);
        g.drawString("PAUSED !", 110, 100);
        g.setColor(Color.RED);
        Font f2 = new Font("Bernard MT Condensed", Font.BOLD, 60);
        g.setFont(f2);
        g.drawString("Menu", 590, 270);
        g.drawString("Resume", 550, 370);
    }

    /**
     * Paint Highscore screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintHighScore(Graphics2D g) {
        getHighScore();
        g.drawImage(blackBack, 0, 0, null);
        g.setColor(Color.WHITE);
        Font f1 = new Font("Bernard MT Condensed", Font.ITALIC, 40);
        g.setFont(f1);
        String levelH1 = Integer.toString((int) level1High);
        String levelH2 = Integer.toString((int) level2High);
        String levelH3 = Integer.toString((int) level3High);
        String levelH4 = Integer.toString((int) level4High);
        g.drawString("Level 1 : " + levelH1, 180, 150);
        g.drawString("Level 2 : " + levelH2, 180, 220);
        g.drawString("Level 3 : " + levelH3, 180, 290);
        g.drawString("Level 4 : " + levelH4, 180, 360);
        g.setColor(Color.RED);
        g.drawString("Press M to return to the Main Menu", 180, 500);

    }

    /**
     * Paint Level 1 instructions screen
     *
     * @param g refers to the main Graphics2D object.
     */
    public void paintInstructions(Graphics2D g) {
        g.drawImage(blackBack, 0, 0, null);
        g.setColor(Color.WHITE);
        Font f1 = new Font("Bernard MT Condensed", Font.ITALIC, 30);
        g.setFont(f1);
        g.drawString("Control : Press Space for a jump and use left or", 50, 100);
        g.drawString("               arrow to go to left or right.", 50, 150);
        g.drawString("Level 1 : Score at least 20 to go 2nd level.", 50, 200);
        g.drawString("Level 2 : Survive 60 seconds to go 3rd level.", 50, 250);
        g.drawString("Level 3 : Survive 60 seconds to go 4th level.", 50, 300);
        g.drawString("Level 4 : Survive 60 seconds to finish the", 50, 350);
        g.drawString("               4th and last level.", 50, 400);
        g.setColor(Color.RED);
        g.drawString("Press M to return to the Main Menu", 100, 500);

    }

    /**
     * Handel the all key press events when the program runs. The program Exits
     * on pressing the escape key at anytime. Handles the level selection
     * screen.
     *
     * @param ke refers to the key pressing event.
     */
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            if (vcat.getdy() == 0) {
                vcat.setdy(-80);
                vcat.setGravity(6);
                pjumpTime = System.currentTimeMillis();

            }
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            vcat.setdx(7);
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            vcat.setdx(-7);
        } else if (ke.getKeyCode() == KeyEvent.VK_P) {
            if (pause == false) {
                pause = true;
            } else {
                pause = false;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_M) {
            level = 5;
            openingScreen = true;
            option = false;
            practiceLevel = false;
            highScore = false;
            screenLevel = false;
            instruct = false;
        }

    }

    public void keyReleased(KeyEvent ke) {

    }

    public void keyTyped(KeyEvent ke) {

    }

    /**
     * This class Handles Mouse Click
     *
     * @param me
     */
    public void mouseClicked(MouseEvent me) {
        System.out.println(me.getX() + "  &   " + me.getY());
        int x, y;
        x = 630;
        y = 270;
        if (openingScreen == true) {
            if (me.getX() >= x && me.getX() <= x + 80 && me.getY() <= y && me.getY() >= y - 50) {
                openingScreen = false;
                level = 5;
                failed = false;
                practiceLevel = true;
                option = true;

            }
        }
        x = 560;
        y = 320;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && openingScreen == true) {
            openingScreen = false;
            level = 5;
            failed = false;
            practiceLevel = true;
            option = true;
            highScore = true;
        }

        x = 565;
        y = 370;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && openingScreen == true) {
            openingScreen = false;
            level = 5;
            failed = false;
            practiceLevel = false;
            option = false;
            instruct = true;
        }

        if (pause == true) {
            if (me.getX() >= x && me.getX() <= x + 80 && me.getY() <= y && me.getY() >= y - 50) {
                pause = false;
                level = 1;
                failed = false;
                openingScreen = true;
                vcat.setX(390);
                vcat.setY(400);
            }
        }

        x = 630;
        y = 420;
        if (me.getX() >= x && me.getX() <= x + 80 && me.getY() <= y && me.getY() >= y - 50 && openingScreen == true) {
            openingScreen = false;
            failed = false;
            GameRunning = false;
            screenLevel = false;
            option = false;
        }

        x = 550;
        y = 370;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && pause == true) {
            pause = false;
            failed = false;
        }

        x = 335;
        y = 340;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && screenLevel == true) {
            screenLevel = false;
            startLevel = true;
            failed = false;
        }

        x = 265;
        y = 340;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && failed == true) {
            screenLevel = true;
            failed = false;
        }

        x = 290;
        y = 400;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && failed == true) {
            openingScreen = true;
            level = 1;
            failed = false;
        }
        x = 630;
        y = 270;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && failed == true) {
            openingScreen = true;
            level = 1;
            failed = false;
        }
        x = 400;
        y = 270;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && option == true) {
            level = 1;
            screenLevel = true;
            practiceLevel = false;
            option = false;
            failed = false;
        }
        x = 400;
        y = 320;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && option == true) {
            practiceLevel = true;
            option = false;
            level = 5;
            failed = false;
        }
        x = 400;
        y = 370;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && option == true) {
            openingScreen = true;
            practiceLevel = false;
            level = 5;
            option = false;
            failed = false;
        }
        x = 180;
        y = 250;

        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && practiceLevel == true) {
            level = 1;
            screenLevel = true;
            practiceLevel = false;
            failed = false;
        }
        x = 180;
        y = 300;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && practiceLevel == true) {
            level = 2;
            screenLevel = true;
            practiceLevel = false;
            failed = false;
        }
        x = 180;
        y = 350;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && practiceLevel == true) {
            level = 3;
            screenLevel = true;
            practiceLevel = false;
            failed = false;
        }
        x = 180;
        y = 400;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && practiceLevel == true) {
            level = 4;
            screenLevel = true;
            practiceLevel = false;
            failed = false;
        }

        x = 250;
        y = 500;
        if (me.getX() >= x && me.getX() <= x + 200 && me.getY() <= y && me.getY() >= y - 50 && finalScore == true) {
            openingScreen = true;
            practiceLevel = false;
            level = 5;
            option = false;
            failed = false;
            finalScore = false;
            screenLevel = false;
        }

    }
    
    /**
     * Methods i didn't use
     * @param me 
     */
    public void mouseEntered(MouseEvent me) {

    }

    public void mouseExited(MouseEvent me) {

    }

    public void mousePressed(MouseEvent me) {

    }

    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
