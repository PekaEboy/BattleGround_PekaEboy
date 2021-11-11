package indie.pekaeboy.october2;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Game extends Canvas implements Runnable {
    public boolean running = false;
    private static int Height = 600;
    private static int Width = Height * 7 / 5;
    public int mouseX = 0, mouseY = 0;
    public int[] x = {-30, -90, -120};
    public int y = Height/2-15;
    public int[] bx = new int[6];
    public int[] by = new int[6];
    public boolean[] bclicked = new boolean[6];
    public int[] health = {100, 1000, 200};
    public int[] areaX = new int[6];
    public int[] areaY = new int[6];
    public int coin = 0;
    public int[] level = new int[6];
    public int wave = 1;
    public BufferedImage texture0;
    public boolean[] isDead = new boolean[3];
    public int newatime = 0;
    public boolean Esc = false;
    public byte a = 0;
    public boolean startGame = true;
    public int TL = 30;
    public String[] subline = {"Hi my friend:)", "It's fun", "Made by Java", "@ozkriff is good men", "by @EboyPeka", "Codename: October2", "Also play KickRedBall", "Try TRASH INVASION by @DifferentWayGam", "Привет", "Why big number of Rustlang-dev like blockchain", "How i've made, @peterklogborg?", "@patching_irl is good guy too", "Source is available in Github"};
    public int number = (int)(Math.random()*subline.length);
    public boolean Gameover = false;
    public void start() {
        running = true;
        new Thread(this).start();
    }
    public void run() {
        addMouseListener(new MouseInputHandle());
        addKeyListener(new KeyInputHandle());
        for(int i = 0; i<6; i++) {
            bx[i] = 200+200*(i%3);
            by[i] = Height-500+300*(i/3);
            bclicked[i] = false;
            areaX[i] = bx[i]+50;
            areaY[i] = by[i]+50;
            level[i] = 1;
        }
        try {
            texture0 = ImageIO.read(this.getClass().getResource("Texture0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isDead[0] = false;
        isDead[1] = true;
        isDead[2] = true;
        while(running) {
            if(!Esc) {
                update();
            }
            render();
        }
    }
    public void update() {
        if (startGame || Gameover) {
            if((mouseX>Width/2-96) && (mouseX<Width/2+96) && (mouseY>Height/2-48) && (mouseY<Height/2+48)) {
                startGame = false;
                Gameover = false;
                coin=0;
                wave=0;
                for(int i = 0; i<3; i++) {
                    isDead[i]=true;
                }
                for(int i = 0; i<6; i++) {
                    level[i]=1;
                    bclicked[i] = false;
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                if (x[i] > Width + 30) {
                    Gameover = true;
                    TL = 30;
                }
            }

            if ((isDead[0] == isDead[1]) && (isDead[1] == isDead[2]) && (isDead[2] == true)) {
                wave++;
                newatime = 100;
                if (wave > 9) {
                    isDead[0] = false;
                    isDead[1] = false;
                    isDead[2] = false;
                } else if ((9 >= wave) && (wave > 5)) {
                    isDead[0] = false;
                    isDead[1] = false;
                } else {
                    isDead[0] = false;
                }
                x[0] = -30;
                x[1] = -90;
                x[2] = -150;
                switch (wave) {
                    case 1:
                        health[0] = 100;
                        break;
                    case 2:
                        health[0] = 250;
                        break;
                    case 3:
                        health[0] = 500;
                        break;
                    case 4:
                        health[0] = 750;
                        break;
                    case 5:
                        health[0] = 1500;
                        break;
                    case 6:
                        health[0] = 2500;
                        break;
                    case 7:
                        health[0] = 2750;
                        health[1] = 1250;
                        break;
                    case 8:
                        health[0] = 3750;
                        health[1] = 2500;
                        break;
                    case 9:
                        health[0] = 4500;
                        health[1] = 3000;
                        break;
                    case 10:
                        health[0] = 5000;
                        health[1] = 4000;
                        health[2] = 3000;
                        break;
                    case 11:
                        System.exit(0);
                }
                coin += 1000;
            }
            for (int i = 0; i < 3; i++) {
                if (health[i] <= 0) {
                    isDead[i] = true;
                    x[i] = -100;
                }
                if (!(isDead[i])) {
                    x[i]++;
                }
            }
            for (int i = 0; i < 6; i++) {
                if ((mouseX > bx[i]) && (mouseX < bx[i] + 100) && (mouseY > by[i]) && (mouseY < by[i] + 100) && (coin >= 1000)) {
                    if (!bclicked[i]) {
                        bclicked[i] = true;
                        coin -= 1000;
                    } else if ((level[i] < 3) && (coin >= 1000 + 500 * level[i])) {
                        coin -= 1000 + 500 * level[i];
                        level[i]++;
                    }

                }
                if ((bclicked[i]) && ((((x[0] - areaX[i]) * (x[0] - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[0] + 30 - areaX[i]) * (x[0] + 30 - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[0] - areaX[i]) * (x[0] - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)) ||
                        (((x[0] + 30 - areaX[i]) * (x[0] + 30 - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)))) {
                    switch (level[i]) {
                        case 1:
                            health[0]--;
                            coin++;
                            break;
                        case 2:
                            health[0] -= 2;
                            coin += 2;
                            break;
                        case 3:
                            health[0] -= 5;
                            coin += 5;
                            break;
                    }
                } else if ((bclicked[i]) && ((((x[1] - areaX[i]) * (x[1] - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[1] + 30 - areaX[i]) * (x[1] + 30 - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[1] - areaX[i]) * (x[1] - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)) ||
                        (((x[1] + 30 - areaX[i]) * (x[1] + 30 - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)))) {
                    switch (level[i]) {
                        case 1:
                            health[1]--;
                            coin++;
                            break;
                        case 2:
                            health[1] -= 2;
                            coin += 2;
                            break;
                        case 3:
                            health[1] -= 5;
                            coin += 5;
                            break;
                    }
                } else if ((bclicked[i]) && ((((x[2] - areaX[i]) * (x[2] - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[2] + 30 - areaX[i]) * (x[2] + 30 - areaX[i]) + (y - areaY[i]) * (y - areaY[i]) <= 40000)) ||
                        (((x[2] - areaX[i]) * (x[2] - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)) ||
                        (((x[2] + 30 - areaX[i]) * (x[2] + 30 - areaX[i]) + (y + 30 - areaY[i]) * (y + 30 - areaY[i]) <= 40000)))) {
                    switch (level[i]) {
                        case 1:
                            health[2]--;
                            coin++;
                            break;
                        case 2:
                            health[2] -= 2;
                            coin += 2;
                            break;
                        case 3:
                            health[2] -= 5;
                            coin += 5;
                            break;
                    }
                }
            }
            mouseX = 0;
            mouseY = 0;
        }
    }
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(texture0, 0, 0, Width, Height, 116, 156, 256, 256, this);
        for(int i = 0; i<3; i++) {
            g.drawImage(texture0, x[i], y, x[i]+30, y+30, 0, 32, 16, 48, this);
        }
        for(int i = 0; i<6; i++) {
            if(!bclicked[i]) {
                g.drawImage(texture0, bx[i], by[i], bx[i]+100, by[i]+100,96, 0, 128, 32, this);
            } else {
                switch (level[i]) {
                    case 1:
                        if(i<3) {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 0, 0, 32, 32, this);
                        } else {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 128, 0, 160, 32, this);
                        }
                        break;
                    case 2:
                        if(i<3) {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 32, 0, 64, 32, this);
                        } else {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 160, 0, 192, 32, this);
                        }
                        break;
                    case 3:
                        if(i<3) {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 64, 0, 96, 32, this);
                        } else {
                            g.drawImage(texture0, bx[i], by[i], bx[i] + 100, by[i] + 100, 192, 0, 224, 32, this);
                        }
                        break;
                }
            }
        }
        g.setColor(Color.BLACK);
        for(int i = 0; i<3; i++) {
            g.drawString(String.valueOf(health[i]), x[i], y - 10);
        }
        g.drawString("Money: " + String.valueOf(coin), 0, 10);
        g.drawString("Wave: " + String.valueOf(wave),0,20);
        for(int i = 0; i<6; i++) {
            if((coin >= 1000 + level[i]*500) && (bclicked[i]) && (level[i]<3)) {
                g.drawImage(texture0, bx[i] + 110, by[i], bx[i] + 142, by[i] + 32, 16, 32, 32, 48, this);
            }
        }
        if((startGame) || Gameover || (TL>0)) {
            if(TL==30) {
                g.drawImage(texture0, 0, 0, Width, Height, 32, 96, 48, 112, this);
                if(!Gameover) g.drawImage(texture0, Width / 2 - 96, Height / 2 - 48, Width / 2 + 96, Height / 2 + 48, 0, 96, 32, 112, this);
                else g.drawImage(texture0, Width / 2 - 96, Height / 2 - 48, Width / 2 + 96, Height / 2 + 48, 0, 112, 32, 128, this);
                if(!Gameover) g.drawImage(texture0, Width / 2 - 139 - 139, 10, Width / 2 + 139 + 139, 62 + 52, 0, 64, 139, 90, this);
                g.setColor(Color.BLACK);
                if(!Gameover) g.drawString(subline[number], Width / 2 - 139 - 139, 62 + 52+10);
                g.setColor(Color.WHITE);
            }
            else if((30>TL) && (TL>=20)) g.drawImage(texture0, 0, 0, Width, Height, 32, 96, 48, 112, this);
            else if((20>TL) && (TL>=10)) g.drawImage(texture0, 0, 0, Width, Height, 48, 96, 64, 112, this);
            else if((10>TL) && (TL>0)) g.drawImage(texture0, 0, 0, Width, Height, 64, 96, 80, 112, this);
            if(!startGame && !Gameover) TL--;
        }
        if(newatime>0 && !Gameover) {
            g.drawImage(texture0, Width / 2 - 144, Height / 2 - 32, Width / 2 + 144, Height / 2 + 32, 32, 32, 176, 64, this);
            if(!Esc) newatime--;
        }
        g.dispose();
        bs.show();
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(Width, Height));

        JFrame frame = new JFrame("BattleGround");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }
    public class MouseInputHandle extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    public class KeyInputHandle extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if((e.getKeyCode()==KeyEvent.VK_ESCAPE) && (1>a)) {
                Esc = !Esc;
                a++;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                a--;
            }
        }
    }
}