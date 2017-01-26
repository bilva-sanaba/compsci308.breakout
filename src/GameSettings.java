import java.io.FileNotFoundException;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameSettings {
	private Scorekeeper score;
	private LevelSettings levelsetting;
	private Stage stage = new Stage();
	private Group root = new Group();
	private Scene Instructions;
	private Scene Start;
	private Scene myScene;
	private Paddle[] myPaddles; 
	private Ball[] myBalls = new Ball[Main.MAX_BALLS];
	private Powerup myPowerup;
	private int ballSpeed=100;
	private Brick[] myBricks;
	private int MaxLevels;
	private LayoutReader LevelsReader;
	private Timeline animation;
	public static final String LevelsFile = "Levels";
	GameSettings(Stage gameStage) throws FileNotFoundException{
		score = new Scorekeeper();
		levelsetting = new LevelSettings();
		stage = gameStage;
		this.createBricks();
	}
	/** Purpose: Reads file and creates array storing brick information for given level
	 * determines if no more levels are remaining
	 * 
	 * @param Level - determines which level of bricks to be read
	 * @throws FileNotFoundException
	 */
	public void createBricks() throws FileNotFoundException{
		read();
		initializeMyBricks();
		this.getLevelSettings().setNumberBricks(countBricks(LevelsReader));
		endGameCheck();
		fillMyBricks();
		breakableCount();
	}
	/*Creates a LayoutReader that is initialized from a text file that determines
	 * where and what type bricks are in each level. This info is stored in the LayoutReader
	 * as an array.
	 * 
	 */
	public void read() throws FileNotFoundException{
		try {
			LevelsReader = new LayoutReader(LevelsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* Determines number of bricks in level by going through the LayoutReaders BrickSpecs
	 *  
	 * @return int Number of Bricks in Level
	 */
	public int countBricks(LayoutReader LevelsReader){
		int Level =this.getScorekeeper().getLevel();
		int numberOfBricks =0;
		MaxLevels=LevelsReader.getBrickSpecs().length;
		if (MaxLevels<=Level){
			return numberOfBricks;
		}
		for (int i = 2; i<LevelsReader.getBrickSpecs()[Level].length;i=i+3){
			numberOfBricks++;
			if (LevelsReader.getBrickSpecs()[Level][i]==0){
				return numberOfBricks-1;
			}
		}
		return numberOfBricks;
	}
	/**
	 * creates an Array of Bricks that can store, at most, the largest number of Bricks in any level
	 */
	private void initializeMyBricks(){
		if (getMyBricks()==null){
			setMyBricks(new Brick[LevelsReader.getMaxBricks()]);	
		}
	}
	/**
	 * counts the number of Bricks that can still be broken in level
	 */
	private void breakableCount(){
		this.getLevelSettings().setNumberBreakable(0);
		for (Brick brick : getMyBricks()){
			if (brick!=null){
				if (brick.getStrength()!=0 && brick.getStrength()!=Main.MAX_BRICK_STRENGTH){
					this.getLevelSettings().setNumberBreakable(this.getLevelSettings().getNumberBreakable()+1);;
				}
			}
		}
	}
	/**
	 * checks if no breakable bricks are remaining and if there are no levels left
	 * if none displays end scene, stops animation
	 * @param numberBricks
	 */
	private void endGameCheck(){
		if (this.getLevelSettings().getNumberBricks()==0){
			getAnimation().stop();
			EndScreen end = new EndScreen(1,this.getScorekeeper()); 
			this.getStage().setScene(end.getScene());
		}
	}
	/**
	 * fills myBricks with Brick objects based on text reading
	 * @param Level
	 */
	private void fillMyBricks(){
		int Level = this.getScorekeeper().getLevel();
		for (int i=0;i<countBricks(LevelsReader);i++){
			getMyBricks()[i] = new Brick(LevelsReader.getBrickSpecs()[Level][i*3],LevelsReader.getBrickSpecs()[Level][i*3+1],LevelsReader.getBrickSpecs()[Level][i*3+2]);
		}
	}
	
	public int getBallSpeed(){
		return ballSpeed;
	}
	public void changeBallSpeed(int increment){
		ballSpeed = ballSpeed + increment; 
	}
		
	public Powerup getPowerup(){
		return myPowerup;
	}
	public void setPowerup(Powerup powerup){
		myPowerup = powerup;
	}
	public Scene getInstructions(){
		return Instructions;
	}
	public void setMyPaddle(int index, Paddle paddle){
		myPaddles[index]=paddle;
	}
	public void setBall(int index, Ball ball){
		myBalls[index]=ball;
	}
	public Ball getBall(int index){
		return myBalls[index];
	}
	public Ball[] getMyBalls(){
		return myBalls;
	}
	public Paddle[] getMyPaddles(){
		return myPaddles;
	}
	public void setMyPaddles(Paddle[] paddles){
		myPaddles=paddles;
	}
	public Scene getStart(){
		return Start;
	}
	public void setStart(Scene scene){
		Start = scene;
	}
	public void setInstructions(Scene instructions) {
		Instructions = instructions;
	}

	public Group getRoot(){
		return root;
	}
	public void addRoot(Node node){
		root.getChildren().add(node);
	}
	public void removeFromRoot(Node node){
		root.getChildren().remove(node);
	}
	public Scorekeeper getScorekeeper(){
		return score;
	}
	public LevelSettings getLevelSettings(){
		return levelsetting;
	}
	public Stage getStage(){
		return stage;
	}
	public void setScene(Scene scene){
		stage.setScene(scene);
	}
	public void setStage(Stage s){
		stage = s;
	}
	public Scene getMyScene(){
		return myScene;
	}
	public void setMyScene(Scene s){
		myScene =s;
	}
	public void SceneChange(Scene scene){
		stage.setScene(scene);
	}
	public Brick[] getMyBricks() {
		return myBricks;
	}
	public void setMyBricks(Brick[] myBricks) {
		this.myBricks = myBricks;
	}
	public Timeline getAnimation() {
		return animation;
	}
	public void setAnimation(Timeline animation) {
		this.animation = animation;
	}	
}