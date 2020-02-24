package cs1302.arcade;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import cs1302.arcade.Bullet;

/**
 * This creates alien objects for the SpaceInvaders Game.
 */
public class Alien{

    private double alienX;
    private double alienY;
    private String alienPic = "YZ8V";
    private double bombX;
    private double bombY;
    private String bombPic;
    private boolean alienLife;
    private Image alienImg;
    private double speed;
    private Bullet b;

    /**
     * This constructs an Alien with a set x and y coordinate.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Alien(int x, int y){
	alienX = x;//initial position
        alienY = y;
        alienImg = new Image(alienPic,30,30,false,false);//default image
        b = new Bullet(alienX,alienY);
	b.setImage(1);
        setLife(true);
	speed =2;//initial speed
    }

    /**
     * This moves the aliens a horizontaly certain integer amount.
     * @param hmove the increment moved.
     */ 
    public void moveAlien(int hmove){
        alienX += hmove;
    }

    /**
     * This returns the alien image.
     * @return alienImg the alien picture.
     */
    public Image getImage(){
	return alienImg;
    }

    /**
     * This returns the x position.
     * @return alienX the x position.
     */
    public double getX(){
	return alienX;
    }

    /**
     * This returns the y position.
     * @return alienY the y position.
     */
    public double getY(){
	return alienY;
    }

    /**
     * This sets the x position.
     * @param x the new x.
     */
    public void setX(double x){
	alienX = x;
    }

    /**
     * This sets the y position.
     * @param y the new y.
     */
    public void setY(double y){
	alienY = y;
    }

    /**
     * This sets the life of the alien.
     * @param alive if the alien is alive.
     */
    public void setLife(boolean alive){
        alienLife = alive;
    }

    /**
     * This returns if the alien is alive.
     * @return alienLife true if the alien is alive.
     */
    public boolean getLife(){
        return alienLife;
    }

    /**
     * This returns the speed of the alien.
     * @return speed the speed of the alien.
     */
    public double getSpeed(){
	return speed;
    }

    /**
     * This sets the speed of the alien.
     * @param inc what is added to speed.
     */
    public void setSpeed(double inc){
	speed +=inc;
    }

    /**
     * This returns a bullet at the aliens position.
     * @return b the alien's bullet.
     */
    public Bullet getBullet(){
	return b;
    }
	
}
