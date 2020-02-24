package cs1302.arcade;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode; 
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import cs1302.arcade.Alien;
import cs1302.arcade.Player;
import java.util.Iterator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.LinkedList;
import java.lang.Math;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.control.Label;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/** 
 * Creates SpaceInvaders game!
 */
public class SpaceInvaders{

    /** 
     * private variables 
     */
    private Canvas spaceBox;
    private GraphicsContext gc;
    private VBox loseBox;
    private Scene scene;
    private Stage stage;
    private String bgPic= "51gJ32A4wRL.jpg";
    private boolean hitPlayer = false;
    private boolean hitAlien = false;
    private Alien[] alienArray;
    private Player player;
    private int aliensDead;
    private Image bgImg;
    private int direction = -1;
    private int level;
    private boolean shooting = false;
    private int score =0;
    private LinkedList<Bullet> bulletList = new LinkedList<Bullet>();
    private int timeCount =0;
    private int timeCount2 =0;
    private Timeline timeline = new Timeline();
    private File file = new File("src/main/java/cs1302/arcade/score.txt");
    private String name;
    private Text text1;
    private TextField field;
    private Button enter;

    
    /**
     * The display method is called from ArcadeApp and begins the process of creating the 
     * SpaceInvaders game. It initializes a canvas with GraphicsContext. The canvas is assigned to
     * a Group object that is fed into the scene. Then the satge is set with this scene and
     * the main game loop is called.
     */
    public void display(){
	spaceBox = new Canvas(500,500);
	gc = spaceBox.getGraphicsContext2D();
	bgImg = new Image(bgPic);
	gc.drawImage(bgImg,0,0);
	Group root = new Group();
	root.getChildren().addAll(spaceBox);
	root.requestFocus();
	scene= new Scene(root);
	scene.setOnKeyPressed(createKeyHandler());
	stage = new Stage();
	stage.setTitle("Space Invaders");
	stage.sizeToScene();
	stage.initModality(Modality.NONE);
	stage.setScene(scene);
	stage.show();
	setUp();
    }

    /** 
     * This method returns the event handler that controls the action taken when certain keys
     * are pressed.
     * @return EventHandler the event that occurs when a key is pressed. 
     */
    private EventHandler<? super KeyEvent> createKeyHandler() {
        return event -> {
            if (event.getCode() == KeyCode.LEFT){
		player.setX(player.getX() - 10);
	    }
            if (event.getCode() == KeyCode.RIGHT){
		player.setX(player.getX() + 10);
	    }
	    if (event.getCode() == KeyCode.SPACE){
		Bullet pb = new Bullet(player.getX(),player.getY());
		addToBulletList(pb);
		shootAlien(pb);
	    }	      
	    //clear the whole canvas and redraw with new player position
	    reset();
        };
    }

    /**
     * Method to clear and reset the GraphicsContext. When images move, 
     * this method is called so the orginal image is cleared and drwn in the new position
     */
    public void reset(){
	gc.clearRect(0.00, 0, 500.00, 500.00);
	gc.drawImage(bgImg,0,0);
	displayPlayer();
	displayAliens();
	displayLifes();
	displayScore();
	displayBullets();
    }

    /**
     * The set up the game that begins when the app is opened.
     */
    public void setUp(){
	makePlayer();
	makeAlienArray();
	aliensDead = 0;
	displayPlayer();
	displayAliens();
	displayLifes();
	displayScore();
	level = 1;
	timeline();
    }

    /**
     * This method is called when the player defeats all the aliens and
     * increases the rate at which the aliens move across the screen.
     */
    public void levelUp(){
	level ++;
	makeAlienArray();
	aliensDead = 0;
	for(Alien alien : alienArray){
	    alien.setSpeed(alien.getSpeed()+1);
	}
	bulletList.clear();
	timeline();
	reset();
    }
    
    /**
     * This method tests if the game should end. If the player dies, all aliens 
     * have died, or the aliens reach the bottom of the canvas, the game stops.
     * @return boolean if playing.
     */ 
    public boolean playing(){
	boolean playing = true;
	if((player.isAlive()==false)||invasion()){
	    playing = false;
	}
	if (aliensDead==20){
	    playing = false;
	}
	return playing;
    }
    
    /**
     * This method creates an array of Alien objects and sets their position.
     */ 
    public void makeAlienArray(){
	alienArray = new Alien[20];
	int x =0;
	for (int i =0; i<2; i++){
	    for(int j = 0; j<10; j++){//loops to set the x ad y positions
		Alien alien = new Alien(50 + (30 * j), 25 + (35 * i));
		alienArray[x] = alien;
		x++;
	    }
	}
    }
    
