
package yogibear;

import java.awt.Image;

/**
 *
 * @author shafa
 */
public class Bear extends Sprite{
    private double velx;
    private double vely;
    
    public Bear(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }
    
    public void move(){
        moveX();
        moveY();
    }
    
    public void moveX() {
        if ((velx < 0 && x > 0) || (velx > 0 && x + width <= 785)) {
            x += velx;
        }
    }
    public void moveY(){
        if ((vely < 0 && y > 0) || (vely > 0 && y + height <= 540/*600,740*/)) {
            y += vely;
        }
    }
    
    public void goBack() {
        x -= velx;
        y -= vely;
    }

    public double getVelx() {
        return velx;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }
    
    public double getVely() {
        return vely;
    }

    public void setVely(double vely) {
        this.vely = vely;
    }
}

