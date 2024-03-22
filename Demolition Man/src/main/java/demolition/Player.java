package demolition;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Player{

    // position value
    private int x;
    private int y;
    private int col;
    private int row;

    // Images Value
    private PImage[][] playerImages;
    private int currentDirection;
    private int currentSprite;
    private int loopValue;

    // Player Alive
    public boolean alive;
    private int liveNum;
    private boolean playerOver;
    private boolean playerWin;

    // map
    private String[][] currentMap;
    private int currentLevel;
    
    public Player(PImage[][] playerImages,int lives){
        this.playerImages = playerImages;
        this.alive = true;
        this.currentDirection = 1;
        this.liveNum = lives;
        this.playerOver = false;
        this.playerWin = false;
    }

    public void tick(Map map, Icons icons){
        // movement logic
        this.currentMap = map.getMap();
        for (int i = 0; i < this.currentMap.length; i++){
            for (int n = 0; n < this.currentMap[i].length; n++){
                if (this.currentMap[i][n].equals("P")){
                    this.x = n*32;
                    this.y = i*32 + 48;
                    row = i;
                    col = n;
                    break;
                }
            }
        }
        this.currentLevel = map.getLevel();
        checkAlive(map, icons);
        checkWin(map, icons);
    }

    public void draw(PApplet app){
        if (this.alive == true){
            currentSprite = loopValue/12;
            if (currentSprite >= 4){
                currentSprite = 0;
                loopValue = 0;
            }
            app.image(this.playerImages[currentDirection][currentSprite], this.x, this.y);
            loopValue ++;
        }
    }


    public void move(String direction, Map map){
        if (this.alive == true){ 
            if (direction == "left"){
                if (this.checkMove(row,col-1) == true){
                    map.setMap("P",row,col,row,(col-1));
                }
                this.currentDirection = 2;
            }
            if (direction == "right"){
                if (this.checkMove(row,col+1) == true){
                    map.setMap("P",row,col,row,col+1);
                }
                this.currentDirection = 3;
            }
            if (direction == "up"){
                if (this.checkMove(row-1,col) == true){
                    map.setMap("P",row,col,row-1,col);  
                }
                this.currentDirection = 0;
            }
            if (direction == "down"){
                if (this.checkMove(row+1,col) == true){
                    map.setMap("P",row,col,row+1,col);  
                }  
                this.currentDirection = 1;
            }
        }
    }

    public boolean checkMove(int a, int b){
        if (this.currentMap[a][b].equals("W") || this.currentMap[a][b].equals("B")){
            return false;
        }
        else{
            return true;
        }
    }

    public Bomb placeBomb(PImage[] bombImages,PImage[] explosionImages){
        Bomb newBomb = new Bomb(bombImages,explosionImages);
        newBomb.getXY(this.col,this.row);
        return newBomb;
    }   

    public int getLives(){
        return this.liveNum;
    }

    public boolean getOver(){
        return this.playerOver;
    }

    public boolean getWin(){
        return this.playerWin;
    }

    public void setOver(){
        this.playerOver = true;
    }

    public void notAlive(){
        this.alive = false;
    }

    public void checkAlive(Map map, Icons icons){
        if (this.alive == false && this.liveNum > 0){
            map.reSet();
            icons.reset();
            this.liveNum --;
            if (map.getLevel() == 1){
                this.currentDirection = 1;
            }
            else{
                this.currentDirection = 2;
            }
            this.alive = true;
        }
        else if (this.liveNum == 0){
            this.setOver();
        }
    }

    public void checkWin(Map map, Icons icons){
        if (this.currentLevel == 2 && this.x == map.goalX && this.y == map.goalY-16){
            this.playerWin = true;
        }
        else if(this.currentLevel == 1 && this.x == map.goalX && this.y == map.goalY-16){
            map.changeMap();
            icons.change();
            this.playerWin = false;
        }
        else{
            this.playerWin = false;
        }
    }

}

