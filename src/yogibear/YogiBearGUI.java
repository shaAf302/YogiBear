/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yogibear;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author shafa
 */
public class YogiBearGUI {
    private JFrame frame;
    private GameEngine gameArea;

    public YogiBearGUI() {
        frame = new JFrame("Yogi Bear");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuBar.add(menuGame);
        JMenuItem newMenu = new JMenuItem("New Game");
        menuGame.add(newMenu);
        newMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameArea.setAllBasketCollected(0);
                gameArea.setLevelNum(0);
                gameArea.restart();
            }
        });
        
        JMenuItem highScores = new JMenuItem("High scores");
        menuGame.add(highScores);
       
        highScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    HighScores highScores = new HighScores(10);
                    ArrayList<HighScore> topScores = highScores.getHighScores();
                    String ranking = "";
                    for(int i = 0; i < topScores.size(); i++){
                        ranking += String.valueOf(i+1)+ ". " + topScores.get(i).toString() + "\n";
                    }
                    JOptionPane.showMessageDialog(gameArea, ranking, "Best 10 scores", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException ex) {
                    Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }); 
        
        frame.setJMenuBar(menuBar);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
    
}

