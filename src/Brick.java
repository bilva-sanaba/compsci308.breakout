import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**Brick class which contains methods for dealing with ball collisions updating scores, updating brick properties
 * etc. 
 * Dependencies: ball, Main
 * 
 *
 */
public class Brick {
	private ImageView Brick_Image;
	private int Strength; 
	private int xLocation;
	private int yLocation;
	/**
	 * Brick is created with an ImageView using an image picked based on Strength
	 * brick image is set to (x,y)
	 * @param x
	 * @param y
	 * @param strength
	 */
	public Brick(int x, int y, int strength){
		Strength = strength;
		pickImage();
		xLocation= x;
		yLocation = y;
		Brick_Image.setX(xLocation);
		Brick_Image.setY(yLocation);
	}
	private void pickImage(){
		Image image;
		switch (Strength){
		case 1: image = new Image(getClass().getClassLoader().getResourceAsStream("brick1.gif"));
				Brick_Image = new ImageView(image);
				break;
		case 2: image = new Image(getClass().getClassLoader().getResourceAsStream("brick2.gif"));
				Brick_Image = new ImageView(image);	
				break;
		case 3: image = new Image(getClass().getClassLoader().getResourceAsStream("brick4.gif"));
				Brick_Image = new ImageView(image);
				break;
		case Main.MAX_BRICK_STRENGTH: image = new Image(getClass().getClassLoader().getResourceAsStream("brick3.gif"));
				Brick_Image = new ImageView(image);
				break;
		}
	}
	/**
	 * returns strength or brick ie number of hits till it dissapears
	 * @return
	 */
	public int getStrength(){
		return Strength;
	}
	/**
	 * returns ImageView displayed on screen in representation of Brick object
	 * @return
	 */
	public ImageView getBrick(){
		return Brick_Image;
	}
	private boolean checkBrickStrength(){
		return (Strength == 0);
	}
	private void lowerStrength(){
		Strength = Strength -1;
	}
	public void updateBrick(Ball ball, GameSettings game){
		if (this.getStrength()!=Main.MAX_BRICK_STRENGTH){
			updateScore(ball, game.getScorekeeper());
			this.weakenBrick(game.getRoot(),game.getLevelSettings());
		}
	}
	private void updateScore(Ball ball, Scorekeeper score){
		if (ball.getLastHit()==1){
			score.updatePlayer1Score(this.getStrength()*10);
		} else{
			score.updatePlayer2Score(this.getStrength()*10);
		}
	}
	private void weakenBrick(Group root, LevelSettings levelset){
		if (Strength == Main.MAX_BRICK_STRENGTH ){
			return; 
		} else {
			lowerStrength();
			updateBrickImage(root, levelset);	
		}
	}
	private void updateBrickImage(Group root, LevelSettings levelset){
		root.getChildren().remove(this.getBrick());
		if (!this.checkBrickStrength()){
			pickImage();
			Brick_Image.setX(xLocation);
			Brick_Image.setY(yLocation);
			root.getChildren().add(this.getBrick());
		}
			else{
				levelset.setNumberBreakable(levelset.getNumberBreakable()-1);
		}
	}
	/**
	 * checks if a ball intersects a brick, if so, it determines which direction the ball should go
	 * and sends it that way. It also updates the brick so that its image and strentgh are correct posthit
	 * Additionally, it generate a powerup when hit.
	 * @param ball
	 * @param root
	 */
	public void checkBricks(Ball ball,GameSettings game){
		if (ball.getBall().getBoundsInParent().intersects(this.getBrick().getBoundsInParent())){
			if (this.getStrength()>0){
				generatePowerup(ball,game);
				boolean atLeftBorder = checkLeftBorder(ball);
				boolean atRightBorder = checkRightBorder(ball);
				boolean atTopBorder = checkTopBorder(ball);
				boolean atBottomBorder = checkBottomBorder(ball);
				allPossibleUpdates(atLeftBorder, atRightBorder, atTopBorder,atBottomBorder,ball, game);
			}
		}
	}
	private void generatePowerup(Ball ball, GameSettings game){
		int randomNumber = Main.rand.nextInt(100);
		PowerupSettings.makePowerup(ball,randomNumber, game);
	}
	private void allPossibleUpdates(boolean atLeftBorder, boolean atRightBorder, 
			boolean atTopBorder, boolean atBottomBorder, Ball ball, GameSettings game){
		if (shouldDirectionChange(atLeftBorder, atRightBorder, atTopBorder, atBottomBorder, ball)){
			updateBrick(ball, game);
			changeDirection(atLeftBorder, atRightBorder, atTopBorder, atBottomBorder, ball);
		}
	}
	private boolean shouldDirectionChange(boolean atLeftBorder, boolean atRightBorder, boolean atTopBorder, boolean atBottomBorder, Ball ball){
		return (atRightBorder && !atLeftBorder && ball.getXSpeed()<0
				|| !atRightBorder && atLeftBorder && ball.getXSpeed()>0
				|| atBottomBorder && !atTopBorder && ball.getYSpeed()>0
				||!atBottomBorder && atTopBorder && ball.getYSpeed()<0);
	}
	private void changeDirection(boolean atLeftBorder, boolean atRightBorder, boolean atTopBorder, boolean atBottomBorder, Ball ball){
		if (atRightBorder){
			ball.rightX();
		}
		else {
			if (atLeftBorder){
				ball.leftX();
			}
			else{
				if (atTopBorder){
					ball.upY();
				}
				else {
					if (atBottomBorder){
						ball.downY();
					}
				}
			}
		}
	}
	private boolean checkLeftBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMinX() >= (ball.getBall().getBoundsInLocal().getMaxX());
	}
	private boolean checkRightBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMaxX() <= (ball.getBall().getBoundsInLocal().getMinX());
	}
	private boolean checkTopBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
	}
	private boolean checkBottomBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());
	}
	
	
}