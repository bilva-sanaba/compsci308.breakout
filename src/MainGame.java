import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class MainGame {
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
	public static final int PaddleSize=60;
	public static final int MAX_BRICK_STRENGTH = 4;
	public static final int levelMultiplier = 10;
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
	public static int ballSpeed=100;
	private boolean cheatMode;
	public static int MaxLevels;



	// picks number of bouncers randomly and creates array to store them
	public static final int BOUNCERNUMBER= rand.nextInt(9)+1;
	// some things we need to remember during our game
	private Text[] texts = new Text[2];
	public static Scene Instructions;
	public static Paddle[] Paddles; 
	private static Scene myScene;
	public static Stage stage = new Stage();
	public static Group root = new Group();
	public static Ball[] myBalls = new Ball[MAX_BALLS];
	private Brick[] myBricks;
	private LayoutReader LevelsReader;
	private Timeline animation;
	// direction variables updated as needed
	MainGame(){
		
	}
	public void toggleCheatMode(){
		cheatMode = !cheatMode;
	}
	/**
	 * Initialize what will be displayed and how it will be updated.
	 * @throws FileNotFoundException 
	 */
	public void read() throws FileNotFoundException{
		LevelsReader = new LayoutReader("Levels");
	}
	public int countBricks(LayoutReader LevelsReader, int Level){
		int count =0;
		MaxLevels=LevelsReader.a.length;
		if (MaxLevels<=Level){
			return count;
		}
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
		System.out.println("Create Bricks called");
		read();
		initializeBrickArray();
		numberBricks=countBricks(LevelsReader,Level);
		if (numberBricks==0){
			animation.stop();
			EndScreen end = new EndScreen(1); 
			stage.setScene(end.getScene());;
		}else{
			System.out.println("test");
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
				if (brick.getStrength()!=0 && brick.getStrength()!=MAX_BRICK_STRENGTH){
					breakableBricks++;
				}
			}
		}
	}
	public void updateMyBalls(Ball ball, int index){
		myBalls[index]=ball;
	}
	public void nextLevel(int direction) throws FileNotFoundException{
		System.out.println("NextLEvel called");
		Level = Level + direction;
		ballSpeed = ballSpeed + levelMultiplier*Level;
		resetPowerup();
		for (int i =0; i<myBricks.length;i++){
			if (myBricks[i]!=null){
				root.getChildren().remove(myBricks[i].getBrick());
				myBricks[i] = null;
			}
		}
		createBricks(Level);
		
		removePaddles();

		createPaddles();

		removeBalls();
		initializeBalls();
		addObjects();
	}
	public void cheatModeOff(){
		cheatMode = false;

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
	private void checkLives(){
		if (player1Lives ==0 || player2Lives ==0){
			EndScreen Death = new EndScreen(1);
			stage.setScene(Death.getScene());
			animation.stop();
		}
	}
	private void initializeText(){
		int LevelDisp = Level+1;
		texts[0] = null;
		texts[1] = null;
		texts[0] = new Text(20, 50, "Level: " + LevelDisp + "\nPlayer 1 Score " + player1Score + "\nLives: " + player1Lives);
		texts[1] = new Text(490,550, "Player 2 Score " + player2Score+"\nLives: " + player2Lives);
		root.getChildren().add(texts[0]);
		root.getChildren().add(texts[1]);
	}
	private void initializeBalls(){
		myBalls[0] = new Ball(0,ballSpeed,BALL_IMAGE);
		myBalls[0].getBall().setX((Width-24)/2);
		myBalls[0].getBall().setY(Height*.13);
		myBalls[0].hit(1);
		myBalls[1] = new Ball(0,-ballSpeed,BALL_IMAGE);
		myBalls[1].getBall().setX((Width-12)/2);
		myBalls[1].getBall().setY(Height*.85);
		myBalls[1].hit(2);
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
		initializeText();
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
		}
			if (myPowerup != null){
				if (myPowerup.contactsWall()){
					Main.root.getChildren().remove(myPowerup.getImage());
					myPowerup = null;
				}

			}
			System.out.println(breakableBricks);
			if (!cheatMode){
				if (breakableBricks==0){
					nextLevel(1);
				}
			}
		



		updateText();
		checkLives();
	}
		// check for collisions
		// with shapes, can check precisely

	

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
	
	public void removePaddles(){
		for (int i=0; i<Paddles.length;i++){
			root.getChildren().remove(Paddles[i].getPaddle());
			Paddles[i] = null;
		}
	}
	public void addPaddles(){
		for (Paddle paddle: Paddles){
			root.getChildren().add(paddle.getPaddle());	
		}
	}
	public void removeBalls(){
		for (int i=0; i<myBalls.length;i++){
			if (myBalls[i]!=null){
				root.getChildren().remove(myBalls[i].getBall());
				myBalls[i] = null;
			}
		}
	}
	public void resetPowerup(){
		if (myPowerup!=null){
			root.getChildren().remove(myPowerup.getImage());
			myPowerup=null;
		}
	}
	public void addBalls(){
		for (Ball ball : myBalls){
			if (ball!=null){
				root.getChildren().add(ball.getBall());	
			}
		}
	}
	public void resetPaddles(){
		removePaddles();
		createPaddles();
		addPaddles();
	}
	public void resetBalls(){
		removeBalls();
		initializeBalls();
		addBalls();
	}
	public void resetMovingObjects(){
		resetPowerup();
		resetBalls();
		resetPaddles();
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
		else if (code == KeyCode.P){
			animation.pause();
		}
		else if (code == KeyCode.SPACE){
			animation.play();	
		}
		else if (code == KeyCode.N){
			nextLevel(1);
		}
		else if (code == KeyCode.R){
			resetMovingObjects();
			animation.stop();
		}
		else if (code == KeyCode.C){
			toggleCheatMode();
		}
		else if (code.isDigitKey()){ 
			animation.stop();
			@SuppressWarnings("deprecation")
			String input = code.impl_getChar();
			int digit = Integer.parseInt(input);

			nextLevel(digit-1-Level);
			Level = digit-1;
		}
	}


	public static Scene getMyScene() {
		return myScene;
	}
	public static void setMyScene(Scene myScene) {
		MainGame.myScene = myScene;
	}
	public static Scene getInstructions() {
		return Instructions;
	}
	public void setInstructions(Scene instructions) {
		Instructions = instructions;
	}
	public void SceneChange(Scene y){
		MainGame.stage.setScene(y);
	}
}