    /**
     * This initiates a new player.
     */
    public void makePlayer(){
	player = new Player();
    }
    
    /**
     * This method draws the player in its set position.
     */
    public void displayPlayer(){
	if(player.isAlive()){
	    gc.drawImage(player.getImage(), player.getX(), player.getY());
	}
    }
    
    /**
     * This method draws each alien in the array if it is alive.
     */
    public void displayAliens(){
	for(int i =0; i<alienArray.length; i++){
	    if(alienArray[i].getLife()){
		Image img = alienArray[i].getImage();//display the aliens at position
		double x = alienArray[i].getX();
		double y = alienArray[i].getY();
		gc.drawImage(img,x,y);
	    }	
	    
	}
    }

    /**
     * This method displays all the bullets in the bullet list.
     */
    public void displayBullets(){
	for (Bullet bullet: bulletList){
	    gc.drawImage(bullet.getImage(),bullet.getX(),bullet.getY());
	}
    }

    /**
     * This method moves the bullet verticaly. If an alien is shooting,
     * then the y increases. If the player is shooting, then the y decreases.
     * @param goingDown true if the alien is shooting
     * @param b the bullet moving
     */
    public void moveBullet(Boolean goingDown, Bullet b){
	if(goingDown){
	    b.setY(b.getY()+20);
	}
	else{
	    b.setY(b.getY()-20);
	}
    }

    /**
     * This method displays the player's score.
     */
    public void displayScore(){
	gc.setFill(Color.WHITE);
	gc.fillText("Score: " + score, 20,480,100);
		   
    }

    /**
     * This method displays the player's lifes in the top right corner
     */
    public void displayLifes(){
	if(player.getNumLifes()>0){
	    gc.drawImage(player.getLevelOne(),460,10);
	}
	if(player.getNumLifes()>1){
	    gc.drawImage(player.getLevelTwo(),420,10);
	}
	if(player.getNumLifes()>2){
	    gc.drawImage(player.getLevelThree(),380,10);
	}
    }

