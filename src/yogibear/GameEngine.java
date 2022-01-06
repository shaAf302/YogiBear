
package yogibear;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


/**
 *
 * @author shafa
 */
public class GameEngine extends JPanel {

    private final int FPS = 240;
    private final int BEAR_WIDTH = 50;
    private final int BEAR_HEIGHT = 50;
    private final int BEAR_MOVEMENT = 2;

    private boolean paused = false;
    private Image background;
    private int levelNum = 0;
    private Level level;
    private Bear bear;
    private Timer newFrameTimer;
    private int lives = 3;
    private int allBasketCollected;

    public GameEngine() {
        super();
        allBasketCollected = 0;
        background = new ImageIcon("data/images/background.png").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                bear.setVelx(-BEAR_MOVEMENT);
                bear.setVely(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                bear.setVelx(BEAR_MOVEMENT);
                bear.setVely(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                bear.setVely(BEAR_MOVEMENT);
                bear.setVelx(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                bear.setVely(-BEAR_MOVEMENT);
                bear.setVelx(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener(this));
        newFrameTimer.start();
    }

    public void restart() {
        try {
            lives = 3;
            level = new Level("data/levels/level0" + levelNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image bearImage = new ImageIcon("data/images/bear.png").getImage();
        bear = new Bear(0, 0, BEAR_WIDTH, BEAR_HEIGHT, bearImage);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        level.draw(grphcs);
        bear.draw(grphcs);
    }

    class NewFrameListener implements ActionListener {
        public JPanel panel;
        
        NewFrameListener(JPanel panel) {
            this.panel = panel;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                level.rangersMove();
                if (level.collides(bear)) {
                    bear.setVelx(0);
                    bear.setVely(0);
                }
                allBasketCollected += level.getBasketNumbers();
                bear.move();
                if(level.cathces(bear)){
                    lives--;
                    JOptionPane.showMessageDialog(panel, "You are caught. You have " + lives + " lives left","Warning", JOptionPane.PLAIN_MESSAGE);
                    if(lives <= 0){
                        paused = true;
                        String playerName;
                        playerName = JOptionPane.showInputDialog("You achieved " + allBasketCollected + " points. Enter your name:");
                        if(playerName == null){
                            playerName = "undefined";
                        }
                        try {
                            HighScores highScores = new HighScores(10);
                            highScores.putHighScore(playerName,allBasketCollected);
                        } catch (SQLException ex) {
                            Logger.getLogger(HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        JOptionPane.showMessageDialog(panel, "A new game is starting","Warning", JOptionPane.PLAIN_MESSAGE);
                        levelNum = 0;
                        allBasketCollected = 0;
                        paused = false;
                        restart();
                    } else {
                        Image bearImage = new ImageIcon("data/images/bear.png").getImage();
                        bear = new Bear(0, 0, BEAR_WIDTH, BEAR_HEIGHT, bearImage);
                    }
                }
            }
            if (level.isWinner()) {
                levelNum = (levelNum+1) % 10;
                JOptionPane.showMessageDialog(panel, "Next level:  " + (levelNum+1), "You won this level", JOptionPane.PLAIN_MESSAGE);
                restart();
            }
            repaint();
        }
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public void setAllBasketCollected(int allBasketCollected) {
        this.allBasketCollected = allBasketCollected;
    }
    
}
