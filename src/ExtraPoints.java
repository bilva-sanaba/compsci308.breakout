/**
 * 
 * Powerup object which gives a player extra points
 * Dependencies: PowerupSettings, Powerup, Main, Paddle
 *
 */
public class ExtraPoints extends PowerupSettings implements Powerup {
	/**
	 * creates a powerup ImageView at location of ball with speed yspeed
	 * @param ball
	 * @param YSpeed
	 */
	public static final int EXTRAPOINTS=100;
	ExtraPoints(Ball ball, int YSpeed){
		super(ball, YSpeed,"pointspower.gif");	
	}
	/*
	 * gives extrapoints to player controlling appropriate paddle and removes powerup Image
	 */
	public void operate(Paddle paddle) {
		if (paddle.getPlayer()==1){
			Main.player1Score+=EXTRAPOINTS;
		}
		else{
			Main.player2Score+=EXTRAPOINTS;
		}
		removePowerup();
	}
}
