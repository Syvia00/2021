package demolition;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    //Image 
    private PImage[][] playerImages;
    private PImage[][] redEnemyImages;
    private PImage[][] yellowEnemyImages;
    private PImage emptyWallImages;
    private PImage solidWallImages;
    private PImage brokenWallImages;
    private PImage[] bombImages;
    private PImage[] explosionImages;
    private PImage goalImages;
    private PImage clocksImages;
    private PImage playerLivesImages;


    // map
    private String path1;
    private int time1;
    private String path2; 
    private int time2;
    private int lives;
    private String[][] map1;
    private String[][] map2;

    // check value
    public boolean checkOver;
    public boolean checkWin;
    private String dirStr;

    //classes objects
    public Player player;
    public RedEnemy redEnemy;
    public YellowEnemy yellowEnemy;
    public ArrayList<Bomb> bomb = new ArrayList<>();
    public Icons icons;
    public Map map;
    public GameOver gameOver;
    public GameWin gameWin;


    //keyboard
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean space;

    public App() {
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);


        ////////////////////////////////
        //** */ Loading file
        // Load Json file during setup
        JSONObject jsonFile = loadJSONObject("config.json");
        
        // get the JsonArray of levels
        JSONArray items = jsonFile.getJSONArray("levels");
        
        //level 1
        JSONObject firstpath = items.getJSONObject(items.size()-2);
        this.path1 = firstpath.getString("path");
        this.time1 = firstpath.getInt("time");
        
        // level2
        JSONObject secondpath = items.getJSONObject(items.size()-1);
        this.path2 = secondpath.getString("path");
        this.time2 = secondpath.getInt("time");
        
        // get the string of lives number
        this.lives = jsonFile.getInt("lives");
        
        // reading level1 path
        this.map1 = this.readFile(this.path1);

        // reading level2 path
        this.map2 = this.readFile(this.path2);



        ////////////////////////////////
        //** */ Load images during setup

        // Loading Images of wall on map
        this.solidWallImages = this.loadImage("src/main/resources/wall/solid.png");
        this.brokenWallImages = this.loadImage("src/main/resources/broken/broken.png");
        this.emptyWallImages= this.loadImage("src/main/resources/empty/empty.png");
        // Loading Image of the goal on map
        this.goalImages = this.loadImage("src/main/resources/goal/goal.png");

        
        // Loading Images of Player
        this.playerImages = new PImage[4][4];
        // direction is up  
        for (int num = 0; num < 4; num ++){
            this.playerImages[0][num] = this.loadImage("src/main/resources/player/player_up" + (num+1) + ".png");
        }
        // direction is down
        for (int num = 0; num < 4; num ++){
            this.playerImages[1][num] = this.loadImage("src/main/resources/player/player" + (num+1) + ".png");
        }
        // direction is left  
        for (int num = 0; num < 4; num ++){
            this.playerImages[2][num] = this.loadImage("src/main/resources/player/player_left" + (num+1) + ".png");
        }
        // direction is right  
        for (int num = 0; num < 4; num ++){
            this.playerImages[3][num] = this.loadImage("src/main/resources/player/player_right" + (num+1) + ".png");
        }

        
        // Loading Images of Red Enemy
        this.redEnemyImages = new PImage[4][4];
        // direction is up  
        for (int num = 0; num < 4; num ++){
            this.redEnemyImages[0][num] = this.loadImage("src/main/resources/red_enemy/red_up" + (num+1) + ".png");
        }
        // direction is down
        for (int num = 0; num < 4; num ++){
            this.redEnemyImages[1][num] = this.loadImage("src/main/resources/red_enemy/red_down" + (num+1) + ".png");
        }
        // direction is left  
        for (int num = 0; num < 4; num ++){
            this.redEnemyImages[2][num] = this.loadImage("src/main/resources/red_enemy/red_left" + (num+1) + ".png");
        }
        // direction is right  
        for (int num = 0; num < 4; num ++){
            this.redEnemyImages[3][num] = this.loadImage("src/main/resources/red_enemy/red_right" + (num+1) + ".png");
        }

        
        // Loading Images of Yellow Enemy
        this.yellowEnemyImages = new PImage[4][4];
        // direction is up  
        for (int num = 0; num < 4; num ++){
            this.yellowEnemyImages[0][num] = this.loadImage("src/main/resources/yellow_enemy/yellow_up" + (num+1) + ".png");
        }
        // direction is down
        for (int num = 0; num < 4; num ++){
            this.yellowEnemyImages[1][num] = this.loadImage("src/main/resources/yellow_enemy/yellow_down" + (num+1) + ".png");
        }
        // direction is left  
        for (int num = 0; num < 4; num ++){
            this.yellowEnemyImages[2][num] = this.loadImage("src/main/resources/yellow_enemy/yellow_left" + (num+1) + ".png");
        }
        // direction is right  
        for (int num = 0; num < 4; num ++){
            this.yellowEnemyImages[3][num] = this.loadImage("src/main/resources/yellow_enemy/yellow_right" + (num+1) + ".png");
        }

        
        // Loading Images of the bomb
        this.bombImages = new PImage[8];
        for (int num = 0; num < 8; num ++){
            this.bombImages[num] = this.loadImage("src/main/resources/bomb/bomb" + (num+1) + ".png");
        }
        

        // Loading Images of the explosion
        this.explosionImages = new PImage[7];
        this.explosionImages[0] = this.loadImage("src/main/resources/explosion/centre.png");
        this.explosionImages[1] = this.loadImage("src/main/resources/explosion/end_bottom.png");
        this.explosionImages[2] = this.loadImage("src/main/resources/explosion/end_left.png");
        this.explosionImages[3] = this.loadImage("src/main/resources/explosion/end_right.png");
        this.explosionImages[4] = this.loadImage("src/main/resources/explosion/end_top.png");
        this.explosionImages[5] = this.loadImage("src/main/resources/explosion/horizontal.png");
        this.explosionImages[6] = this.loadImage("src/main/resources/explosion/vertical.png");


        // Loading Images of the icons
        this.clocksImages = this.loadImage("src/main/resources/icons/clock.png");
        this.playerLivesImages = this.loadImage("src/main/resources/icons/player.png");

        

        ////////////////////////////////
        //** */ create Object
        //Initial map in Level1
        this.map =  new Map(this.map1, this.map2,this.solidWallImages,this.brokenWallImages,this.emptyWallImages,this.goalImages);
        this.player = new Player(this.playerImages,this.lives);
        this.redEnemy = new RedEnemy(this.redEnemyImages);
        this.yellowEnemy = new YellowEnemy(this.yellowEnemyImages);
        this.icons = new Icons(this.clocksImages,this.playerLivesImages,this.time1,this.time2);
        this.gameOver = new GameOver();
        this.gameWin = new GameWin();

        
        // start keypress
        this.left = false;
        this.up = false;
        this.right = false;
        this.down = false;
        this.space = false;

        // initial gameover and gamewin
        this.checkOver = false;
        this.checkWin = false;
        
    }

    public void draw() {
        background(239,129,0);
        if (this.checkOver == true){
            this.gameOver.tick();
            this.gameOver.draw(this);
        }
        else if(this.checkWin == true){
            this.gameWin.tick();
            this.gameWin.draw(this);
        }
        else{
            this.map.tick();
            this.map.draw(this);
            this.icons.tick(this.player,this.map);
            this.icons.draw(this);
            this.player.tick(this.map,this.icons);
            this.player.draw(this);
            this.redEnemy.tick(this.map,this.player);
            this.redEnemy.draw(this);
            this.yellowEnemy.tick(this.map,this.player);
            this.yellowEnemy.draw(this);
            for (Bomb b : this.bomb){
                b.tick();
                b.draw(this,this.map,this.player,this.redEnemy,this.yellowEnemy);
            }
            if (keyPressed){
                if (this.left == true){
                    this.player.move("left",this.map);
                    this.left = false;
                }
                if (this.up == true){
                    this.player.move("up",this.map);
                    this.up = false;
                }
                if (this.right == true){
                    this.player.move("right",this.map);
                    this.right = false;
                }
                if (this.down == true){
                    this.player.move("down",this.map);
                    this.down = false;
                }
                if (this.space == true){
                    this.bomb.add(this.player.placeBomb(this.bombImages,this.explosionImages));
                    this.space = false;
                }
            }
            this.checkWin = this.player.getWin();
            this.checkOver = this.player.getOver();
        }
    }

    public void keyPressed(){
        if (keyCode == 37){
            this.left = true;
        }
        if (keyCode == 38){
            this.up = true;
        }
        if (keyCode == 39){
            this.right = true;
        }
        if (keyCode == 40){
            this.down = true;
        }
        if (keyCode == 32){
            this.space = true;
        }
    }

    public void keyReleased(){
        if (keyCode == 37){
            this.left = false;
        }
        if (keyCode == 38){
            this.up = false;
        }
        if (keyCode == 39){
            this.right = false;
        }
        if (keyCode == 40){
            this.down = false;
        }
        if (keyCode == 32){
            this.space = false;
        }
    }


    public String[][] readFile(String pathName){
        try {
            File fileLevel = new File(pathName);
            Scanner scan = new Scanner(fileLevel);
            int numRow = 0;
            // Initial the map array
            String[][] map = new String[13][15];
            while (scan.hasNextLine()) {
                String fileLine = scan.nextLine();
                String[] str = fileLine.split("");
                for(int i = 0; i < str.length; i++) {
                    map[numRow][i] = str[i];
                }
                numRow ++;
            }
            scan.close();
            return map;
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
