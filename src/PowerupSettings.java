// This entire file is part of my masterpiece.
// Bilva Sanaba
//This is an abstract class that all my Powerup Classes Extend
//It contains methods that the main class calls for all powerups regardless of type to determine
//what should happen to the game and to the powerup
//I believe it is well designed for several reasons, first it uses no static instance variables and no magic numbers
//Several helper methods are used so that all code is easily readable. 
//I also believe it is a good example of good design because it avoids the use of one Powerup class with several different
// types of powerups, while also avoiding duplicate code when making multiple seperate powerup classes. 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * Class that all Powerups extend in this game
 * Dependencies: Ball, Paddle, GameSettings
 */
public abstract class PowerupSettings {
	public static final int dropSpeed = 30;
	public static final int percentPowerups = 60;
	public static final int numberPowerupTypes = 3;
	private int YDirection;
	private ImageView Powerup_Image;
	private int player;
	/**
	 * Creates an Imageview of the powerup based on parameter String
	 * The location of this imageview is set to the location of the ball
	 * Assumptions: PowerupImage is the name of an image file
	 * @param ball
	 * @param YSpeed
	 * @param PowerupImage
	 */
	PowerupSettings(Ball ball, String PowerupImage){
		setPowerupImage(ball, PowerupImage);
		setPowerupSpeed();	
	}
	/**
	 * Sets private variable player to the playerId of the last paddle to hit the ball
	 * Creates ImageView of Powerup based on String parameter and sets its location to the ball location
	 * @param ball
	 * @param PowerupImage
	 */
	private void setPowerupImage(Ball ball,String PowerupImage){
		player= ball.getLastHit();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(PowerupImage));
		Powerup_Image = new ImageView(image);
		Powerup_Image.setX(ball.getBall().getX());
		Powerup_Image.setY(ball.getBall().getY());
	}
	/**
	 * Sets the speed of the Powerup to a constant, to the direction of the player who hit the ball that generated the powerup
	 */
	private void setPowerupSpeed(){
		if (player == 1){
			YDirection =-dropSpeed;
		}else {
			YDirection = dropSpeed;
		}	
	}
	/**
	 * Updates GameSettings so that the Powerup Image is removed
	 */
	public static void removePowerup(GameSettings game){
		game.getRoot().getChildren().remove(game.getPowerup().getImage());
	}
	/**
	 * returns YSpeed of Powerup Image
	 * @return
	 */
	public int getY(){
		return YDirection;
	}
	/**
	 * Checks if the Powerup ImageView has made contact with a paddle
	 * @param paddle
	 * @return
	 */
	public boolean checkPaddleContact(Paddle paddle) {
		return (this.Powerup_Image.getBoundsInParent().intersects(paddle.getPaddle().getBoundsInParent()));	
	}
	/**
	 * Returns which player generated the Powerup
	 * @return
	 */
	public int getPlayer(){
		return player;
	}
	/**
	 * returns ImageView of Powerup
	 * @return
	 */
	public ImageView getImage(){
		return Powerup_Image;
	}
	/**
	 * determines if Powerup ImageView contacts a wall
	 * Assumptions: Powerup has no X velocity
	 * @return
	 */
	public boolean contactsWall(){
		return (this.getImage().getY()<0 || this.getImage().getY()>Main.HEIGHT);
	}
	/**
	 * Creates a powerup and sets it as the GameSettings myPowerup which leads to it being added to the scene
	 * @param ball
	 * @param root
	 * @param number
	 */
	public static void makePowerup(Ball ball, int number, GameSettings game){
		createPowerup(ball,number,game);
		addPowerup(game);
	}
	
	/**
	 * Generates a random powerup at percentPowerups% of the time with each type of powerup having an equal possibility
	 * @param ball
	 * @param number
	 * @param game
	 */
	private static void createPowerup(Ball ball, int number, GameSettings game){
		if (game.getPowerup()==null){
			if (number>0 && number < percentPowerups/numberPowerupTypes){
				game.setPowerup(new ExtraPoints(ball, game));
			}else {
				if (number >percentPowerups/numberPowerupTypes && number <2*percentPowerups/numberPowerupTypes){

					game.setPowerup(new ExtraBall(ball,game));
				}
				else{
					if (number>2*percentPowerups/numberPowerupTypes && number <3*percentPowerups/numberPowerupTypes){
						game.setPowerup(new SizePower(ball,game)); 
					}
				}
			}

		}
	}
	/**
	 * Adds a Powerup to the GameSettings' root
	 * @param game
	 */
	private static void addPowerup(GameSettings game){
		if (game.getPowerup()!=null){
			removePowerup(game);
			game.getRoot().getChildren().add(game.getPowerup().getImage());
		}
	}
}