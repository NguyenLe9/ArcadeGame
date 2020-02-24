package cs1302.arcade;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import cs1302.arcade.Bullet;

/**
 * This class creates a player object for SpaceInvaders game.
 */
public class Player{

    /**
     * private instance variables
     */
    private String lazerPic = "thanos_0_0-min.png";
    private int gunX;
    private int gunY;
    private int playerX;
    private int playerY;
    private boolean life;
    private Image lazerImg;
    private Image[] lifes;
    private int numLifes;
    private Bullet b;

    /**
     * This constructs a player object.
     */
    public Player(){
        playerX = 240;//original position
        playerY = 450;
        lazerImg = new Image(lazerPic,120,120,true,true);
	lifes = new Image[3];//lifes
	makeLevels();
        ImageView lazerView = new ImageView(lazerImg);
	setLife(true);
	numLifes = 3;
    }

    /**
     * This returns the player's image.
     * @return lazerImg the player's image
     */
    public Image getImage(){
	return lazerImg;
    }

    /**
     * This inititates the player's bullet.
     */
    public void makeBullet(){
	b = new Bullet(playerX+5,playerY+5);
    }

    /**
     * This returns the player's bullet.
     * @return b the player's bullet.
     */
    public Bullet getBullet(){
	return b;
    }

    /**
     * This method assigns different pictures to each element in lifes array
     */
    public void makeLevels(){
	lifes[0] = new Image("infinity-stone-png-3.png",50,50,true,true);
	lifes[1] =new Image("latest?cb=20180701235516",50,50,true,true);
	lifes[2] = new Image("transparent-stone-infinity-6.png",50,50,true,true);
    }

    /**
     * This returns the image for level one.
     * @return levels[0] the first picture.
     */
    public Image getLevelOne(){
	return lifes[0];
    }

     /**
     * This returns the image for level two.
     * @return levels[1] the first picture.
     */
    public Image getLevelTwo(){
	return lifes[1];
    }

     /**
     * This returns the image for level three.
     * @return levels[2] the first picture.
     */
    public Image getLevelThree(){
	return lifes[2];
    }

    /**
     * This return the x position.
     * @return playerX the x position.
     */
    public int getX(){
	return playerX;
    }

    /**
     * This returns the y position.
     * @return playerY the y position.
     */
    public int getY(){
	return playerY;
    }

    /**
     * This sets the x position.
     * @param x the x position
     */
    public void setX(int x){
	playerX = x;
    }

    /**
     * This sets the y position.
     * @param y the y position.
     */
    public void setY(int y){
	playerY = y;
    }

    /**
     * This sets the life of the player.
     * @param alive if the player is alive or dead.
     */
    public void setLife(boolean alive){
        life = alive;
    }

    /**
     * This return the number of lifes the player has remaining.
     * @return numLifes the number of lifes remaining.
     */
    public int getNumLifes(){
	return numLifes;
    }

    /**
     * This sets the number of lifes.
     * @param num the amount of lifes.
     */
    public void setNumLifes(int num){
	numLifes = num;
    }

    /**
     * This returns if the player is alive.
     * @return life if the player is alive.
     */
    public boolean isAlive(){
	return life;
    }

    /**
     * This moves the player in a horizontal direction.
     * @param direction the amount to be moved.
     */
    public void move(int direction){
	playerX += direction;
    }

}    
