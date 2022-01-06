/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yogibear;

import java.awt.Image;

/**
 *
 * @author shafa
 */
public class Ranger extends Sprite{
    private double velx;
    private double vely;
    private int direction;

    public Ranger(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        velx = 1;
        vely = 1;
    }

    public void moveX() {
        x += velx;
        if (x + width >= 785 || x <= 0) {
            invertVelX();
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public void move(){
        if(this.direction == 1){
            this.moveX();
        } else { 
            this.moveY();
        }
    }
    
    public void invertVel(){
        if(this.direction == 1){
            this.invertVelX();
        } else { 
            this.invertVelY();
        }
    }

    public void moveY() {
        y += vely;
        if (y <= 0 || y + height >= 540) {
            invertVelY();
        }
    }

    public void invertVelX() {
        velx = -velx;
    }

    public void invertVelY() {
        vely = -vely;

    }
}
