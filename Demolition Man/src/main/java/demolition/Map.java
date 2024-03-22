package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;

public class Map {

    // image
    private PImage solidWallImages;
    private PImage brokenWallImages;
    private PImage emptyWallImages;
    private PImage goalImages;

    // position
    private int x;
    private int y;
    public int goalX;
    public int goalY;
    private int startTop = 64;

    // map
    private String[][] map;
    private String[][] map1;
    private String[][] map2;

    // value
    private int currentLevel;
    private boolean gameOver;
    private boolean countP;

    public Map(String[][] map1,String[][] map2, PImage solidWallImages, PImage brokenWallImages, PImage emptyWallImages, PImage goalImages){
        this.map1 = map1;
        this.map2 = map2;
        this.solidWallImages = solidWallImages;
        this.brokenWallImages = brokenWallImages;
        this.emptyWallImages = emptyWallImages;
        this.goalImages = goalImages;
        this.currentLevel = 1;
        this.countP = true;
        this.map = new String[13][15];
        for (int i = 0; i < map.length; i++){
            for (int n = 0; n < map[i].length; n++){
                this.map[i][n] = map1[i][n];
                if (map[i][n].equals("G")){
                    this.goalX = n*32;
                    this.goalY = i*32 + startTop;
                }
            }
        }
    }

    public void tick(){
    }

    public void draw(PApplet app){
        if (this.gameOver == false){
            for (int i = 0; i < map.length; i++){
                for (int n = 0; n < map[i].length; n++){
                    if (map[i][n].equals("W")){
                        this.x = n*32;
                        this.y = i*32 + startTop;
                        app.image(this.solidWallImages, this.x, this.y);
                    }
                    else if (map[i][n].equals("B")){
                        this.x = n*32;
                        this.y = i*32 + startTop;
                        app.image(this.brokenWallImages, this.x, this.y);
                    }
                    else if (map[i][n].equals("G")){
                        this.x = n*32;
                        this.y = i*32 + startTop;
                    }
                    else{
                        this.x = n*32;
                        this.y = i*32 + startTop;
                        app.image(this.emptyWallImages, this.x, this.y);
                    }
                }
            }
            app.image(this.goalImages, this.goalX, this.goalY);
        }
    }

    public void setMap(String str, int OrgX, int OrgY, int setX, int setY){
        this.map[OrgX][OrgY] = " ";
        this.map[setX][setY] = str;
    }
        
    public String[][] getMap(){
        return this.map;
    }

    public int getLevel(){
        return this.currentLevel;
    }

    public void removeMap(Player player, RedEnemy redEnemy, YellowEnemy yellowEnemy,String str, int OrgX, int OrgY){
        if (str.equals("R") && this.map[OrgX][OrgY].equals("R")){
            redEnemy.notAlive();
        }
        if (str.equals("Y") && this.map[OrgX][OrgY].equals("Y")){
            yellowEnemy.notAlive();
        }
        if (str.equals("P") && this.map[OrgX][OrgY].equals("P")){
            player.notAlive();
        }
        this.map[OrgX][OrgY] = " ";
    }

    public void changeMap(){
        this.currentLevel = 2;
        for (int i = 0; i < map.length; i++){
            for (int n = 0; n < map[i].length; n++){
                this.map[i][n] = this.map2[i][n];
                if (map[i][n].equals("G")){
                    this.goalX = n*32;
                    this.goalY = i*32 + startTop;
                }
            }
        }
    }

    public void reSet(){
        if (currentLevel == 1){
            for (int i = 0; i < map.length; i++){
                for (int n = 0; n < map[i].length; n++){
                    this.map[i][n] = this.map1[i][n];
                }
            }
        }
        else if (currentLevel == 2){
            for (int i = 0; i < map.length; i++){
                for (int n = 0; n < map[i].length; n++){
                    this.map[i][n] = this.map2[i][n];
                }
            }
        }
    }

}
