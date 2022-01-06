
package yogibear;


import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.ImageIcon;
/**
 *
 * @author shafa
 */
public class Level {

    private final int BASKET_WIDTH = 40;
    private final int BASKET_HEIGHT = 30;
    private final int TREE_WIDTH = 40;
    private final int TREE_HEIGHT = 30;
    private final int MOUNTAIN_WIDTH = 40;
    private final int MOUNTAIN_HEIGHT = 30;
    private final int RANGER_WIDTH = 40;
    private final int RANGER_HEIGHT = 50;
    ArrayList<Basket> baskets;
    ArrayList<Tree> trees;
    ArrayList<Mountain> mountains;
    ArrayList<Ranger> rangers;
    private int randomNum = 1;
    private int basketNumbers;

    public Level(String levelPath) throws IOException {
        loadLevel(levelPath);
    }

    public void loadLevel(String levelPath) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath));
        baskets = new ArrayList<>();
        trees = new ArrayList<>();
        mountains = new ArrayList<>();
        rangers = new ArrayList<>();
        basketNumbers = 0;
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                if (Character.isDigit(blockType)) {
                    int b = blockType;
                    if(b==49){
                        Image image = new ImageIcon("data/images/basket.png").getImage();
                        baskets.add(new Basket(x * BASKET_WIDTH, y * BASKET_HEIGHT, BASKET_WIDTH, BASKET_HEIGHT, image));
                    } else if(b==50){
                        Image image = new ImageIcon("data/images/tree.png").getImage();
                        trees.add(new Tree(x * TREE_WIDTH, y * TREE_HEIGHT, TREE_WIDTH, TREE_HEIGHT, image));
                    } else if(b==51){
                        Image image = new ImageIcon("data/images/mountain.png").getImage();
                        mountains.add(new Mountain(x * MOUNTAIN_WIDTH, y * MOUNTAIN_HEIGHT, MOUNTAIN_WIDTH, MOUNTAIN_HEIGHT, image));
                    } else if(b==52){
                        Image image = new ImageIcon("data/images/ranger.png").getImage();
                        rangers.add(new Ranger(x * RANGER_WIDTH, y * RANGER_HEIGHT, RANGER_WIDTH, RANGER_HEIGHT, image));
                    }
                }
                x++;
            }
            y++;
        }
    }

    public boolean collides(Bear bear) {
        boolean c = false;
        bear.move();
        Basket collidedWith = null;
        for (Basket basket : baskets) {
            if (bear.collides(basket)) {
                collidedWith = basket;
                break;
            }
        }
        if (collidedWith != null) {
            basketNumbers = 1;
            baskets.remove(collidedWith);
        } else {
            basketNumbers = 0;
        }
        Tree collidedWithT = null;
        for (Tree tree : trees) {
            if (bear.collides(tree)) {
                collidedWithT = tree;
                break;
            }
        }
        if (collidedWithT != null) {
            c = true;
        }
        Mountain collidedWithM = null;
        for (Mountain mountain : mountains) {
            if (bear.collides(mountain)) {
                collidedWithM = mountain;
                break;
            }
        }
        if (collidedWithM != null) {
            c = true;
        }
        bear.goBack();
        if(c){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean cathces(Bear bear){
        Ranger collidedWithR = null;
        for (Ranger ranger : rangers) {
            if (ranger.collides(bear)) {
                collidedWithR = ranger;
                break;
            }
        }
        if (collidedWithR != null) {
            return true;
        }
        return false;
    }
    
    public boolean collides(Ranger ranger) {
        Tree collidedWithT = null;
        for (Tree tree : trees) {
            if (ranger.collides(tree)) {
                collidedWithT = tree;
                break;
            }
        }
        if (collidedWithT != null) {
            return true;
        }
        Mountain collidedWithM = null;
        for (Mountain mountain : mountains) {
            if (ranger.collides(mountain)) {
                collidedWithM = mountain;
                break;
            }
        }
        if (collidedWithM != null) {
            return true;
        }
        Ranger collidedWithR = null;
        for (Ranger r : rangers) {
            if (!ranger.equals(r) && ranger.collides(r)) {
                collidedWithR = r;
                break;
            }
        }
        if (collidedWithR != null) {
            return true;
        }
        return false;
    }
    
    public void rangersMove() {
        for (Ranger ranger : rangers) {
            if(this.collides(ranger)){
                ranger.invertVel();
            }
            randomNum = -randomNum;
            ranger.setDirection(randomNum);
            ranger.move();
        }
        randomNum = 1;
    }
    
    public boolean isWinner() {
        return baskets.isEmpty();
    }

    public void draw(Graphics g) {
        for (Basket basket : baskets) {
            basket.draw(g);
        }
        for (Tree tree : trees) {
            tree.draw(g);
        }
        for (Mountain mountain : mountains) {
            mountain.draw(g);
        }
        for (Ranger ranger : rangers) {
            ranger.draw(g);
        }
    }
    
    public int getBasketNumbers(){
        return basketNumbers;
    }

}

