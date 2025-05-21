package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class GamePanel extends JPanel implements Runnable{
    
    //SCREEN SETTINGS
    final int originalTitleSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTitleSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixel
    final int screenHeight = tileSize * maxScreenRow; // 576 pixel

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's Default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 3;


    public GamePanel(){

        this.setPreferredSize(new DimensionUIResource(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            

            // 1 UPDATE: update information such as character position
            update();
            // 2 DRAW: draw the screen with the update infomation
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void update(){

        if(keyH.upPressed == true) {
            playerY -= playerSpeed;
            if(keyH.leftPressed == true){
                playerX -= playerSpeed/2;
                playerY += playerSpeed/2;
            }else if(keyH.rigthPressed == true){
                playerX += playerSpeed/2;
                playerY += playerSpeed/2;
            }
        }else if(keyH.downPressed == true){
            playerY += playerSpeed;
            if(keyH.leftPressed == true){
            	playerX -= playerSpeed/2;
                playerY -= playerSpeed/2;
            }else if(keyH.rigthPressed == true){
            	playerX += playerSpeed/2;
                playerY -= playerSpeed/2;
            }
        }else if(keyH.rigthPressed == true){
            playerX += playerSpeed;
            if(keyH.upPressed == true){
            	playerX += playerSpeed/2;
                playerY += playerSpeed/2;
            }else if(keyH.downPressed == true){
            	playerX += playerSpeed/2;
                playerY -= playerSpeed/2;
            }
        }else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
            if(keyH.upPressed == true){
            	playerX -= playerSpeed/2;
                playerY += playerSpeed/2;
            }else if(keyH.downPressed == true){
            	playerX -= playerSpeed/2;
                playerY -= playerSpeed/2;
            }
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
