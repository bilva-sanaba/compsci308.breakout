/**
 * instance of PowerupSettings that implements the Powerup interface. This powerup serves to increase the paddles' size
 * Dependencies PowerupSettings
 *
 */
public class SizePower extends PowerupSettings implements Powerup {
	public static final double Scaler = 1.3333;
	public static final String SIZEPOWER_IMAGE="sizepower.gif";
	private GameSettings Game;
	/**
	 * creates a powerup imageview with file from SIZEPOWER_IMAGE at ball location with speed Yspeed
	 * @param ball
	 * @param YSpeed
	 */
	SizePower(Ball ball,GameSettings game){
		super(ball, SIZEPOWER_IMAGE);	
		Game=game;
	}
	/**resets both paddles to constant height and then increases one's width by a scaler
	 * then removes imageview of powerup
	 * @param paddle
	 */
	@Override
	public void operate(Paddle paddle) {
		for (Paddle pad : Game.getMyPaddles()){
		pad.getPaddle().setPreserveRatio(false);
		pad.getPaddle().setFitWidth(Main.PADDLESIZE);
		}
		paddle.getPaddle().setFitWidth(Main.PADDLESIZE*Scaler);
		removePowerup(Game);	
	}
}
