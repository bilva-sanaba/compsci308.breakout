// This entire file is part of my masterpiece.
// Bilva Sanaba
//This is simply an example of a powerup which, like all other powerups, extends PowerupSettings and implements Powerup
/**
 * 
 * Powerup object which gives a player extra points
 * Dependencies: PowerupSettings, Powerup, Ball, Paddle, GameSettings
 *
 */
public class ExtraPoints extends PowerupSettings implements Powerup {
	private GameSettings Game;
	public static final String EXTRAPOINTS_IMAGE="pointspower.gif";
	/**
	 * Creates a powerup ImageView at the location of the ball with speed yspeed
	 * @param ball
	 * @param YSpeed
	 */
	public static final int EXTRAPOINTS=100;
	ExtraPoints(Ball ball, GameSettings game){
		super(ball,EXTRAPOINTS_IMAGE);	
		Game = game;
	}
	/*
	 * gives Extra points to the player controlling the paddle and removes the powerup Image
	 * Assumptions: method is called if checkPaddleContact() == true
	 */
	public void operate(Paddle paddle) {
		if (paddle.getPlayer()==1){
			Game.getScorekeeper().updatePlayer1Score(EXTRAPOINTS);
		}
		else{
			Game.getScorekeeper().updatePlayer2Score(EXTRAPOINTS);
		}
		removePowerup(Game);
	}
}
