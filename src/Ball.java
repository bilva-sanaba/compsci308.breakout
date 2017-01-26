import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/*Purpose: This is the general ball class with functionality to create a ball and change its attributes
 * such as speed and location as well as last player hit
 * Dependencies: Paddle, Brick
 */
public class Ball {
	public static final int BallSize= 20;
	private int XSpeed;
	private int YSpeed;
	private ImageView Ball_Image;
	private int lastHit;
	/**Creates ball object with an imageview from the BALL_IMAGE at (x,y) coordinates
	 * 
	 * @param x
	 * @param y
	 * @param BALL_IMAGE
	 */
	public Ball(int x, int y){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(Main.BALL_IMAGE));
		Ball_Image = new ImageView(image);
		Ball_Image.setPreserveRatio(true);
		Ball_Image.setFitHeight(BallSize);
		XSpeed=x;
		YSpeed=y;
		lastHit=3;
	}
	/**
	 * Changes the attribute containing which player last hit ball for scorekeeping purposes
	 * @param paddle
	 */
	public void updateLastHit(Paddle paddle){
		this.hit(paddle.getPlayer());
	}
	/**
	 * sets X Speed of ball to input
	 * @param Speed
	 */
	public void setXSpeed(int Speed){
		XSpeed = Speed;
	}
	/**
	 * sets Y Speed of ball to input
	 * @param Speed
	 */
	public void setYSpeed(int Speed){
		YSpeed = Speed;
	}
	/**Updates ball position and rotates image 
	 * 
	 */
	public void shiftBall(){
		this.getBall().setX(this.getBall().getX()+this.getXSpeed()*Main.SECOND_DELAY);
		this.getBall().setY(this.getBall().getY()+this.getYSpeed()*Main.SECOND_DELAY);
		this.getBall().setRotate(this.getBall().getRotate() - 1);
	}
	/**
	 * updates attribute of last player to hit ball
	 * @param player
	 */
	public void hit(int player){
		lastHit=player;
	}
	/**
	 * gets Last player to have hit ball
	 * @return player number
	 */
	public int getLastHit(){
		return lastHit;
	}
	/**
	 * gets current xSpeed of ball
	 * @return current xSpeed
	 */
	public int getXSpeed(){
		return XSpeed;
	}
	/**
	 * gets current ySpeed of ball
	 * @return current ySpeed
	 */
	public int getYSpeed(){
		return YSpeed;
	}
	/**changes x direction to right
	 * 
	 */
	public void rightX(){
		XSpeed = Math.abs(XSpeed);
	}
	/**
	 * changes y direction to up
	 */
	public void upY(){
		YSpeed = Math.abs(YSpeed);
	}
	/**
	 * changes X direction to left
	 */
	public void leftX(){
		XSpeed = -Math.abs(XSpeed);
	}
	/**
	 * changes y direction down
	 */
	public void downY(){
		YSpeed = -Math.abs(YSpeed);
	}
	/**
	 * returns ImageView stored for the ball object
	 * @return Ball ImageView
	 */
	public ImageView getBall(){
		return Ball_Image;
	}
}

