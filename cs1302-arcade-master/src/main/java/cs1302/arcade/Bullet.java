package cs1302.arcade;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This creates a bullet object for SpaceInvaders.
 */
public class Bullet{

    /**
     * Private instance variables
     */
    private double bulletX;
    private double bulletY;
    private String bulletPic = "173-1737937_rohit-glow-png-circle.png";
    private String alienBulletPic = "black-circle-png47c-4a62-b41d-149d42a05759.png";
    private boolean visible;
    private Image bulletImg;
    private boolean hasHit;

    /**
     * THis creates a bullet at a set x and y.
     * @param x the set x
     * @param y the set y
     */
    public Bullet(double x, double y){
        //set initial position
        bulletX = x;
        bulletY = y;
        setVisible(false);
	bulletImg = new Image(bulletPic,30,30,false,false);
    }

    /**
     * This sets the x position.
     * @param x the new x position.
     */
    public void setX(double x){
	bulletX = x;
    }

    /**
     * This sets the y position.
     * @param y the new y position.
     */
    public void setY(double y){
	bulletY = y;
    }

    /**
     * This returns the x position.
     * @return bulletX the x position.
     */
    public double getX(){
	return bulletX;
    }

    /**
     * This returns the y position.
     * @return bulletY the y position.
     */
    public double getY(){
	return bulletY;
    }

    /**
     * This returns the bullet image.
     * @return bulletImg the image.
     */
    public Image getImage(){
	return bulletImg;
    }

    /**
     * This sets the image.
     * @param selection if 0 use alternate picture
     */
    public void setImage(int selection){
	if(selection ==0){
	    bulletImg = new Image(bulletPic,50,50,false,false);
	}
	else{
	    bulletImg = new Image(alienBulletPic,10,10,false,false);
	}
    }

    /**
     * This sets the bullet as visible.
     * @param result if the image is visible.
     */
    public void setVisible(boolean result){
	visible = result;
    }

    /**
     * This returns if the bullet isVisible;
     * @return visible if the image is visible.
     */
    public boolean isVisible(){
	return visible;
    }

    /** 
     * This returns if the bullet has hit a target.
     * @return hasHit if the bullet has hit a target.
     */
    public boolean hasHit(){
	return hasHit;
    }

    /**
     * This sets if the bullet has hit.
     * @param hit if the bullet has hit.
     */
    public void setHit(boolean hit){
	hasHit = hit;
    }
    
}
