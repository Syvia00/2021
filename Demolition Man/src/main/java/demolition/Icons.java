package demolition;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Icons {
    
    private PImage clocksImages;
    private PImage playerLivesImages;
    private int currentTime;
    private int loopValue;
    private int countLives;
    private int time1;
    private int time2;
    private int currentLevel;
    
    public Icons(PImage clocksImages,PImage playerLivesImages,int time1, int time2){
        this.clocksImages = clocksImages;
        this.playerLivesImages = playerLivesImages;
        this.time1 = time1;
        this.time2 = time2;
        this.currentTime = time1;
    }

    public void tick(Player player, Map map){
        countLives = player.getLives();
        this.checkTime(player);
        this.currentLevel = map.getLevel();
    }

    public void draw(PApplet app){
        
        if (loopValue == 60){
            loopValue = 0;
        }
        else if (loopValue == 59){
            currentTime --;
        }

        app.image(this.clocksImages, 256, 20);
        app.image(this.playerLivesImages, 128, 20);
        
        PFont font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        app.textFont(font);
        app.fill(0);
        app.text(currentTime, 296, 48);
        app.text(countLives, 168, 48);
        
        loopValue ++;
    }

    public void checkTime(Player player){
        if (currentTime == 0){
            player.setOver();
        }
    }

    public void reset(){
        if (this.currentLevel == 1){
            this.currentTime = time1;
        }
        if (this.currentLevel == 2){
            this.currentTime = time2;
        }
    }

    public void change(){
        this.currentTime  = this.time2;
    }
}
