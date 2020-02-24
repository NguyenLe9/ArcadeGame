package cs1302.arcade;
import javafx.scene.paint.*;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import javafx.util.Duration;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.shape.CubicCurveTo; 
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path; 
import javafx.animation.PathTransition; 
import java.io.InputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.*;
import java.util.Arrays;
import java.util.Comparator;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.*;
import javafx.geometry.Pos;
/**
This is a class the represents the three main scenes of an Arcade App
*/
public class ArcadeApp extends Application {
    Circle c;
    int count;
    Stage stage;
    Group root;           // main container
    Text t;
    Label[] text;
    int[] maxScore =new int[9];
    String[] name = new String[50];
    String[] game = new String[50];
    Integer[] score = new Integer[50];
    Timeline timeline;
    Button scoreButton;
    Button nButton;
    Button sButton;
    Group root1;
    Group root2;
    File file = new File("src/main/java/cs1302/arcade/score.txt");
    Label label;
    Label teamLabel;
    Label author;
    Button cont;
    Rectangle r;
    String link; 
    Button goBack;
    
    /** 
     * This method opens the application.
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {
	
	this.stage = stage;
	root1 = new Group();
	root = new Group();
	setUpIntroGroup();
	
        Scene scene = new Scene(root1, 640, 480);
	scene.getStylesheets().add("file:src/main/resources/Theme.css");
        stage.setTitle("cs1302-arcade!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        // the group must request input focus to receive key events
        // @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--
        root.requestFocus();
    } // start

    /**
     * This method sets up the group for the menu page. 
     */
    public void setUpGroup() {
	Rectangle r = new Rectangle(0.0,0.0,640.0,480.0);
	r.setFill(new ImagePattern(new Image("file:src/main/resources/2YbvKVx")));
	Label label = new Label("WELCOME!");
	label.setStyle("-fx-text-fill: white;");
	label.setLayoutX(200.0);
	label.setLayoutY(50.0);
	makeSButton();
	makeNButton();
	makeScoreButton();
	root.getChildren().addAll(r,label,sButton,nButton,scoreButton);
    }

    /**
     * Set up for a button that opens the spaceInvaders app.
     */
    public void makeSButton(){
	sButton = new Button("1 SPACE INVADERS");
        sButton.setLayoutX(100.0);
        sButton.setLayoutY(150.0);
        sButton.setStyle("-fx-text-fill: white;");
	sButton.setOnAction(e-> {SpaceInvaders game = new SpaceInvaders();
                game.display();});
    }
    
    /**
     * Set up the button that opens the 2048 app.
     */
    public void makeNButton(){
	nButton = new Button("2 CLASSIC 2048");
        nButton.setLayoutX(100.0);
        nButton.setLayoutY(250.0);
        nButton.setStyle("-fx-text-fill: white;");
	nButton.setOnAction(e-> {Game2048 game = new Game2048();
		game.display();});
    }

    /**
     * Set up the button that opens the scoreboard.
     */
    public void makeScoreButton(){
	scoreButton = new Button("< TOP PLAYERS >");
        scoreButton.setLayoutX(100.0);
        scoreButton.setLayoutY(350.0);
        scoreButton.setStyle("-fx-text-fill: white;");
        scoreButton.setOnAction(e ->  {
                root2 = new Group();
                setUpHighScore();
                Scene scene = new Scene(root2, 640, 480);
                scene.getStylesheets().add("file:src/main/resources/Theme.css");
                stage.setTitle("cs1302-arcade!");
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
            });
    }

    /**
     * This method creates the group for the into page.
     */
    public void setUpIntroGroup() {
	r = new Rectangle(0.0,0.0,640.0,480.0);
	link = "80s-background-80s-background-stock-footage-video-shutterstock-6295";
	makeLabels();
	createTimeline();
	makeCont();
	root1.getChildren().addAll(r,label,teamLabel,cont,author,c);
	root1.requestFocus();
    }

    /**
     * This method makes the labels introducing the project.
     */
    public void makeLabels(){
	r.setFill(new ImagePattern(new Image(link)));
        label = new Label("A product from team Wamen:");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
        label.setLayoutX(100.0);
        label.setLayoutY(50.0);
        teamLabel = new Label("CS1302-ARCADE");
        teamLabel.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
        teamLabel.setLayoutX(200.0);
        teamLabel.setLayoutY(85.0);
	author = new Label("Authors: Sam Wolfe && Nguyen Le");
        author.setStyle("-fx-text-fill: white; -fx-font-size: 15;");
        author.setLayoutX(150);
        author.setLayoutY(450);
    }

