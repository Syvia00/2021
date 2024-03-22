package demolition;

import processing.core.PImage;

import processing.core.PApplet;
import processing.core.PImage;

public class Bomb {
    
    private int x;
    private int y;

    private PImage[] bombImages;
    private int currentBomb;
    private PImage[] explosionImages;
    private int[] xDir;
    private int[] yDir;

    private int countTime;
    private boolean start;
    
    public Bomb(PImage[] bombImages, PImage[] explosionImages){
        this.bombImages = bombImages;
        this.explosionImages = explosionImages;
        this.start = false;
    }

    public void tick(){
    }

    public void draw(PApplet app, Map map, Player player, RedEnemy redEnemy, YellowEnemy yellowEnemy){
        if (this.start == true){
            currentBomb = countTime/15;
            if (countTime < 120){
                app.image(this.bombImages[currentBomb], this.x*32, this.y*32+64);
            }
            else if (countTime >= 120 && countTime < 150){
                for (int i = 0; i < this.xDir.length; i++){
                    if (this.xDir[i] >= 0 && this.xDir[i] < 15 && this.yDir[i] >= 0 && this.yDir[i] <13 && checkWall(map,i) == true){
                        if (i == 6){
                            app.image(this.explosionImages[5], this.xDir[i]*32, this.yDir[i]*32+64);
                        }
                        else if (i == 7){
                            app.image(this.explosionImages[6], this.xDir[i]*32, this.yDir[i]*32+64);
                        }
                        else if (i == 8){
                            app.image(this.explosionImages[6], this.xDir[i]*32, this.yDir[i]*32+64);
                        }
                        else{
                            app.image(this.explosionImages[i], this.xDir[i]*32, this.yDir[i]*32+64);
                        }
                    }
                }
                for (int i = 0; i < this.xDir.length; i++){
                    if (this.xDir[i] >= 0 && this.xDir[i] < 15 && this.yDir[i] >= 0 && this.yDir[i] <13 && checkWall(map,i) == true){
                        if (map.getMap()[this.yDir[i]][this.xDir[i]].equals("P")){
                            this.checkRemove(player,redEnemy,yellowEnemy,map,i);
                            this.start = false;
                            break;
                        }
                        else{
                            this.checkRemove(player,redEnemy,yellowEnemy,map,i);
                        }
                            
                    }
                }
            }
            else{
                this.start = false;
            }
            countTime ++;
        }
    }

    public void getXY(int x, int y){
        this.x = x;
        this.y = y;
        this.xDir = new int[]{this.x, this.x, this.x-2, this.x+2, this.x, this.x-1, this.x+1, this.x, this.x};
        this.yDir = new int[]{this.y, this.y+2, this.y, this.y, this.y-2, this.y, this.y, this.y-1, this.y+1};
        this.start = true;
    }

    public void checkRemove(Player player, RedEnemy redEnemy, YellowEnemy yellowEnemy,Map map, int i){
        String[][] currentMap = map.getMap();
        if (!currentMap[this.yDir[i]][this.xDir[i]].equals(" ")){
            map.removeMap(player,redEnemy,yellowEnemy,currentMap[this.yDir[i]][this.xDir[i]],this.yDir[i],this.xDir[i]);
        }
    }

    public boolean checkWall(Map map,int i){
        boolean check = false;
        String[][] currentMap = map.getMap();
        if (!currentMap[this.yDir[i]][this.xDir[i]].equals("W")){
            if (i == 1){
                if (!currentMap[this.yDir[i]-1][this.xDir[i]].equals("W")){
                    check = true;
                }   
            }
            else if(i == 2){
                if (!currentMap[this.yDir[i]][this.xDir[i]+1].equals("W")){
                    check = true;
                }
            }
            else if (i ==3){
                if (!currentMap[this.yDir[i]][this.xDir[i]-1].equals("W")){
                    check = true;
                }
            }
            else if(i == 4){
                if (!currentMap[this.yDir[i]+1][this.xDir[i]].equals("W")){
                    check = true;
                }
            }
            else{
                check = true;
                }
        }
        return check;
    }
}