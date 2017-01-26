/**
 * 
 * type of Powerup which creates an extra ball
 *Dependencies: PowerupSettings, Powerup, Main,paddle
 */
public class ExtraBall extends PowerupSettings implements Powerup {
	public static final double upperPosition=.13;
	public static final double lowerPosition=.85;
	public static final String EXTRABALL_IMAGE="extraballpower.gif";
	public static final int extraballXSpeed = 0;
	private GameSettings Game;
	/**
	 * creates a Powerup ImageView at the location of ball moving down at YSpeed
	 * @param ball
	 * @param YSpeed
	 */
	ExtraBall(Ball ball,GameSettings game){
		super(ball, EXTRABALL_IMAGE);
		Game = game;
	}
	/**
	 * removes the powerup imageview from scene and creates a new additional ball
	 */
	public void operate(Paddle paddle) {
		Ball extraBall = initializeNewBall(paddle);
		addBall(extraBall,Game);
		removePowerup(Game);	
	}
	private Ball initializeNewBall(Paddle paddle){
		Ball ball = new Ball(extraballXSpeed,Game.getBallSpeed());
		ball.getBall().setX(paddle.getPaddle().getX()+paddle.getPaddle().getImage().getWidth()/2);
		ball.hit(this.getPlayer());
		setBallPos(ball);
		return ball;
	}
	private void setBallPos(Ball ball){
		if (this.getPlayer()==1){
			ball.upY();
			ball.getBall().setY(upperPosition*Main.HEIGHT);
		}else{
			ball.downY();
			ball.getBall().setY(lowerPosition*Main.HEIGHT);	
		}
	}
	private void addBall(Ball ball,GameSettings game){
		for (int i=0;i<game.getMyBalls().length;i++){
			if (game.getMyBalls()[i]==null){
				game.getMyBalls()[i]=ball;
				game.getRoot().getChildren().add(ball.getBall());
				break;
			}
		}	
	}
}

