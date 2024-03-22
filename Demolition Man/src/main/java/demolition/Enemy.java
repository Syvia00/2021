package demolition;


import processing.core.PImage; 
import processing.core.PApplet; 

public abstract class Enemy {
    
    // position value
    protected int x;
    protected int y;
    protected int col;
    protected int row;


    // Images Value
    protected int currentDirection;
    protected int currentSprite;
    protected PImage[][] sprite;
    
    // Enemey Alive
    protected boolean alive;

    // Map
    protected String[][] currentMap;
    protected String enemyType;

    // count value
    protected int loopValue;
    protected int currentTime;
    protected String direction;

    // check Player
    protected boolean playerAlive;

    public Enemy(PImage[][] sprite) {
        this.sprite = sprite;
        this.alive = true;
        this.currentTime = 48;
        this.playerAlive = true;
    }

    public void tick(Map map, Player player){
        // movement logic
        checkLevel(map);
        if (this.alive == true){
            this.currentMap = map.getMap();
            for (int i = 0; i < this.currentMap.length; i++){
                for (int n = 0; n < this.currentMap[i].length; n++){
                    if (this.currentMap[i][n].equals(this.enemyType)){
                        this.x = n*32;
                        this.y = i*32 + 48;
                        this.row = i;
                        this.col = n;
                        break;
                    }
                }
            }
            enemyMove(map);
            checkPlayer(player);
        }
    }

    public void draw(PApplet app){
        if (this.alive == true){
            currentSprite = loopValue/12;
            if (currentSprite >= 4){
                currentSprite = 0;
                loopValue = 0;
            }
            app.image(this.sprite[currentDirection][currentSprite], this.x, this.y);
            loopValue ++;
        }
    }

    public void enemyMove(Map map){
        if (this.alive == true){
            if (currentTime >= 60){
                currentTime = 0;
            }
            if (currentTime == 59){
                boolean check =false;
                while(check == false){
                    if (this.direction.equals("left")){
                        if (this.checkMove(row,col-1) == true){
                            map.setMap(this.enemyType,row,col,row,(col-1));
                            this.currentDirection = 2;
                            check = true;
                        }
                        else{
                            while(direction.equals(changeDir())){
                                changeDir();
                            }
                            direction = changeDir();
                        }
                    }
                    if (this.direction.equals("right")){
                        if (this.checkMove(row,col+1) == true){
                            map.setMap(this.enemyType,row,col,row,col+1);
                            this.currentDirection = 3;
                            check = true;
                        }
                        else{
                            while(direction.equals(changeDir())){
                                changeDir();
                            }
                            direction = changeDir();
                        }
                    }
                    if (this.direction.equals("up")){
                        if (this.checkMove(row-1,col) == true){
                            map.setMap(this.enemyType,row,col,row-1,col);  
                            this.currentDirection = 0;
                            check = true;
                        }
                        else{
                            while(direction.equals(changeDir())){
                                changeDir();
                            }
                            direction = changeDir();
                        }
                    }
                    if (this.direction.equals("down")){
                        if (this.checkMove(row+1,col) == true){
                            map.setMap(this.enemyType,row,col,row+1,col);
                            this.currentDirection = 1;
                            check = true;  
                        }  
                        else{
                            while(direction.equals(changeDir())){
                                changeDir();
                            }
                            direction = changeDir();
                        }
                    }
                }
            }
            currentTime += 1;
        }
    }

    public boolean checkMove(int a, int b){
        if (this.currentMap[a][b].equals("W") || this.currentMap[a][b].equals("B")){
            return false;
        }
        else if (this.currentMap[a][b].equals("P")){
            this.playerAlive = false;
            return true;
        }
        else{
            return true;
        }
    }

    public abstract String changeDir();

    public void checkLevel(Map map){
        if (map.getLevel() == 2){
            this.notAlive();
        }
    }

    public void notAlive(){
        this.alive = false;
    }

    public void checkPlayer(Player player){
        if (this.playerAlive == false){
            player.notAlive();
            this.playerAlive = true;
        }
    }
}
