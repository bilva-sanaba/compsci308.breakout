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

/**@author Bilva Sanaba
 * Purpose: Runs Application
 * Dependencies: depends on all other classes
 * 
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
	public static final Random rand = new Random();
	public static final int paddleCount = 2;
	public static final int PaddleSize=60;
	public static final int MAX_BRICK_STRENGTH = 4;
	public static final int levelMultiplier = 10;
	public static final int Width = SIZE;
	public static final int Height = SIZE;
	public static final int LifePoints=100;
	public static final int LeftPost = 138;
	public static final int RightPost = 428;
	public static final int ballAdjustment = 12;
	public static int player1Score=0;
	public static int player2Score=0;
	public static int player1Lives=10;
	public static int player2Lives=10;
	public static int LevelCount =0;
	public static Powerup myPowerup;
	public static int numberBricks;
	public static int breakableBricks;
	public static int ballSpeed=100;
	
	private boolean cheatMode=false;
	private int MaxLevels;
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


	
	/*
	 * Purpose: Launches game 
	 * Dependencies: depends on all classes
	 * @param Stage 
	 */
	@Override
	public void start (Stage stage) throws FileNotFoundException {
		createBricks(LevelCount);
		Main.stage =stage;
		setupGame(Width, Height, BACKGROUND);
		initializeStartScreen();
		initializeInstructionScreen();
		createAnimation();
	}
	/** Purpose: Reads file and creates array storing brick information for given level
	 * determines if no more levels are remaining
	 * 
	 * @param Level - determines which level of bricks to be read
	 * @throws FileNotFoundException
	 */
	public void createBricks(int Level) throws FileNotFoundException{
		read();
		initializeMyBricks();
		numberBricks=countBricks(LevelsReader,Level);
		endGameCheck(numberBricks);
		fillMyBricks(Level);
		breakableCount();
	}
	/*Creates a LayoutReader for reading brick layouts from text file
	 * 
	 */
	public void read() throws FileNotFoundException{
		try {
			LevelsReader = new LayoutReader("Levels");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* Determines number of bricks in level
	 * 
	 * @return int Number of Bricks in Level
	 */
	public int countBricks(LayoutReader LevelsReader, int Level){
		int count =0;
		MaxLevels=LevelsReader.getBrickSpecs().length;
		if (MaxLevels<=Level){
			return count;
		}
		for (int i = 2; i<LevelsReader.getBrickSpecs()[Level].length;i=i+3){
			count++;
			if (LevelsReader.getBrickSpecs()[Level][i]==0){
				return count-1;
			}
		}
		return count;
	}	
	
	private void initializeMyBricks(){
		if (myBricks==null){
			myBricks = new Brick[LevelsReader.maxBricks];	
		}
	}
	/**
	 * counts the number of Bricks that can still be broken in level
	 */
	private void breakableCount(){
		breakableBricks = 0; 
		for (Brick brick : myBricks){
			if (brick!=null){
				if (brick.getStrength()!=0 && brick.getStrength()!=MAX_BRICK_STRENGTH){
					breakableBricks++;
				}
			}
		}
	}
	/**
	 * checks if no breakable bricks are remaining and if there are no levels left
	 * if none dislpays end scene
	 * @param numberBricks
	 */
	private void endGameCheck(int numberBricks){
		if (numberBricks==0){
			animation.stop();
			EndScreen end = new EndScreen(1,LevelCount); 
			stage.setScene(end.getScene());;
		}
	}
	/**
	 * fills myBricks with Brick objects based on text reading
	 * @param Level
	 */
	private void fillMyBricks(int Level){
		for (int i=0;i<countBricks(LevelsReader,Level);i++){
			myBricks[i] = new Brick(LevelsReader.getBrickSpecs()[Level][i*3],LevelsReader.getBrickSpecs()[Level][i*3+1],LevelsReader.getBrickSpecs()[Level][i*3+2]);
		}
	}
	/**
	 * creates game Scene with background, balls paddles, score diplay, and bricks, does various actions based on keycode
	 * @param width
	 * @param height
	 * @param background
	 * @return
	 */
	private Scene setupGame (int width, int height, Paint background) {
		setMyScene(new Scene(root, width, height, background));
		drawBackground();
		initializeBalls();
		initializePaddles();
		initializeText();
		addObjects();
		
		// respond to input
		getMyScene().setOnKeyPressed(e -> {
			try {
				handleKeyInput(e.getCode());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		return getMyScene();
	}
	
	private void drawBackground(){
		Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
		ImageView background_disp = new ImageView(background_image);
		background_disp.setFitWidth(Width);
		background_disp.setFitHeight(Height);
		background_disp.setX(0);
		background_disp.setY(0);
		root.getChildren().add(background_disp);
	}
	private void initializeBalls(){
		myBalls[0] = new Ball(0,ballSpeed,BALL_IMAGE);
		myBalls[0].getBall().setX(Width/2-ballAdjustment);
		myBalls[0].getBall().setY(Height*ExtraBall.upperPosition);
		myBalls[0].hit(1);
		myBalls[1] = new Ball(0,-ballSpeed,BALL_IMAGE);
		myBalls[1].getBall().setX(Width/2-ballAdjustment);
		myBalls[1].getBall().setY(Height*ExtraBall.lowerPosition);
		myBalls[1].hit(2);
	}
	private void initializePaddles(){
		if (Paddles==null){
			Paddles = new Paddle[2];
		}
		Paddles[0] = new Paddle(1, Width, Height);
		Paddles[1] = new Paddle(2, Width, Height);
	}
	private void initializeText(){
		int LevelDisp = LevelCount+1;
		texts[0] = null;
		texts[1] = null;
		texts[0] = new Text(20, 50, "Level: " + LevelDisp + "\nPlayer 1 Score " + player1Score + "\nLives: " + player1Lives);
		texts[1] = new Text(490,550, "Player 2 Score " + player2Score+"\nLives: " + player2Lives);
	}
	private void addObjects(){
		addPaddles();
		addBricks();
		addBalls();	
		addText(); 
	}
	private void addText(){
		for (Text text: texts){
			root.getChildren().add(text);
			}
	}
	private void addBricks(){
		for (Brick brick : myBricks){
			if (brick!=null){
				root.getChildren().add(brick.getBrick());}
		}
	}
	private void removeText(){
		for (int i=0;i<texts.length;i++){
			root.getChildren().remove(texts[i]);
		}
	}
	private void removePaddles(){
		for (int i=0; i<Paddles.length;i++){
			root.getChildren().remove(Paddles[i].getPaddle());
			Paddles[i] = null;
		}
	}
	private void addPaddles(){
		for (Paddle paddle: Paddles){
			root.getChildren().add(paddle.getPaddle());	
		}
	}
	private void addBalls(){
		for (Ball ball : myBalls){
			if (ball!=null){
				root.getChildren().add(ball.getBall());	
			}
		}
	}
	private void removeBalls(){
		for (int i=0; i<myBalls.length;i++){
			if (myBalls[i]!=null){
				root.getChildren().remove(myBalls[i].getBall());
				myBalls[i] = null;
			}
		}
	}
	private void resetMovingObjects(){
		resetPowerup();
		resetBalls();
		resetPaddles();
	}
	private void resetPowerup(){
		if (myPowerup!=null){
			root.getChildren().remove(myPowerup.getImage());
			myPowerup=null;
		}
	}
	private void resetPaddles(){
		removePaddles();
		initializePaddles();
		addPaddles();
	}
	private void resetBalls(){
		removeBalls();
		initializeBalls();
		addBalls();
	}
	
	private void initializeStartScreen(){
		StartScreen start = new StartScreen(2);
		start.getScene().setOnKeyPressed(e -> {start.changeScreen(e.getCode());});
		start.getScene().setOnMouseClicked(e -> start.startMouseInput(e.getX(), e.getY()));
		stage.setScene(start.getScene());
		stage.setTitle(TITLE);
		stage.show();
	}
	private void initializeInstructionScreen(){
		InstructionScreen Instruct = new InstructionScreen(1);
		setInstructions(Instruct.getScene());
		Instructions.setOnKeyPressed(e -> {Instruct.changeScreen(e.getCode());});
	}
	private void createAnimation(){
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> {
					try {
						step(SECOND_DELAY);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}
	/**
	 * each step ball position is updated, powerups are updated, bricks are updated, level is checked, score is updated
	 * and lives are checked for ending game
	 * @param elapsedTime
	 * @throws FileNotFoundException
	 */
	private void step (double elapsedTime) throws FileNotFoundException{
		updateBallPos();
		updatePowerup();
		nextLevelCheck();
		updateText();
		checkLives();
	}	
	private void updateBallPos(){
		for (Ball ball: myBalls){
			if (ball!=null){
				checkWalls(ball);
				checkAllPaddles(ball);
				checkAllBricks(ball,root);
				ball.shiftBall();
			}
		}
	}
	private void checkAllPaddles(Ball ball){
		for (Paddle paddle : Paddles){	
			paddle.checkPaddle(ball);
			paddle.checkLocation(Width);
		}
	}
	private void checkAllBricks(Ball ball, Group root){
		for (Brick brick : myBricks){
			if (brick != null){
				brick.checkBricks(ball,root);
			}
		}
	}
	private void checkWalls(Ball ball){
		if (ball.getBall().getX()>=Width-ball.getBall().getBoundsInLocal().getWidth()){
			ball.leftX();
		}
		if (ball.getBall().getX()<=0) {
			ball.rightX();
		}
		if (ball.getBall().getY()>=Height-ball.getBall().getBoundsInLocal().getHeight()){
			checkTopGoal(ball);
			ball.downY();
		}
		if (ball.getBall().getY()<=0){
			checkBottomGoal(ball);
			ball.upY();
		}
	}
	private void checkTopGoal(Ball ball){
		if (ball.getYSpeed()>0 && ball.getBall().getX()>LeftPost && ball.getBall().getX()<RightPost){
			player2Lives = player2Lives-1;
			player1Score += LifePoints;
		}
	}
	private void checkBottomGoal(Ball ball){
		if (ball.getYSpeed()<0 && ball.getBall().getX()>LeftPost && ball.getBall().getX()<RightPost){
			player1Lives = player1Lives-1;
			player2Score += LifePoints;
		}
	}
	
	private void updatePowerup(){
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
	}
	private void nextLevelCheck() throws FileNotFoundException{
		if (!cheatMode){
			if (breakableBricks==0){
				nextLevel(1);
			}
		}
	}
	public void updateText(){
		for (Text t: texts){
		root.getChildren().remove(t);
		}
		initializeText();
		addText();
	}
	private void checkLives(){
		if (player1Lives ==0 || player2Lives ==0){
			EndScreen Death = new EndScreen(1,LevelCount);
			stage.setScene(Death.getScene());
			animation.stop();
		}
	}
	/**
	 * creates next level of bricks and resets all positions
	 * @param direction
	 * @throws FileNotFoundException
	 */
	private void nextLevel(int direction) throws FileNotFoundException{		
		LevelCount = LevelCount + direction;
		ballSpeed = ballSpeed + levelMultiplier*LevelCount;
		resetPowerup();
		removeBricks();
		createBricks(LevelCount);
		removePaddles();
		initializePaddles();
		removeBalls();
		initializeBalls();
		removeText();
		initializeText();
		addObjects();
	}
	
	private void removeBricks(){
		for (int i =0; i<myBricks.length;i++){
			if (myBricks[i]!=null){
				root.getChildren().remove(myBricks[i].getBrick());
				myBricks[i] = null;
			}
		}
	}
	
	
	
	public void toggleCheatMode(){
		cheatMode = !cheatMode;
	}
	public void updateMyBalls(Ball ball, int index){
		myBalls[index]=ball;
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

			nextLevel(digit-1-LevelCount);
			LevelCount = digit-1;
		}
	}
	/*
	 * returns myScene from the class Main
	 */
	public static Scene getMyScene() {
		return myScene;
	}
	/*
	 * sets Main.myScene to @param
	 * @param myScene
	 */
	public static void setMyScene(Scene myScene) {
		Main.myScene = myScene;
	}
	/**
	 * Returns scene created from the InstructionScreen Class
	 * @return
	 */
	public static Scene getInstructions() {
		return Instructions;
	}
	/**
	 * Sets Main.Instructions to @param
	 * @param instructions
	 */
	public void setInstructions(Scene instructions) {
		Instructions = instructions;
	}
	/**
	 * Sets stage scene to @param
	 * @param scene
	 */
	public void SceneChange(Scene scene){
		Main.stage.setScene(scene);
	}
	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
	
}
