package cs1302.arcade;
import javafx.animation.AnimationTimer;
import java.net.URL;
import java.io.File;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import java.io.IOException;
import javafx.scene.Group;
import java.io.BufferedWriter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Insets;
import javafx.stage.WindowEvent;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import javafx.scene.effect.GaussianBlur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import javafx.stage.Modality;
import javafx.scene.text.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.animation.PauseTransition;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import java.io.FileWriter;
/**
 *This class represents a 2048 game
 */
public class Game2048 {
    private PauseTransition pause;
    private String square = "file:src/main/resources/2IIAEoz";
    private String twoImg = "file:src/main/resources/2P9LEwm";
    private String fourImg = "file:src/main/resouces/2J9pLfH";
    private int columnClone;
    private SequentialTransition sequence;
    private String name;
    private Text text1;
    private Group origRoot;
    private double temp;
    private TilePane tilePane;
    private Rectangle r;
    private Stage stage;
    private Text text;
    private VBox vbox;
    private TextField field;
    private ImageView imageView;
    private Scene scene;
    private Label label;
    private Group root;
    private Timeline downTimeline;
    private Timeline upTimeline;
    private Timeline rightTimeline;
    private Timeline printRand;
    private Timeline leftTimeline;
    private Rectangle rect;
    private File file = new File("src/main/java/cs1302/arcade/score.txt");
    private int score = 0;
    private int[] scoreArray = new int[10];
    private ImagePattern[] pattern = new ImagePattern[11];
    private ImagePattern squareImg = new ImagePattern(new Image(square));
    private ImagePattern two = new ImagePattern(new Image(twoImg));
    private Rectangle[][] myArray = new Rectangle[4][4]; 
    private double[][] xPosition = new double [4][4];
    private double[][] yPosition = new double[4][4];
    private TranslateTransition translate;
    private EventHandler<KeyEvent> keyListener;
    /**
     *This method sets up the key listeners for playes including left, right, up, down.
     */
    public void setUpKeyListener() {
	keyListener = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
		    EventHandler<ActionEvent> randomHandler = randEvent -> {
			printRandom1();
			drawBoard();
			if (checkGameOver()==true) {
			    setPopUpStage();}
		    };
		    printRand = new Timeline();
		    KeyFrame keyFrame0 = new KeyFrame(Duration.seconds(1),randomHandler);
		    printRand.setCycleCount(1);
		    printRand.getKeyFrames().add(keyFrame0);
		    if(event.getCode() == KeyCode.LEFT) {
			setUpKeyLeft();	}
		    if (event.getCode() == KeyCode.RIGHT) {
			setUpKeyRight();	}
		    if (event.getCode() == KeyCode.UP) {
			setUpKeyUp(); }   
		    if (event.getCode() == KeyCode.DOWN) {
			setUpKeyDown();}
		    event.consume(); }   };
    }
    /**
     *This method sets up all the implementations when players hit left
     */
    public void setUpKeyLeft() {
	EventHandler<ActionEvent> leftHandler = event1 -> moveLeft();
	KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), leftHandler);
	leftTimeline = new Timeline();
	leftTimeline.setCycleCount(1);
	leftTimeline.getKeyFrames().add(keyFrame1);
	sequence = new SequentialTransition(leftTimeline, printRand);
	sequence.play();
    }
    
    /**
     *This method sets up all the implementations when players hit right
     */
    public void setUpKeyRight(){
	EventHandler<ActionEvent> rightHandler = event1 -> moveRight();
	KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), rightHandler);
	rightTimeline = new Timeline();
	rightTimeline.setCycleCount(1);
	rightTimeline.getKeyFrames().add(keyFrame1);
	sequence = new SequentialTransition(rightTimeline, printRand);
	sequence.play(); 
    }
    
    /**
     *This method sets up all the implementations when players hit up
     */
    public void setUpKeyUp() {
	EventHandler<ActionEvent> upHandler = event1 -> moveUp();
	KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), upHandler);
	upTimeline = new Timeline();
	upTimeline.setCycleCount(1);
	upTimeline.getKeyFrames().add(keyFrame1);
	sequence = new SequentialTransition(upTimeline, printRand);
	sequence.play();
    }
    
    /**
     *This method sets up all the implementations when players hit down
     */
    public void setUpKeyDown() {
	EventHandler<ActionEvent> downHandler = event1 -> moveDown();
	KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), downHandler);
	downTimeline = new Timeline();
	downTimeline.setCycleCount(1);
	downTimeline.getKeyFrames().add(keyFrame1);
	sequence = new SequentialTransition(downTimeline, printRand);
	sequence.play();
    }
    
    /**
     *This method updates the score table to the score.txt file
     */
    public void updateScoreTable() {
	try {
	    BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
	    output.append("2048"+ " " + name + " " + score + "\n");
	    output.close();}
	catch (IOException e) {
	    System.out.println("Error while updating file");}
    }
    
    /**
     *This method pops up a new window when game is over
     */
    public void setPopUpStage() {
	root.setEffect(new GaussianBlur());
	VBox gameOverRoot = new VBox();
	Text text1 = new Text("Game Over!");
	text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 50));
	text1.setFill(Color.CORAL);
	gameOverRoot.getChildren().add(text1);
	gameOverRoot.setAlignment(Pos.CENTER);
	gameOverRoot.setPadding(new Insets(20));
		
	Stage popupStage = new Stage();
	popupStage.initOwner(this.stage);
	popupStage.initModality(Modality.NONE);
	popupStage.setScene(new Scene(gameOverRoot));
	popupStage.setOpacity(0.2);
	popupStage.sizeToScene();
	popupStage.show();
    }
    /**
     *This method initializes an array of image patterns including 2,4,8...
     */
    public void setUpImgPatterns() {
	pattern[0] = two;
	pattern[1] = new ImagePattern(new Image("file:src/main/resources/2J9pLfH"));
	pattern[2] = new ImagePattern(new Image("file:src/main/resources/2DBSNAO"));
	pattern[3] = new ImagePattern(new Image("file:src/main/resources/2IPy54B"));
	pattern[4] = new ImagePattern(new Image("file:src/main/resources/2GC3O5K"));	
	pattern[5] = new ImagePattern(new Image("file:src/main/resources/2DEY5eP"));	
	pattern[6] = new ImagePattern(new Image("file:src/main/resources/2VzxdXZ"));
	pattern[7] = new ImagePattern(new Image("file:src/main/resources/2IN6Asv"));
	pattern[8] = new ImagePattern(new Image("file:src/main/resources/2IM9oq1"));
	pattern[9] = new ImagePattern(new Image("file:src/main/resources/2IN7aXd"));
	pattern[10] = new ImagePattern(new Image("file:src/main/resources/2ViSupA"));
    }
    /**
     *This method sets up a score array and store it
     */
    public void setUpScoreArray() {
	int i = 0;
	int numb = 2;
	while (i<scoreArray.length) {
	    scoreArray[i] = numb;
	    numb = numb*2;
	    i++;}
    }
    
    /**
     *This method moves all the tile to the left and distribute those in the right places
     *when plays hit left. It also resets the array the stores image pattern.
     */
    public void moveLeft() {
	int row = 0;
	int column = 0;
	while (row<4) {
	    column = 0;
	    while (column<4) {
		if (myArray[row][column].getFill()!=squareImg && xPosition[row][column]>10.0) {
		    int columnClone = column;
		    while (columnClone> 0) {
			if (myArray[row][columnClone-1].getFill()==squareImg) {
 			    columnClone--;}
			else {  break;}}
		    translate = new TranslateTransition();
		    //if tiles can combine
		    if (columnClone>0 &&
			myArray[row][columnClone-1].getFill()==myArray[row][column].getFill()) {
			root.getChildren().remove(myArray[row][columnClone-1]);
			Rectangle rect = new Rectangle(xPosition[row][columnClone-1],
						       yPosition[row][columnClone-1],100,100);
			int numb = findMatchedPattern(myArray[row][columnClone-1].getFill());
			rect.setFill(pattern[numb]);
			updateScore(findMatchedPattern(myArray[row][columnClone-1].getFill()));
			translate.setByX(-(xPosition[row][column]-xPosition[row][columnClone-1]));
			PauseTransition pause = new PauseTransition(Duration.millis(900));
			int row1 = row;
			int column1 = columnClone -1;
			pause.setOnFinished(event ->{
					    root.getChildren().remove(myArray[row1][column1]);
					    root.getChildren().add(rect); });
			pause.play(); 
			translate.setDuration(Duration.millis(600));
			translate.setNode(myArray[row][column]);
			translate.play();
			myArray[row][columnClone-1] = rect;
			createRectangle(row, column); }
		    else if(columnClone==column &&
			    myArray[row][columnClone-1].getFill()!=myArray[row][column].getFill()){}
		    //if tiles can't combine
		    else {moveHelper1(row,columnClone,column); }}
		column++; }
	    row++;}
    }
    /**
     *This methods create a rectangle based on given row and column and adds it to the root
     *@param row as given row
     *@param column as given column
     */
    public void createRectangle(int row, int column) {
	Rectangle r = new Rectangle(xPosition[row][column],yPosition[row][column],100,100);
	r.setFill(squareImg);
	myArray[row][column]= r;
	root.getChildren().add(r);
    }
    /**
     *A helper method to move the tiles to the left when they don't combine
     *@param row as current row
     *@param columnClone as the destination
     *@param column as current column
     */
    public void moveHelper1(int row, int columnClone, int column) {
	translate = new TranslateTransition();
	root.getChildren().remove(myArray[row][columnClone]);
	translate.setByX(-(xPosition[row][column]-xPosition[row][columnClone]));
	Rectangle r1 = new Rectangle(xPosition[row][columnClone],
				     yPosition[row][columnClone],100,100);
	r1.setFill(myArray[row][column].getFill());
	myArray[row][columnClone] = r1;
	translate.setDuration(Duration.millis(600));
	translate.setNode(myArray[row][column]);
	translate.play();  
	Rectangle r = new Rectangle(xPosition[row][column],yPosition[row][column],100,100);
	r.setFill(squareImg);
	myArray[row][column]= r;
	root.getChildren().add(r);
    }
    
    /**
     *This method moves all the tile to the left and distribute those in the right places
     *when plays hit right. It also resets the array the stores image pattern.
     */
    public void moveRight() {	
	int row = 0;
	int column = 3;
	while (row<4) {
	    column = 3;
	    while (column>=0) {
		if (myArray[row][column].getFill()!=squareImg && xPosition[row][column]<340.0) {
		    int columnClone = column;
		    while (columnClone<3) {
			if (myArray[row][columnClone+1].getFill()==squareImg) {
 			    columnClone++;}
			else {
			    break;}  }
		    translate = new TranslateTransition();
		    //if tiles can combine
		    if (columnClone<3
			&& myArray[row][columnClone+1].getFill()==myArray[row][column].getFill()) {
			root.getChildren().remove(myArray[row][columnClone+1]);
			Rectangle rect = new Rectangle(xPosition[row][columnClone+1],
						       yPosition[row][columnClone+1],100,100);
			int numb = findMatchedPattern(myArray[row][columnClone+1].getFill());
			rect.setFill(pattern[numb]);
			updateScore(findMatchedPattern(myArray[row][columnClone+1].getFill()));
			translate.setByX(-(xPosition[row][column]-xPosition[row][columnClone+1]));
			PauseTransition pause = new PauseTransition(Duration.millis(900));
			int row1 = row;
			int column1 = columnClone +1;
			pause.setOnFinished(event ->{
				root.getChildren().remove(myArray[row1][column1]);
				root.getChildren().add(rect); } );
			pause.play(); 
			translate.setDuration(Duration.millis(600));
			translate.setNode(myArray[row][column]);
			translate.play();
			myArray[row][columnClone+1] = rect;
			createRectangle(row,column); }
		    else if(columnClone==column &&
			    myArray[row][columnClone+1].getFill()!=myArray[row][column].getFill()) {
		    }
		    //if tiles can't combine
		    else {moveHelper2(row,columnClone,column); }	}
		column--; }
	    row++;}
    }
    
    /**
     *A helper method to move the tiles to the right when they don't combine
     *@param row as current row
     *@param columnClone as the destination
     *@param column as current column
     */
    public void moveHelper2(int row, int columnClone, int column) {
	root.getChildren().remove(myArray[row][columnClone]);
	translate.setByX(xPosition[row][columnClone]-xPosition[row][column]);
	Rectangle r1 = new Rectangle(xPosition[row][columnClone],yPosition[row][columnClone],100,100);
	r1.setFill(myArray[row][column].getFill());
	myArray[row][columnClone] = r1;
	translate.setDuration(Duration.millis(600));
	translate.setNode(myArray[row][column]);
	translate.play();  
	Rectangle r = new Rectangle(xPosition[row][column],yPosition[row][column],100,100);
	r.setFill(squareImg);
	myArray[row][column]= r;
	root.getChildren().add(r);
    }
    
    /**
     *This method moves all the tile to the left and distribute those in the right places
     *when plays hit up. It also resets the array the stores image pattern.
     */
    public void moveUp() {
	int row = 0;
	int column = 0;
	while (column<4) {
 	    row = 0;
	    while (row<4) {
		if (myArray[row][column].getFill()!=squareImg && yPosition[row][column]>10.0) {
		    int rowClone = row;
		    while (rowClone>0) {
			if (myArray[rowClone-1][column].getFill()==squareImg) {
 			    rowClone--;}
			else {
			    break;} }
		    translate = new TranslateTransition();
		    //if tiles can combine
		    if (rowClone>0
			&& myArray[rowClone-1][column].getFill()==myArray[row][column].getFill()) {
			root.getChildren().remove(myArray[rowClone-1][column]);
			Rectangle rect = new Rectangle(xPosition[rowClone-1][column],
						       yPosition[rowClone-1][column],100,100);
			int numb = findMatchedPattern(myArray[rowClone-1][column].getFill());
			rect.setFill(pattern[numb]);
			updateScore(findMatchedPattern(myArray[rowClone-1][column].getFill()));
			translate.setByY(-(yPosition[row][column]-yPosition[rowClone-1][column]));
			PauseTransition pause = new PauseTransition(Duration.millis(900));
			int row1 = rowClone-1;
			int column1 = column;
			pause.setOnFinished(event ->{
					    root.getChildren().remove(myArray[row1][column1]);
					    root.getChildren().add(rect);  } );
			pause.play(); 
			translate.setDuration(Duration.millis(600));
			translate.setNode(myArray[row][column]);
			translate.play();
			myArray[rowClone-1][column] = rect;
			createRectangle(row,column);  }
		    else if (rowClone==row &&
			     myArray[rowClone-1][column].getFill()!=myArray[row][column].getFill()){
		    }//if tiles can't combine
		    else { moveHelper3(rowClone,row,column);   }}
		row++;  }
	    column++;	}
    }
    
    /**
     *A helper method to move the tiles up  when they don't combine
     *@param row as current row
     *@param rowClone as the destination
     *@param column as current column
     */
    public void moveHelper3(int rowClone, int row, int column) {
	root.getChildren().remove(myArray[rowClone][column]);
	translate.setByY(yPosition[rowClone][column]-yPosition[row][column]);
	Rectangle r1 = new Rectangle(xPosition[rowClone][column],yPosition[rowClone][column],100,100);
	r1.setFill(myArray[row][column].getFill());
	myArray[rowClone][column] = r1;
	translate.setDuration(Duration.millis(600));
	translate.setNode(myArray[row][column]);
	translate.play();  
	Rectangle r = new Rectangle(xPosition[row][column],yPosition[row][column],100,100);
	r.setFill(squareImg);
	myArray[row][column]= r;
	root.getChildren().add(r);
    }
    
    /**
     *This method moves all the tile to the left and distribute those in the right places
     *when plays hit down. It also resets the array the stores image pattern.
     */
    public void moveDown() {
	int row = 3;
	int column = 0;
	while (column<4) {
	    row = 3;
	    while (row>=0) {
		if (myArray[row][column].getFill()!=squareImg && yPosition[row][column]<340.0) {
		    int rowClone = row;
		    while (rowClone<3) {
			if (myArray[rowClone+1][column].getFill()==squareImg) {
 			    rowClone++;}
			else {    break;}}
		    translate = new TranslateTransition();
		    //if tiles can combine
		    if (rowClone<3 &&
			myArray[rowClone+1][column].getFill()==myArray[row][column].getFill()) {
			root.getChildren().remove(myArray[rowClone+1][column]);
			Rectangle rect = new Rectangle(xPosition[rowClone+1][column],
						       yPosition[rowClone+1][column],100,100);
			int numb = findMatchedPattern(myArray[rowClone+1][column].getFill());
			rect.setFill(pattern[numb]);
			updateScore(findMatchedPattern(myArray[rowClone+1][column].getFill()));
			translate.setByY(-(yPosition[row][column]-yPosition[rowClone+1][column]));
			PauseTransition pause = new PauseTransition(Duration.millis(900));
			int row1 = rowClone+1;
			int column1 = column;
			pause.setOnFinished(event ->{
				root.getChildren().remove(myArray[row1][column1]);
				root.getChildren().add(rect); } );
			pause.play(); 
			translate.setDuration(Duration.millis(600));
			translate.setNode(myArray[row][column]);
			translate.play();
			myArray[rowClone+1][column] = rect;
			createRectangle(row,column); }
		    else if (rowClone==row &&
			     myArray[rowClone+1][column].getFill()!=myArray[row][column].getFill()){
		    }
		    //if tiles can't combine
		    else { moveHelper4(row,rowClone,column); }	}
		row--;}
	    column++;}
    }
    
    /**
     *A helper method to move the tiles down when they don't combine
     *@param row as current row
     *@param rowClone as the destination
     *@param column as current column
     */
    public void moveHelper4(int row, int rowClone, int column) {
	root.getChildren().remove(myArray[rowClone][column]);
	translate.setByY(yPosition[rowClone][column]-yPosition[row][column]);
	Rectangle r1 = new Rectangle(xPosition[rowClone][column],
				     yPosition[rowClone][column],100,100);
	r1.setFill(myArray[row][column].getFill());
	myArray[rowClone][column] = r1;
	translate.setDuration(Duration.millis(600));
	translate.setNode(myArray[row][column]);
	translate.play();  
	Rectangle r = new Rectangle(xPosition[row][column],yPosition[row][column],100,100);
	r.setFill(squareImg);
	myArray[row][column]= r;
	root.getChildren().add(r);
    }
    /**
     *This method updates the score when the tiles combine
     *@param i as an index in the current score array
     */
    public void updateScore(int i) {
	this.score = this.score + scoreArray[i];
	origRoot.getChildren().remove(text);
	text = new Text("SCORE "+score);
	text.setStyle("-fx-font-weight: bold;-fx-font-size:18;");
	text.setLayoutX(300.0);
	text.setLayoutY(50.0);
	origRoot.getChildren().add(text);
    }
    /**
     *This method finds a pattern in the pattern array that matches the parameter and 
     *returns the next index of that pattern in the array
     *@param fill as an image pattern needed to be matched
     *@return the next index of the macthed pattern
     */
    public int  findMatchedPattern(Paint fill) {
	int i = 0;
	while (i<pattern.length) {
	    if (fill==pattern[i]) {;
		break;}
	    i++;
	}
	return (i+1);
    }
    /**
     *This method prints a random 2 or 4
     */
    public void printRandom1() {
	int row = (int)(Math.random()*4);
	int column = (int)(Math.random()*4);
	while (myArray[row][column].getFill()!=squareImg) {
	    row = (int)(Math.random()*4);
	    column=(int)(Math.random()*4);}
	double xPos = xPosition[row][column];
	double yPos = yPosition[row][column];
	Rectangle  r = new Rectangle(xPos,yPos,100,100);
	r.setFill(pattern[(int)(Math.random()*2)]);
	root.getChildren().add(r);
	myArray[row][column]=r;	
    }
    /**
     *This method sets up the main scene for the 2048 game
     */
    public void display() {
	stage = new Stage();
	stage.setTitle("2048!");
	stage.initModality(Modality.NONE);
	vbox = new VBox();
	root = new Group();
	setUpKeyListener();
	setUpBackground();
	setUpArray();
	setUpImgPatterns();
	setUpOrigRoot(); 
	vbox.getChildren().addAll(origRoot,root);
	vbox.setVgrow(origRoot,Priority.ALWAYS);
	scene = new Scene(vbox);
	scene.setFill(Color.PEACHPUFF);
	scene.setOnKeyPressed(keyListener);
	stage.setMaxWidth(450);
	stage.setMaxHeight(670);
	stage.setScene(scene);
	stage.sizeToScene();
	stage.show();
	stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,this::closeWindowEvent);
	root.requestFocus();
    }
    /**
     *This method implements what happens if players close the window
     *@param event as a window event
     */
    public void closeWindowEvent(WindowEvent event) {
	updateScoreTable();
    }
    /**
     *This method sets up an array of rectangles. Each rectangle contains
     *image of the tile.
     */
    public void setUpArray() {
	Thread t = new Thread(() -> {
	int row = 0;
	int column = 0;
	double x = 10.0;
	double y = 10.0;
	while (row<4) {
	    column = 0;
	    x = 10.0;
	    while (column<4) {
		double x1 = x;
		double y1 = y;
		Platform.runLater(() ->  r = new Rectangle(x1,y1,100,100));
	        Platform.runLater(() -> r.setFill(squareImg));
		xPosition[row][column] = x;
		yPosition[row][column] = y;
		int row1 = row;
		int column1 = column;
		Platform.runLater(() -> myArray[row1][column1]=r);
		x = x + 110;
		column++;}
	    y = y + 110;
	    row++;
	}
	setUpScoreArray();
	setUpGroup();
	});
	t.setDaemon(true);
	t.start();
    }
    /**
     *This method sets up the upper part of the game, including score and name
     */
    public void setUpOrigRoot() {
	origRoot = new Group();
	Rectangle r = new Rectangle(0.0,0.0,450,220);
	r.setFill(Color.CORNSILK);
	Image image = new Image("file:src/main/resources/2G3haIe");
	ImageView imageView = new ImageView(image);
	label = new Label("",imageView);
	label.setLayoutX(0.0);
	label.setLayoutY(0.0);
	text = new Text("SCORE "+score);
	text.setStyle("-fx-font-weight: bold;-fx-font-size:18;");
	text.setLayoutX(300.0);
	text.setLayoutY(50.0);
	text1 = new Text("First, please enter \nyour nickname in ONE \nword and hit Enter");
	text1.setLayoutX(240);
	text1.setLayoutY(80.0);
	text1.setStyle("-fx-font-weight: bold;-fx-font-size:15;");
	field = new TextField("");
	field.setLayoutX(225.0);
	field.setLayoutY(150.0);
	Button enter = new Button("Enter");
	enter.setLayoutX(390);
	enter.setLayoutY(150.0);
	EventHandler<ActionEvent> enterHandler = e -> {
	    name = field.getCharacters().toString();
	    field.setDisable(true);};
	enter.setOnAction(enterHandler);
	origRoot.getChildren().addAll(r,label,text,text1,field,enter);
	stage.sizeToScene();
    }
    /**
     *This method prints two 2 a two random positions
     */
     public void printRandom() {
	 int i = 0;
	 int row1 = 10;
	 int column1 = 10;
	 while (i<2) {
	     int row = (int)(Math.random()*4);
	     while (row==row1) {
		 row = (int)(Math.random()*4);}
	     int column = (int)(Math.random()*4);
	     while (column == column1) {
		 column=(int)(Math.random()*4);}
	     double xPos = xPosition[row][column];
	     double yPos = yPosition[row][column];
	     Platform.runLater(() ->  r = new Rectangle(xPos,yPos,100,100));
	     Platform.runLater(() ->r.setFill(two));
	     Platform.runLater(() -> root.getChildren().add(r));
	     int rowClone = row;
	     int columnClone = column;
	     Platform.runLater(() -> myArray[rowClone][columnClone]=r);
	     row1=row;
	     column1=column;
	     i++;
	 }
     }
    /**
     * The method prints out all the small rectangles that contain the images of the tiles
     */
    public void setUpGroup() {
	int row = 0;
	int column = 0;
	while (row<4){
	    column = 0;
	    while (column<4){
		int row1 = row;
		int column1 = column;
		Platform.runLater(() -> root.getChildren().add(myArray[row1][column1]));
		column++;}
	    row++;
	}
	printRandom();
    }
    /**
     *This method sets up the background for the game
     */
    public void setUpBackground() {
	Rectangle r = new Rectangle(0.0,0.0,450,450);
	r.setFill(Color.PEACHPUFF);
	root.getChildren().add(r);	
    }
    /**
     *This method clears up the board and redraw it again
     */
    public void drawBoard() {
	int row = 0;
	int column = 0;
	while (row<4) {
	    column = 0;
	    while (column<4) {
		root.getChildren().remove(myArray[row][column]);
		column++;
	    }
	    row++;
	}
	row = 0;
	column = 0;
	while (row<4) {
	    column = 0;
	    while (column<4) {
		root.getChildren().add(myArray[row][column]);
		column++;
	    }
	    row++;
	}
    }
    /**
     *This method goes over rows and columns to make sure if it is full.
     *@return true if the game is actually over
     */
    public boolean checkGameOver() {
	//check full
	int row = 0;
	int column = 0;
	while (row<4) {
	    column = 0;
	    while (column<4) {
		if (myArray[row][column].getFill()==squareImg) {
		    return false;}
		column++;
	    }
	    row++;
	}
	return (checkRowCol());
    }
    /**
     *This method goes over rows and columns to make sure the tiles can't be combined 
     *anymore
     *@return true if there is no similar tiles staying next to each other
     */
    public boolean checkRowCol() {
	//check rows
	int row = 0;
	int column = 0;
	while (row<4) {
	    column = 0;
	    while (column<3) {
		if (myArray[row][column].getFill()==myArray[row][column+1].getFill()){
		    return false;}
		column++;
	    }
	    row++;
	}
	//check cols
	row = 0;
	column = 0;
	while (column<4) {
	    row = 0;
	    while (row<3) {
		if (myArray[row][column].getFill() == myArray[row+1][column].getFill()) {
		    return false;}
		row++;
	    }
	    column++;
	}
	return true;
    }
}