    /**
     * This is the tineline called when the game starts and moves the aliens 
     * every 2 seconds.
     */
    public void timeline(){
	EventHandler<ActionEvent> handler = event -> {
	    moveAliens();
	    randomSelection();
	    displayBullets();
	    if(aliensDead == 20){
		timeline.stop();//stops the timeline so levelUp executes once
		levelUp();
	    }
	    reset();
	};
	KeyFrame keyFrame = new KeyFrame(Duration.seconds(.06), handler);
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyFrame);
	timeline.play();
    }


    /**
     * This method explains the alien movement pattern. Once the aliens reach 
     * the boundary, they change directions and get closer to the ground.
     */
    public void moveAliens(){
	Alien leftAlien = alienArray[0];
	Alien rightAlien = alienArray[19];
	if(leftAlien.getX() <= 10){//left bound
	    direction = 1;
	    for(Alien alien :alienArray){
		alien.setY(alien.getY()+10);
		playing();
	    }
	}
	if(rightAlien.getX()>=480){//right bound
	    direction = -1;
	    for(Alien alien :alienArray){
		alien.setY(alien.getY()+10);
		playing();
	    }
	}
	for (Alien alien : alienArray){
	    alien.setX(alien.getX()+(rightAlien.getSpeed()*direction));
	}
    }


    /**
     * This method is called to shoot the aliens. It starts a timeline that 
     * moves the calls the moveBullet method.
     * @param b the bullet being shot
     */
    public void shootAlien(Bullet b){
	EventHandler<ActionEvent> handler = event -> {
	    moveBullet(false,b);
	    isAlienShot(b);
	};
	KeyFrame keyFrame = new KeyFrame(Duration.seconds(.05), handler);
	Timeline timeline = new Timeline();
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyFrame);
	timeline.play();
    }

    /**
     * This method is called to shoot the player. It starts a timeline that
     * moves the calls the moveBullet method.
     * @param b the bullet being shot
     */
    public void shootPlayer(Bullet b){
	EventHandler<ActionEvent> handler = event -> {
	    moveBullet(true,b);
	    isPlayerShot(b);
	};
	KeyFrame keyFrame = new KeyFrame(Duration.seconds(.05), handler);
	Timeline timeline = new Timeline();
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyFrame);
	timeline.play();
    }

    /**
     * This method randomly selects an alien to shoot the player.
     * It adds a bullet to the list and then shoots the player with that bullet.
     */
    public void randomSelection(){
	if(timeCount == 30){ //only called every 30 times due to faster timeline
	    boolean answer =false;
	    while(answer == false){
		int choice = (int)(Math.random()*19);
		if(alienArray[choice].getLife()){
		    addToBulletList(alienArray[choice].getBullet());
		    shootPlayer(alienArray[choice].getBullet());
		    answer = true;
		}
	    }
	    timeCount = 0;
	}
	else{
	    timeCount++;
	}
    }

    /**
     * This method adds a bullet to bulletList.
     * @param b the bullet to add.
     */
    public void addToBulletList(Bullet b){
	bulletList.add(b);
    }
    
    /** 
     * This method checks if a alien was hit. If an alien is hit,
     * the destroy method is called.
     * @param b the bullet shot.
     */
    public void isAlienShot(Bullet b){
	if(b.hasHit()==false){
	    for(int i =alienArray.length-1;i>=0; i--){
		if(alienArray[i].getLife()){ //can only destroy alive aliens
		    if (Math.abs(alienArray[i].getX()-b.getX())<5){
			destroyAlien(alienArray[i]);
			aliensDead ++;
			keepScore(20);
			b.setHit(true);
			i=0;
		    }
		}
	    }
	}
    }
	
    /**
     * This method is called is the player was hit. When the player is hit, 
     * they lose a life.
     * @param b the bullet
     */
    public void isPlayerShot(Bullet b){
	if(b.hasHit()==false){
	    if (Math.abs((player.getX()+20)-b.getX())<21){//adjust for calibrate
		keepScore(-30);
		loseLife();
		b.setHit(true);
	    }
	    b.setHit(true);
	}
    }

    /**
     * This method adds a number to the score.
     * @param num the integer added.
     */
    public void keepScore(int num){
	score += num;
    }

    /**
     * This method is called when an alien is hit and killed.
     * @param alien the alien to destroy
     */
    public void destroyAlien(Alien alien){
	alien.setLife(false);
	reset();
    }

    /** 
     * This method declines the lifes of the player when they are hit.
     */
    public void loseLife(){
	player.setNumLifes(player.getNumLifes()-1);
	if (player.getNumLifes() == 0){
	    destroyPlayer();
	}
    }
    
    /**
     * This method is called when th player is shot enough times to die.
     */
    public void destroyPlayer(){
	player.setLife(false);
	if(playing()==false){
	    displayLoseScreen();
	}
    }
    
    /**
     * This method tells if the aliens have reached the ground.
     * @return boolean if the aliens have reached y=0
     */
    public boolean invasion(){
	boolean result = false;
	if(alienArray[19].getY()<=0){
	    System.out.println("invasion result: "+result);
	    result = true;
	}
	return result;
    }

    /**
     * This scene is displayed when the player loses
     */
    public void displayLoseScreen(){
	Rectangle r = new Rectangle(0.0,0.0,640.0,480.0);
        String link = "Iron-Man-Decapitates-Thanos-Fan-Art-Header-Crop.jpg";
	r.setFill(new ImagePattern(new Image(link)));
        Label label = new Label("GAME OVER");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
        label.setLayoutX(100.0);
        label.setLayoutY(50.0);
	Label finalScore = new Label("Final score: "+ score);
        finalScore.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
        finalScore.setLayoutX(100.0);
        finalScore.setLayoutY(85.0);
	enterScore();
	updateScoreTable();
	Group root1 = new Group();
        root1.getChildren().addAll(r,label,finalScore,text1,field,enter);
	root1.requestFocus();
	Scene loseScene= new Scene(root1);
	stage.setScene(loseScene);
	stage.show();
    }

    /**
     * This creates a place for the player to enter their name to be recorded for the high score.
     */
    public void enterScore(){
	text1 = new Text("Please enter \nyour nickname in ONE \nword and hit Enter");
        text1.setLayoutX(100);
        text1.setLayoutY(200.0);
        text1.setStyle("-fx-text-fill: white; -fx-font-size:15;");
        field = new TextField("");
        field.setLayoutX(100.0);
        field.setLayoutY(300.0);
        enter = new Button("Enter");
        enter.setLayoutX(100);
        enter.setLayoutY(300.0);
        EventHandler<ActionEvent> enterHandler = e -> {
            name = field.getCharacters().toString();
            field.setDisable(true);};
        enter.setOnAction(enterHandler);
    }

    /**
     * This method updates the score table file.
     */
    public void updateScoreTable(){
	try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.append("SpaceInvaders" + " " + name + " " +score + "\n");
            output.close();}
        catch (IOException e) {
            System.out.println("Error while updating file");}
    }
       
    
}
    
    