    /**
     * This method sets up the timeline for the spaceship.
     */
    public void createTimeline(){
	timeline = new Timeline();
        KeyFrame key1 = new KeyFrame(Duration.seconds(0.05),evt -> teamLabel.setVisible(false));
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.1),evt -> teamLabel.setVisible(true));
        timeline.getKeyFrames().addAll(key1,key2);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        c = new Circle(25.0,25.0,25,new ImagePattern(new Image("file:src/main/resources/2H282VW")));
        setUpCircle(); 
        TranslateTransition translate = new TranslateTransition();
        translate.setByY(-300);
        translate.setDuration(Duration.millis(2000));
        translate.setNode(author);
        translate.play();
    }

    /**
     * This method makes the continue button that allows the user to 
     * enter the menu page.
     */
    public void makeCont(){
	cont = new Button("Continue >>");
        cont.setLayoutX(400.0);
        cont.setLayoutY(400.0);
        cont.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
        cont.setOnAction(e -> {
                root = new Group();
                setUpGroup();
                Scene scene = new Scene(root, 640, 480);
                scene.getStylesheets().add("file:src/main/resources/Theme.css");
                stage.setTitle("cs1302-arcade!");
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
            }   );
    }

    /**
     * This method sets up the spaceship and its movement patterns.
     */
    public void setUpCircle() {
	//Instantiating the path class  
	Path path = new Path(); 
	//Creating the MoveTo path element 
	MoveTo moveTo = new MoveTo(160, 460);
	//Creating 1st line 
	LineTo line1 = new LineTo(390, 25);        
	LineTo line2 = new LineTo(126,460); 
	LineTo line3 = new LineTo(50,25);        
	LineTo line4 = new LineTo(465, 320);        
	LineTo line5 = new LineTo(600,25);  
	//Adding the path elements to Observable list of the Path class 
	path.getElements().add(moveTo); 
	path.getElements().addAll(line1, line2, line3, line4, line5);
      //Creating a path transition 
	PathTransition pathTransition = new PathTransition(); 
	pathTransition.setDuration(Duration.millis(6000)); 
	pathTransition.setNode(c); 
	pathTransition.setPath(path);  
	pathTransition.setOrientation(PathTransition.OrientationType.
				      ORTHOGONAL_TO_TANGENT); 
	pathTransition.setCycleCount(Animation.INDEFINITE); 
	pathTransition.setAutoReverse(true);
	pathTransition.play(); 
    }

    /** 
     * This method sets up the highscore page.
     */
    public void setUpHighScore() {
	Rectangle r = new Rectangle(0.0,0.0,640.0,480.0);
	String link = "file:src/main/resources/2IZecbz";
	r.setFill(new ImagePattern(new Image(link)));
	Label label = new Label("HIGH SCORES");
	label.setStyle("-fx-text-fill: pink; -fx-font-size: 25;");
	label.setLayoutX(200.0);
	label.setLayoutY(50.0);	
	timeline = new Timeline();
	KeyFrame key1 = new KeyFrame(Duration.seconds(0.05),evt -> label.setVisible(false));
        KeyFrame key2 = new KeyFrame(Duration.seconds(0.1),evt -> label.setVisible(true));
	timeline.getKeyFrames().addAll(key1,key2);
	timeline.setCycleCount(Animation.INDEFINITE);
	timeline.play();
	goBack();
	root2.getChildren().addAll(r,label,goBack);
	root2.requestFocus();
	readFile();
    }

    /**
     * This method creates the goBack button to go back to the menu page.
     */
    public void goBack(){
	goBack = new Button("<< Go Back");
        goBack.setLayoutX(10.0);
        goBack.setLayoutY(400.0);
        goBack.setStyle("-fx-text-fill: pink; -fx-font-size: 20;");
        goBack.setOnAction(e -> {
                root = new Group();
                setUpGroup();
                Scene scene = new Scene(root, 640, 480);
                scene.getStylesheets().add("file:src/main/resources/Theme.css");
                stage.setTitle("cs1302-arcade!");
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
            }   );
    }

    /** 
     * This method reads the game apps' files to enter into the high score chart.
     */
    public void readFile() {
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    int i = 0;
	    while (line != null)                 // read the score file line by line
		{
		    line = line.trim();
		    String[] info = new String[3];
		    info = line.split("\\s+");
		    game[i] = info[0];
		    name[i] = info[1];
		    score[i] = Integer.parseInt(info[2]);
		    line = reader.readLine();
		    i++;
		}
		reader.close();
		displayTable();
	}
	catch (IOException ex) {
	    System.err.println("ERROR reading file");
	}
    }

    /**
     * This method displays the highscore chart.
     */    
    public void displayTable() {
	this.count = 0;
	int i = 0;
	while (i<50 && game[i]!=null) {
	    this.count++;
	    i++;}
	selectionSort(score,0,this.count-1,Integer::compareTo);
	text = new Label[this.count];
	i = 0;
	double y = 95.0;
	i = text.length-1;
	while (i>=0) {
	    if (game[i].equals("2048")) {
		text[i] = new Label(game[i] + "          " + name[i] + " " + score [i]);}
	    else {	
		text[i] = new Label(game[i] + " " + name[i] + " " + score [i]);}
	    Label newText = text[i];
	    newText.setLayoutX(90.0);
	    newText.setLayoutY(y);
	    newText.setStyle("-fx-text-fill: white; -fx-font-size: 25;");
	    root2.getChildren().add(newText);
	    y = y + 40;
	    i--;}
    }
    /**
     *This method select the minimum score in the score file.
     *@param array as an array of score
     *@param lo as the first index
     *@param hi as the last index
     *@param c as the comparator
     *@param <T> as the type of the array
     */
    public  <T> void selectMin(T[] array, int lo, int hi, Comparator<T> c) {
	T min = array[lo];
	int index = 0;
	String n = "";
	String g = "";
	int i = lo;
	while (i<=hi) {
	    if (c.compare(min,array[i])>0) {
		index = i;
		min = array[i];
		n = name[i];
		g = game[i];
	    }
	    i++;
	}
	if (min!=array[lo]) {
	    T temp = array[lo];
	    String nameTemp = name[lo];
	    String gameTemp = game[lo];
	    array[lo] = min;
	    name[lo] = n;
	    game[lo] = g;
	    array[index] = temp;
	    name[index] = nameTemp;
	    game[index] = gameTemp;}
    }
    /**
     *This method arrange the array from the lowest score to the highest
     *@param array as an array of score
     *@param lo as the first index
     *@param hi as the last index
     *@param c as the comparator
     *@param <T> as type of the array
     */
    public  <T> void selectionSort(T[] array, int lo, int hi, Comparator<T> c) {
	while (lo<hi) {
	    selectMin(array,lo,hi,c);
	    lo++;}
    }    
} // ArcadeApp
