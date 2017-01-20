import java.awt.Canvas;
import java.awt.Font;
import java.io.FileNotFoundException;
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
import javafx.scene.text.Text;
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
	public static final int FRAMES_PER_SECOND = 500;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int KEY_INPUT_SPEED = 10;
	public static final double GROWTH_RATE = 1.1;
	public static Random rand = new Random();
	public static int Width;
	public static int Height;
	public static int player1Score=0;
	public static int player2Score=0;
	public static int player1Lives=3;
	public static int player2Lives=3;

	// picks number of bouncers randomly and creates array to store them
	public static final int BOUNCERNUMBER= rand.nextInt(9)+1;
	// some things we need to remember during our game
	private Scene myScene;
	private Group root = new Group();
	private ImageView myPaddle1;
	private ImageView myPaddle2;
	private Ball myBall;
	private Brick[] myBricks;
	private LayoutReader LevelsReader;
	// direction variables updated as needed


	/**
	 * Initialize what will be displayed and how it will be updated.
	 * @throws FileNotFoundException 
	 */
	public void read() throws FileNotFoundException{
		LevelsReader = new LayoutReader("Levels");
	}
	public int countBricks(LayoutReader LevelsReader, int Level){
		int count =0;
		for (int i = 2; i<LevelsReader.a[Level].length;i=i+3){
			count++;
			if (LevelsReader.a[Level][i]==0){
				return count-1;
			}
		}
		return count;
		
		
	}
	public void createBricks(int Level) throws FileNotFoundException{
		read();
		
		myBricks = new Brick[countBricks(LevelsReader,Level)];
		
		for (int i=0;i<myBricks.length;i++){
			myBricks[i] = new Brick(LevelsReader.a[Level][i*3],LevelsReader.a[Level][i*3+1],LevelsReader.a[Level][i*3+2]);
			

		}
		
	}
	
	@Override
	public void start (Stage s) throws FileNotFoundException {
		// attach scene to the stage and display it
		createBricks(1);
		
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

		// create a place to see the shapes
		myScene = new Scene(root, width, height, background);
		// make some shapes and set their properties
		ImageView background_disp = createBackground();
		myBall = new Ball(200,50,BALL_IMAGE);
		myBall.getBall().setX(1);
		myBall.getBall().setY(1);
		Text t = new Text(10, 50, "This is a test");
		

		createPaddle1();
		createPaddle2();
		// order added to the group is the order in which they are drawn
		
		root.getChildren().add(background_disp);
		root.getChildren().add(myBall.getBall());
		root.getChildren().add(myPaddle1);
		root.getChildren().add(myPaddle2);
		root.getChildren().add(t);
		for (Brick brick : myBricks){
			if (brick!=null){
			root.getChildren().add(brick.getBrick());}
		}
		// respond to input
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		//	        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}
	public void dimensions(){
		Width = SIZE;
		Height = SIZE;

	}
	private void step (double elapsedTime) {
		// update attributes of each bouncer
		checkWalls(myBall);
		checkPaddles(myBall);
		for (Brick brick : myBricks){
			checkBricks(myBall,brick);}
		myBall.getBall().setX(myBall.getBall().getX()+myBall.getXSpeed()*elapsedTime);
		myBall.getBall().setY(myBall.getBall().getY()+myBall.getYSpeed()*elapsedTime);
		myBall.getBall().setRotate(myBall.getBall().getRotate() - 1);
		// check for collisions
		// with shapes, can check precisely
	}
	private void checkWalls(Ball ball){

		if (ball.getBall().getX()>=Width-ball.getBall().getBoundsInLocal().getWidth() || ball.getBall().getX()<=0 ){
			ball.reverseX();
		}
		if (ball.getBall().getY()>=Height-ball.getBall().getBoundsInLocal().getHeight() || ball.getBall().getY()<=0 ){
			ball.reverseY();
		}
	}
	private void checkPaddles(Ball ball){
		boolean atRightBorder;
		boolean atLeftBorder; 
		boolean atBottomBorder;
		boolean atTopBorder;
		if (ball.getBall().getBoundsInParent().intersects(myPaddle1.getBoundsInParent())){
			atLeftBorder = myPaddle1.getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
			atRightBorder = myPaddle1.getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
			atTopBorder = myPaddle1.getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
			atBottomBorder = myPaddle1.getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

			if (atRightBorder && !atLeftBorder) {
				ball.rightX();	
				ball.hit1();
			}else{
				if (!atRightBorder && atLeftBorder){
					ball.leftX();
					ball.hit1();
				} else{ 
					if (atBottomBorder && !atTopBorder){
						ball.downY();	
						ball.hit1();
					}
					else{
						if (!atBottomBorder && atTopBorder) {
							ball.upY();
							ball.hit1();
						}
					}
				}
			}
		}
		if (ball.getBall().getBoundsInParent().intersects(myPaddle2.getBoundsInParent())){
			atLeftBorder = myPaddle2.getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
			atRightBorder = myPaddle2.getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
			atTopBorder = myPaddle2.getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
			atBottomBorder = myPaddle2.getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

			if (atRightBorder && !atLeftBorder) {
				ball.rightX();	
				ball.hit2();
			}else{
				if (!atRightBorder && atLeftBorder){
					ball.leftX();
					ball.hit2();
				} else{ 
					if (atBottomBorder && !atTopBorder){
						ball.downY();	
						ball.hit2();
					}
					else{
						if (!atBottomBorder && atTopBorder) {
							ball.upY();
							ball.hit2();
						}
					}
				}
			}
		}
	}



	//fix so it takes full list and stops when hit found
	private void checkBricks(Ball ball, Brick brick){
		if (ball.getBall().getBoundsInParent().intersects(brick.getBrick().getBoundsInParent())){
			if (brick.getStrength()>0){
				boolean atLeftBorder = brick.getBrick().getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
				boolean atRightBorder = brick.getBrick().getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
				boolean atTopBorder = brick.getBrick().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
				boolean atBottomBorder = brick.getBrick().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

				if (atRightBorder && !atLeftBorder) {
					if (ball.getXSpeed()<0){
						updateBrick(brick,ball);
						ball.rightX();
					}					
				}else{
					if (!atRightBorder && atLeftBorder){
						if (ball.getXSpeed()>0){
							updateBrick(brick,ball);
							ball.leftX();	
						}
					} else{ 
						if (atBottomBorder && !atTopBorder){
							if (ball.getYSpeed()>0){
								updateBrick(brick,ball);
								ball.downY();
							}
						}
						else{
							if (!atBottomBorder && atTopBorder) {
								if (ball.getYSpeed()<0){
									updateBrick(brick,ball);
									ball.upY();
								}
							}
						}
					}
				}
			}
		}
	}
		private void updateBrick(Brick brick, Ball ball){
			if (brick.getStrength()!=4){
				if (ball.lastHit()){
					player1Score = player1Score+10*brick.getStrength();
				} else{
					player2Score = player2Score+10*brick.getStrength();
				}
			}
			
			root.getChildren().remove(brick.getBrick());
			brick.updateBrick();
			if (!brick.checkBrick()){
				root.getChildren().add(brick.getBrick());}
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
