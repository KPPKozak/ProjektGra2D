import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Ustawienia Ekranu
    final int orgTileSize = 16;  //16x16
    final int scale = 3;

    final int tileSize = orgTileSize * scale;  //48x48
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenColumn;  //768 pixels
    final int screenHeight = tileSize * maxScreenRow;    //576 pixels

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    //Domyślna pozycja gracza
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    int FPS = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;

        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/drawInterval;
            timer  += (currentTime-lastTime);
            lastTime = currentTime;
            //Ograniczenie do 60 FPS
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            //Wyświetlanie FPS
            if(timer>=1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if(keyHandler.upPressed==true){
            playerY -= playerSpeed;
        }
        else if(keyHandler.downPressed==true){
            playerY += playerSpeed;
        }
        else if(keyHandler.leftPressed==true){
            playerX -= playerSpeed;
        }
        else if(keyHandler.rightPressed==true){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.white);

        graphics2D.fillRect(playerX,playerY,tileSize,tileSize);

        graphics2D.dispose();
    }
}
