package demolition;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class YellowEnemy extends Enemy{
    
    public YellowEnemy(PImage[][] YellowEnemyImages){
        super(YellowEnemyImages);
        this.alive = true;
        this.currentDirection = 1;
        this.direction = "down";
        this.enemyType = "Y";
    }
    
    public String changeDir(){
        if (this.direction.equals("left")){
            return "up";
        }
        else if (this.direction.equals("right")){
            return "down";
        }
        else if (this.direction.equals("up")){
            return "right";
        }
        else{
            return "left";
        }
    }


}
