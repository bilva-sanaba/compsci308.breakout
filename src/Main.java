import java.io.FileNotFoundException;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
	public static final int PADDLECOUNT = 2;
	public static final int PADDLESIZE=60;
	public static final int MAX_BRICK_STRENGTH = 4;
	public static final int LEVEL_MULTIPLIER = 10;
	public static final int WIDTH = SIZE;
	public static final int HEIGHT = SIZE;
	public static final int POINTS_PER_LIFE=100;
	public static final int LEFTPOST = 138;
	public static final int RIGHTPOST= 428;
	public static final int BALLADJUSTMENT = 12;
	private Text[] texts = new Text[2];
	private boolean cheatMode=false;


	
	/*
	 * Purpose: Launches game 
	 * Dependencies: depends on all classes
	 * @param Stage 
	 */
	@Override
	public void start (Stage stage) throws FileNotFoundException {
		GameSettings game = new GameSettings(stage);
		game.createBricks();
		game.setStage(stage);
		setupGame(WIDTH, HEIGHT, BACKGROUND,game);
		initializeStartScreen(game);
		initializeInstructionScreen(game);
		createAnimation(game);
	}
	private Scene setupGame (int width, int height, Paint background, GameSettings game) {
		game.setMyScene(new Scene(game.getRoot(), width, height, background));
		drawBackground(game);
		initializeBalls(game);
		initializePaddles(game);
		initializeText(game);
		addObjects(game);
		
		// respond to input
		game.getMyScene().setOnKeyPressed(e -> {
			try {
				handleKeyInput(e.getCode(),game);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		return game.getMyScene();
	}
	
	private void drawBackground(GameSettings game){
		Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
		ImageView background_disp = new ImageView(background_image);
		background_disp.setFitWidth(WIDTH);
		background_disp.setFitHeight(HEIGHT);
		background_disp.setX(0);
		background_disp.setY(0);
		game.getRoot().getChildren().add(background_disp);
	}
	private void initializeBalls(GameSettings game){
		
		game.setBall(0,new Ball(0,game.getBallSpeed()));
		game.getBall(0).getBall().setX(WIDTH/2-BALLADJUSTMENT);
		game.getBall(0).getBall().setY(HEIGHT*ExtraBall.upperPosition);
		game.getBall(0).hit(1);
		game.setBall(1,new Ball(0,-game.getBallSpeed()));
		game.getBall(1).getBall().setX(WIDTH/2-BALLADJUSTMENT);
		game.getBall(1).getBall().setY(HEIGHT*ExtraBall.lowerPosition);
		game.getBall(1).hit(2);
		
	
		
	}
	private void initializePaddles(GameSettings game){
		
		if (game.getMyPaddles()==null){
			game.setMyPaddles(new Paddle[2]);
		}
		game.setMyPaddle(0, new Paddle(1,WIDTH,HEIGHT));
		game.setMyPaddle(1, new Paddle(2,WIDTH,HEIGHT));
	}
	private void initializeText(GameSettings game){
		int LevelDisp = game.getScorekeeper().getLevel()+1;
		texts[0] = null;
		texts[1] = null;
		texts[0] = new Text(20, 50, "Level: " + LevelDisp + "\nPlayer 1 Score " + game.getScorekeeper().getPlayer1Score() + "\nLives: " + game.getScorekeeper().getPlayer1Lives());
		texts[1] = new Text(490,550, "Player 2 Score " +game.getScorekeeper().getPlayer2Score()+"\nLives: " + game.getScorekeeper().getPlayer2Lives());
	}
	private void addObjects(GameSettings game){
		addPaddles(game);
		addBricks(game);
		addBalls(game);	
		addText(game); 
	}
	private void addText(GameSettings game){
		for (Text text: texts){
			game.getRoot().getChildren().add(text);
			}
	}
	private void addBricks(GameSettings game){
		for (Brick brick : game.getMyBricks()){
			if (brick!=null){
				game.getRoot().getChildren().add(brick.getBrick());}
		}
	}
	private void removeText(GameSettings game){
		for (int i=0;i<texts.length;i++){
			game.getRoot().getChildren().remove(texts[i]);
		}
	}
	private void removePaddles(GameSettings game){
		
		for (int i=0; i<game.getMyPaddles().length;i++){
			game.getRoot().getChildren().remove(game.getMyPaddles()[i].getPaddle());
			game.setMyPaddle(i,null);
		}
	}
	private void addPaddles(GameSettings game){
		for (Paddle paddle: game.getMyPaddles()){
			if (paddle!=null){
			game.getRoot().getChildren().add(paddle.getPaddle());	
			}
		}
	}
	private void addBalls(GameSettings game){
		for (Ball ball : game.getMyBalls()){
			if (ball!=null){
				game.getRoot().getChildren().add(ball.getBall());	
			}
		}
	}
	private void removeBalls(GameSettings game){
		for (int i=0; i<game.getMyBalls().length;i++){
			if (game.getMyBalls()[i]!=null){
				game.getRoot().getChildren().remove(game.getMyBalls()[i].getBall());
				game.getMyBalls()[i] = null;
			}
		}
	}
	private void resetMovingObjects(GameSettings game){
		resetPowerup(game);
		resetBalls(game);
		resetPaddles(game);
	}
	private void resetPowerup(GameSettings game){
		if (game.getPowerup()!=null){
			game.getRoot().getChildren().remove(game.getPowerup().getImage());
			game.setPowerup(null);
		}
	}
	private void resetPaddles(GameSettings game){
		removePaddles(game);
		initializePaddles(game);
		addPaddles(game);
	}
	private void resetBalls(GameSettings game){
		removeBalls(game);
		initializeBalls(game);
		addBalls(game);
	}
	
	private void initializeStartScreen(GameSettings game){
		StartScreen start = new StartScreen(2);
		start.getScene().setOnKeyPressed(e -> {start.changeScreen(e.getCode(),game);});
		start.getScene().setOnMouseClicked(e -> start.startMouseInput(e.getX(), e.getY(),game));
		game.getStage().setScene(start.getScene());
		game.getStage().setTitle(TITLE);
		game.getStage().show();
	}
	private void initializeInstructionScreen(GameSettings game){
		InstructionScreen Instruct = new InstructionScreen(1);
		game.setInstructions(Instruct.getScene());
		game.getInstructions().setOnKeyPressed(e -> {Instruct.changeScreen(e.getCode(),game);});
	}
	private void createAnimation(GameSettings game){
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> {
					try {
						step(SECOND_DELAY,game);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				});
		game.setAnimation(new Timeline());
		game.getAnimation().setCycleCount(Timeline.INDEFINITE);
		game.getAnimation().getKeyFrames().add(frame);
	}
	/**
	 * each step ball position is updated, powerups are updated, bricks are updated, level is checked, score is updated
	 * and lives are checked for ending game
	 * @param elapsedTime
	 * @throws FileNotFoundException
	 */
	public void step (double elapsedTime, GameSettings game) throws FileNotFoundException{
		updateBallPos(game);
		updatePowerup(game);
		nextLevelCheck(game);
		updateText(game);
		checkLives(game);
	}	
	private void updateBallPos(GameSettings game){
		for (Ball ball: game.getMyBalls()){
			if (ball!=null){
				checkWalls(ball,game);
				checkAllPaddles(ball,game);
				checkAllBricks(ball,game);
				ball.shiftBall();
			}
		}
	}
	private void checkAllPaddles(Ball ball,GameSettings game){
		for (Paddle paddle : game.getMyPaddles()){	
			if (paddle!=null){
			paddle.checkPaddle(ball,game);
			paddle.checkLocation(WIDTH);
			}
		}
	}
	private void checkAllBricks(Ball ball,GameSettings game){
		for (Brick brick :game.getMyBricks()){
			if (brick != null){
				brick.checkBricks(ball,game);
			}
		}
	}
	private void checkWalls(Ball ball, GameSettings game){
		if (ball.getBall().getX()>=WIDTH-ball.getBall().getBoundsInLocal().getWidth()){
			ball.leftX();
		}
		if (ball.getBall().getX()<=0) {
			ball.rightX();
		}
		if (ball.getBall().getY()>=HEIGHT-ball.getBall().getBoundsInLocal().getHeight()){
			checkTopGoal(ball,game);
			ball.downY();
		}
		if (ball.getBall().getY()<=0){
			checkBottomGoal(ball,game);
			ball.upY();
		}
	}
	private void checkTopGoal(Ball ball, GameSettings game){
		if (ball.getYSpeed()>0 && ball.getBall().getX()>LEFTPOST && ball.getBall().getX()<RIGHTPOST){
			game.getScorekeeper().updatePlayer1Score(POINTS_PER_LIFE);
			game.getScorekeeper().reducePlayer2Lives();
		}
	}
	private void checkBottomGoal(Ball ball, GameSettings game){
		if (ball.getYSpeed()<0 && ball.getBall().getX()>LEFTPOST && ball.getBall().getX()<RIGHTPOST){
			game.getScorekeeper().updatePlayer2Score(POINTS_PER_LIFE);
			game.getScorekeeper().reducePlayer1Lives();
		}
	}
	
	private void updatePowerup(GameSettings game){
		if (game.getPowerup() != null){
			game.getPowerup().getImage().setY(game.getPowerup().getImage().getY()+game.getPowerup().getY()*Main.SECOND_DELAY);				
			for (Paddle paddle : game.getMyPaddles()){
				if (paddle!=null){
				if (game.getPowerup().checkPaddleContact(paddle)) {
					game.getPowerup().operate(paddle);
					game.setPowerup(null);
					break;
				}
				}
			}
		}
			if (game.getPowerup() != null){
				if (game.getPowerup().contactsWall()){
					game.getRoot().getChildren().remove(game.getPowerup().getImage());
					game.setPowerup(null);
				}

			}
	}
	private void nextLevelCheck(GameSettings game) throws FileNotFoundException{
		if (!cheatMode){
			if (game.getLevelSettings().getNumberBreakable()==0){
				nextLevel(1,game);
			}
		}
	}
	public void updateText(GameSettings game){
		for (Text t: texts){
		game.getRoot().getChildren().remove(t);
		}
		initializeText(game);
		addText(game);
	}
	private void checkLives(GameSettings game){
		if (game.getScorekeeper().getPlayer1Lives() ==0 || game.getScorekeeper().getPlayer2Lives() ==0){
			EndScreen Death = new EndScreen(1,game.getScorekeeper());
			game.getStage().setScene(Death.getScene());
			game.getAnimation().stop();
		}
	}
	/**
	 * creates next level of bricks and resets all positions
	 * @param direction
	 * @throws FileNotFoundException
	 */
	private void nextLevel(int direction, GameSettings game) throws FileNotFoundException{		
		game.getScorekeeper().increaseLevel(direction);
		game.changeBallSpeed(LEVEL_MULTIPLIER*game.getScorekeeper().getLevel());
		resetPowerup(game);
		removeBricks(game);
		game.createBricks();
		removePaddles(game);
		initializePaddles(game);
		removeBalls(game);
		initializeBalls(game);
		removeText(game);
		initializeText(game);
		addObjects(game);
	}
	
	private void removeBricks(GameSettings game){
		for (int i =0; i<game.getMyBricks().length;i++){
			if (game.getMyBricks()[i]!=null){
				game.getRoot().getChildren().remove(game.getMyBricks()[i].getBrick());
				game.getMyBricks()[i] = null;
			}
		}
	}
	
	
	
	public void toggleCheatMode(){
		cheatMode = !cheatMode;
	}
	public void updateMyBalls(Ball ball, int index, GameSettings game){
		game.getMyBalls()[index]=ball;
	}
	
	// What to do each time a key is pressed
	private void handleKeyInput (KeyCode code,GameSettings game) throws FileNotFoundException {
		if (code == KeyCode.RIGHT) {
			game.getMyPaddles()[0].getPaddle().setX(game.getMyPaddles()[0].getPaddle().getX() + KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.LEFT) {
			game.getMyPaddles()[0].getPaddle().setX(game.getMyPaddles()[0].getPaddle().getX() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.A) {
			game.getMyPaddles()[1].getPaddle().setX(game.getMyPaddles()[1].getPaddle().getX() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.D) {
			game.getMyPaddles()[1].getPaddle().setX(game.getMyPaddles()[1].getPaddle().getX() + KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.L) {
			game.getScorekeeper().setPlayer1Lives(game.getScorekeeper().getPlayer1Lives()+1);
			game.getScorekeeper().setPlayer2Lives(game.getScorekeeper().getPlayer2Lives()+1);
		}
		else if (code == KeyCode.P){
			game.getAnimation().pause();
		}
		else if (code == KeyCode.SPACE){
			game.getAnimation().play();	
		}
		else if (code == KeyCode.N){
			nextLevel(1,game);
		}
		else if (code == KeyCode.R){
			resetMovingObjects(game);
			game.getAnimation().stop();
		}
		else if (code == KeyCode.C){
			toggleCheatMode();
		}
		else if (code.isDigitKey()){ 
			game.getAnimation().stop();
			@SuppressWarnings("deprecation")
			String input = code.impl_getChar();
			int digit = Integer.parseInt(input);
			nextLevel(digit-1-game.getScorekeeper().getLevel(),game);
			game.getScorekeeper().setLevel(digit-1);
		}
	}

	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
	
}
