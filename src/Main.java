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
	public static final String TITLE = "Breakout Soccer";
	public static final String BALL_IMAGE = "soccerball.gif";
	public static final int SIZE = 600;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final String BACKGROUND_IMAGE = "field.gif";
	public static final int FRAMES_PER_SECOND = 1000;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int KEY_INPUT_SPEED = 10;
	public static final double GROWTH_RATE = 1.1;
	public static final int MAX_BALLS = 4;
	public static Random rand = new Random();
	public static final int paddleCount = 2;
	public static int Width;
	public static int Height;
	public static int player1Score=0;
	public static int player2Score=0;
	public static int player1Lives=5;
	public static int player2Lives=5;
	public static int Level =0;
	public static Powerup myPowerup;
	public static int numberBricks;
	public static int breakableBricks;



	// picks number of bouncers randomly and creates array to store them
	public static final int BOUNCERNUMBER= rand.nextInt(9)+1;
	// some things we need to remember during our game
	private Text[] texts = new Text[2];
	public static Paddle[] Paddles; 
	private static Scene myScene;
	public static Stage stage = new Stage();
	public static Group root = new Group();
	public static Ball[] myBalls = new Ball[MAX_BALLS];
	private Brick[] myBricks;
	private LayoutReader LevelsReader;
	private Timeline animation;
	// direction variables updated as needed


	/**
	 * Initialize what will be displayed and how it will be updated.
	 * @throws FileNotFoundException 
	 */
	public void read() throws FileNotFoundException{
		LevelsReader = new LayoutReader("Levels");
	}
	public int countBricks(LayoutReader LevelsReader, int Level){
		if (LevelsReader.a.length==Level){
			return 0;
		}
		int count =0;
		for (int i = 2; i<LevelsReader.a[Level].length;i=i+3){
			count++;
			if (LevelsReader.a[Level][i]==0){
				return count-1;
			}
		}
		return count;

	}
	public void initializeBrickArray(){
		if (myBricks==null){
			myBricks = new Brick[LevelsReader.maxBricks];	
		}
	}

	public void createBricks(int Level) throws FileNotFoundException{
		read();
		initializeBrickArray();
		numberBricks=countBricks(LevelsReader,Level);
		//		myBricks = new Brick[countBricks(LevelsReader,Level)];
		if (numberBricks==0){
			System.out.println("lol");
		}else{
		for (int i=0;i<countBricks(LevelsReader,Level);i++){
			myBricks[i] = new Brick(LevelsReader.a[Level][i*3],LevelsReader.a[Level][i*3+1],LevelsReader.a[Level][i*3+2]);
		}
		breakableCount();
		}

	}
	public void breakableCount(){
		breakableBricks = 0; 
		for (Brick brick : myBricks){
			if (brick!=null){
				
				if (brick.getStrength()!=0 && brick.getStrength()!=4){
					breakableBricks++;
			}
		}
		}
	}
	
	public void nextLevel() throws FileNotFoundException{
		
		Level++;
		if (myPowerup!=null){
			root.getChildren().remove(myPowerup.getImage());
			myPowerup = null;
		}
		for (int i =0; i<myBricks.length;i++){
			if (myBricks[i]!=null){
				
					root.getChildren().remove(myBricks[i].getBrick());
				
			myBricks[i] = null;
			}
		}
		createBricks(Level);	
		for (int i =0; i<Paddles.length;i++){
			if (Paddles[i]!=null){
			root.getChildren().remove(Paddles[i].getPaddle());
			Paddles[i] = null;
			}
		}
		
		createPaddles();
		
		for (int i =0; i<myBalls.length;i++){
			if (myBalls[i]!=null){
			root.getChildren().remove(myBalls[i].getBall());
			myBalls[i] = null;
			}
		}
		initializeBalls();
		addObjects();
	}

	@Override
	public void start (Stage s) throws FileNotFoundException {
		// attach scene to the stage and display it
		dimensions();
		//		Group rootSplash = new Group();
		//		Scene x = new Scene(rootSplash, Width, Height, BACKGROUND);
		//		
		//		
		//		s.setScene(x);
		//		s.show();


		createBricks(Level);
		
		this.stage =s;

		Scene scene = setupGame(Width, Height, BACKGROUND);
		Screen test = new Screen();
		test.s.setOnKeyPressed(e -> {test.changeScreen(e.getCode());	
		});
		test.s.setOnMouseClicked(e -> test.startMouseInput(e.getX(), e.getY()));
		
		s.setScene(test.s);
//		s.setScene(test.s);
		s.setTitle(TITLE);
		s.show();
		

		// attach "game loop" to timeline to play it
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> {
					try {
						step(SECOND_DELAY);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
//		animation.play();
		
	}
	public ImageView createBackground(){
		Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
		ImageView background_disp = new ImageView(background_image);
		background_disp.setFitWidth(Width);
		background_disp.setFitHeight(Height);
		background_disp.setX(0);
		background_disp.setY(0);
		return background_disp;

	}
	private void initializePaddleArray(){
		
		if (Paddles==null){
		Paddles = new Paddle[2];
		}
		
	}
	private void createPaddles(){
		initializePaddleArray();
		
		Paddles[0] = new Paddle(1, Width, Height);
		
		Paddles[1] = new Paddle(2, Width, Height);


	}
	private void initializeText(){
		texts[0] = new Text(20, 50, "Player 1 Score " + player1Score + "\nLives: " + player1Lives);
		texts[1] = new Text(490,550, "Player 2 Score " + player2Score+"\nLives: " + player2Lives);
		root.getChildren().add(texts[0]);
		root.getChildren().add(texts[1]);
	}
	private void initializeBalls(){
		myBalls[0] = new Ball(0,300,BALL_IMAGE);
		myBalls[0].getBall().setX((Width-24)/2);
		myBalls[0].getBall().setY(Height*.13);
		myBalls[0].hit1();
		myBalls[1] = new Ball(0,-300,BALL_IMAGE);
		myBalls[1].getBall().setX((Width-12)/2);
		myBalls[1].getBall().setY(Height*.85);
		myBalls[1].hit2();
	}
	private void addObjects(){
		for (Paddle paddle : Paddles){
			root.getChildren().add(paddle.getPaddle());
		}

		for (Brick brick : myBricks){
			if (brick!=null){
				root.getChildren().add(brick.getBrick());}
		}
		for (Ball ball : myBalls){
			if (ball != null){
			root.getChildren().add(ball.getBall());
			}
		}		
	}


	private Scene setupGame (int width, int height, Paint background) {
		// create one top level collection to organize the things in the scene

		// create a place to see the shapes
		setMyScene(new Scene(root, width, height, background));
		// make some shapes and set their properties
		ImageView background_disp = createBackground();
		initializeBalls();

		
		createPaddles();
		// order added to the group is the order in which they are drawn

		root.getChildren().add(background_disp);
		addObjects();

		initializeText();

		// respond to input
		getMyScene().setOnKeyPressed(e -> {
			try {
				handleKeyInput(e.getCode());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//	        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return getMyScene();
	}
	public void updateText(){
		root.getChildren().remove(texts[0]);
		root.getChildren().remove(texts[1]);
		texts[0] = new Text(20, 50, "Player 1 Score: " + player1Score + "\nLives: " + player1Lives);
		texts[1] = new Text(500, 550, "Player 2 Score " + player2Score+ "\nLives: " + player2Lives);
		root.getChildren().add(texts[0]);
		root.getChildren().add(texts[1]);
	}


	public void dimensions(){
		Width = SIZE;
		Height = SIZE;

	}
	private void step (double elapsedTime) throws FileNotFoundException{
		// update attributes of each bouncer
		
		for (Ball ball: myBalls){
			if (ball!=null){
			checkWalls(ball);
			for (Paddle paddle : Paddles){	
				paddle.checkPaddle(ball);
				paddle.checkLocation(Width);
			}
			for (Brick brick : myBricks){
				if (brick != null){
					brick.checkBricks(ball,root);
				}
			}
			ball.shiftBall();
			}
		}
		if (myPowerup != null){
			myPowerup.getImage().setY(myPowerup.getImage().getY()+myPowerup.getY()*Main.SECOND_DELAY);				
			for (Paddle paddle : Paddles){
				if (myPowerup.checkPaddleContact(paddle)) {
					myPowerup.operate(paddle);
					myPowerup=null;
					break;
					}
			}
		if (myPowerup != null){
			if (myPowerup.contactsWall()){
				Main.root.getChildren().remove(myPowerup.getImage());
				myPowerup = null;
			}
				
		}
		if (breakableBricks==0){
			nextLevel();
		}
		}
		
		
			
		updateText();
		// check for collisions
		// with shapes, can check precisely
	
}

	private void checkWalls(Ball ball){

		if (ball.getBall().getX()>=Width-ball.getBall().getBoundsInLocal().getWidth()){
			ball.leftX();
		}
		if (ball.getBall().getX()<=0) {
			ball.rightX();

		}
		if (ball.getBall().getY()>=Height-ball.getBall().getBoundsInLocal().getHeight()){
			if (ball.getYSpeed()>0 && ball.getBall().getX()>138 && ball.getBall().getX()<428){
				player2Lives = player2Lives-1;
				player1Score += 50;
			}
			ball.downY();
		}
		if (ball.getBall().getY()<=0){
			
			if (ball.getYSpeed()<0 && ball.getBall().getX()>138 && ball.getBall().getX()<428){
				player1Lives = player1Lives-1;
				player2Score += 50;
			}
			ball.upY();
		}
	}








	// What to do each time a key is pressed

	private void handleKeyInput (KeyCode code) throws FileNotFoundException {
		if (code == KeyCode.RIGHT) {
			Paddles[0].getPaddle().setX(Paddles[0].getPaddle().getX() + KEY_INPUT_SPEED);
			
		}
		else if (code == KeyCode.LEFT) {
			Paddles[0].getPaddle().setX(Paddles[0].getPaddle().getX() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.A) {
			Paddles[1].getPaddle().setX(Paddles[1].getPaddle().getX() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.D) {
			Paddles[1].getPaddle().setX(Paddles[1].getPaddle().getX() + KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.L) {
			player1Lives++;player2Lives++;
		}
		else if (code == KeyCode.Y){
			animation.stop();
		}
		else if (code == KeyCode.SPACE){
			animation.play();
			
			
		}
		else if (code == KeyCode.N){
			
			nextLevel();
		}
	}



	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
	public static Scene getMyScene() {
		return myScene;
	}
	public static void setMyScene(Scene myScene) {
		Main.myScene = myScene;
	}
}
