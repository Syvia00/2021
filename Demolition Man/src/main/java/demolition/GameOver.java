package demolition;

import processing.core.PApplet;
import processing.core.PFont;

public class GameOver{

    
    public GameOver(){
    }

    public void tick(){
    }

    public void draw(PApplet app){
        PFont font = app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        app.textFont(font);
        app.fill(0);
        app.text("Game Over", 160, 240);
    }
    
}
