import java.util.Random;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A basic example JavaFX program for the first lab.
 * 
 * @author Robert C. Duvall
 */

public class Main extends Application{
	 public static final String TITLE = "Example JavaFX";
	    public static final String BALL_IMAGE = "soccerball.gif";
	    public static final int SIZE = 600;
	    public static final Paint BACKGROUND = Color.WHITE;
	    public static final String BACKGROUND_IMAGE = "field.gif";
	    public static final int FRAMES_PER_SECOND = 60;
	    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	    public static final int KEY_INPUT_SPEED = 5;
	    public static final double GROWTH_RATE = 1.1;
	    public static Random rand = new Random();
	    public static int Width;
	    public static int Height;
	    
	    // picks number of bouncers randomly and creates array to store them
	    public static final int BOUNCERNUMBER= rand.nextInt(9)+1;
	    // some things we need to remember during our game
	    private Scene myScene;
	    private ImageView myPaddle1;
	    private ImageView myPaddle2;
	    private Ball myBall;
	    // direction variables updated as needed

	    
	    /**
	     * Initialize what will be displayed and how it will be updated.
	     */
	    @Override
	    public void start (Stage s) {
	        // attach scene to the stage and display it
	    	dimensions();
	    	Scene scene = setupGame(Width, Height, BACKGROUND);
	        s.setScene(scene);
	        s.setTitle(TITLE);
	        s.show();
	        // attach "game loop" to timeline to play it
	        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
	                                      e -> step(SECOND_DELAY));
	        Timeline animation = new Timeline();
	        animation.setCycleCount(Timeline.INDEFINITE);
	        animation.getKeyFrames().add(frame);
	        animation.play();
	    }
	    private ImageView createBackground(){
	    	Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
	        ImageView background_disp = new ImageView(background_image);
            background_disp.setFitWidth(Width);
            background_disp.setFitHeight(Height);
            background_disp.setX(0);
            background_disp.setY(0);
            return background_disp;
	    	
	    }
	    private void createPaddle1(){
	    	Image paddle = new Image(getClass().getClassLoader().getResourceAsStream("paddle.gif"));
	        myPaddle1 = new ImageView(paddle);
	        myPaddle1.setX(Width/2);
	        myPaddle1.setY(Height*9/10);
	           	
	    }
	    private void createPaddle2(){
	    	Image paddle = new Image(getClass().getClassLoader().getResourceAsStream("paddle.gif"));
	        myPaddle2 = new ImageView(paddle);
	        myPaddle2.setX(Width/2);
	        myPaddle2.setY(Height/10);
	           	
	    }
	    
	    private Scene setupGame (int width, int height, Paint background) {
	        // create one top level collection to organize the things in the scene
	        Group root = new Group();
	        // create a place to see the shapes
	        myScene = new Scene(root, width, height, background);
	        // make some shapes and set their properties
	        ImageView background_disp = createBackground();
            myBall = new Ball(200,500,BALL_IMAGE);
            myBall.getBall().setX(Width/2);
            myBall.getBall().setY(Height/2);
            createPaddle1();
            createPaddle2();
	        // order added to the group is the order in which they are drawn
	        root.getChildren().add(background_disp);
	        root.getChildren().add(myBall.getBall());
	        root.getChildren().add(myPaddle1);
	        root.getChildren().add(myPaddle2);
	        // respond to input
	        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//	        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
	        return myScene;
	    }
	    public void dimensions(){
	    	Width = SIZE;
	    	Height = 3/2*SIZE;
	    	
	    }
	    private void step (double elapsedTime) {
	        // update attributes of each bouncer
	    	checkWalls(myBall);
	    	checkPaddles(myBall);
	    	myBall.getBall().setX(myBall.getBall().getX()+myBall.XSpeed*elapsedTime);
	    	myBall.getBall().setY(myBall.getBall().getY()+myBall.YSpeed*elapsedTime);
	    	myBall.getBall().setRotate(myBall.getBall().getRotate() - 1);
	        // check for collisions
	        // with shapes, can check precisely
	    }
	    private void checkWalls(Ball ball){
	    	if (ball.getBall().getX()>=Width-ball.getBall().getBoundsInLocal().getWidth() || ball.getBall().getX()<=0 ){
	    		ball.updateX();
	    	}
	    	if (ball.getBall().getY()>=Height-ball.getBall().getBoundsInLocal().getHeight() || ball.getBall().getY()<=0 ){
	    		ball.updateY();
	    	}
	    }
	    private void checkPaddles(Ball ball){
	    	if (ball.getBall().getBoundsInParent().intersects(myPaddle1.getBoundsInParent())){
	    		ball.updateY();
	    	}
	    	if (ball.getBall().getBoundsInParent().intersects(myPaddle2.getBoundsInParent())){
	    		ball.updateY();
	    	}
	    }
	    // What to do each time a key is pressed
	    private void handleKeyInput (KeyCode code) {
	        if (code == KeyCode.RIGHT) {
	            myPaddle1.setX(myPaddle1.getX() + KEY_INPUT_SPEED);
	        }
	        else if (code == KeyCode.LEFT) {
	            myPaddle1.setX(myPaddle1.getX() - KEY_INPUT_SPEED);
	        }
	        else if (code == KeyCode.A) {
	            myPaddle2.setX(myPaddle2.getX() - KEY_INPUT_SPEED);
	        }
	        else if (code == KeyCode.D) {
	            myPaddle2.setX(myPaddle2.getX() + KEY_INPUT_SPEED);
	        }
	    }

	    // What to do each time a key is pressed
//	    private void handleMouseInput (double x, double y) {
//	        if (myPaddle2.contains(x, y)) {
//	            myPaddle2.setScaleX(myPaddle2.getScaleX() * GROWTH_RATE);
//	            myPaddle2.setScaleY(myPaddle2.getScaleY() * GROWTH_RATE);
//	        }
//	    }

	    /**
	     * Start the program.
	     */
	    public static void main (String[] args) {
	        launch(args);
	    }
}
