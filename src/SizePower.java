/**
 * instance of PowerupSettings that implements the Powerup interface. This powerup serves to increase the paddles' size
 * Dependencies PowerupSettings
 *
 */
public class SizePower extends PowerupSettings implements Powerup {
	public static final double Scaler = 1.3333;
	public static final String SIZEPOWER_IMAGE="sizepower.gif";
	/**
	 * creates a powerup imageview with file from SIZEPOWER_IMAGE at ball location with speed Yspeed
	 * @param ball
	 * @param YSpeed
	 */
	SizePower(Ball ball, int YSpeed){
		super(ball, YSpeed,SIZEPOWER_IMAGE);	
	}
	/**resets both paddles to constant height and then increases one's width by a scaler
	 * then removes imageview of powerup
	 * @param paddle
	 */
	@Override
	public void operate(Paddle paddle) {
		for (Paddle pad : Main.Paddles){
		pad.getPaddle().setPreserveRatio(false);
		pad.getPaddle().setFitWidth(Main.PaddleSize);
		}
		paddle.getPaddle().setFitWidth(Main.PaddleSize*Scaler);
		removePowerup();	
	}
}
