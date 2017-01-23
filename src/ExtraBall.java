/**
 * 
 * type of Powerup which creates an extra ball
 *Dependencies: PowerupSettings, Powerup, Main,paddle
 */
public class ExtraBall extends PowerupSettings implements Powerup {
	public static final double upperPosition=.13;
	public static final double lowerPosition=.85;
	/**
	 * creates a Powerup ImageView at the location of ball moving down at YSpeed
	 * @param ball
	 * @param YSpeed
	 */
	ExtraBall(Ball ball, int YSpeed){
		super(ball, YSpeed, "extraballpower.gif");
	}
	/**
	 * removes the powerup imageview from scene and creates a new additional ball
	 */
	public void operate(Paddle paddle) {
		Ball ball = initializeNewBall(paddle);
		addBall(ball);
		removePowerup();	
	}
	private Ball initializeNewBall(Paddle paddle){
		Ball ball = new Ball(0,Main.ballSpeed,Main.BALL_IMAGE);
		ball.getBall().setX(paddle.getPaddle().getX()+paddle.getPaddle().getImage().getWidth()/2);
		ball.hit(this.getPlayer());
		setBallPos(ball);
		return ball;
	}
	private void setBallPos(Ball ball){
		if (this.getPlayer()==1){
			ball.upY();
			ball.getBall().setY(upperPosition*Main.Height);
		}else{
			ball.downY();
			ball.getBall().setY(lowerPosition*Main.Height);	
		}
	}
	private void addBall(Ball ball){
		for (int i=0;i<Main.myBalls.length;i++){
			if (Main.myBalls[i]==null){
				Main.myBalls[i]=ball;
				Main.root.getChildren().add(ball.getBall());
				break;
			}
		}	
	}
}

