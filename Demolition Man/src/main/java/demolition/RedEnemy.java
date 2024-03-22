package demolition;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class RedEnemy extends Enemy{
    
    
    public RedEnemy(PImage[][] redEnemyImages){
        super(redEnemyImages);
        this.currentDirection = 3;
        this.direction = "right";
        this.enemyType = "R";
    }
    
    public String changeDir(){
        Random rand = new Random();
        int newNum = rand.nextInt(4);
        if (newNum == 0){
            return "up";
        }
        else if (newNum == 1){
            return "down";
        }
        else if (newNum == 2){
            return "left";
        }
        else{
            return "right";
        }
    }

}