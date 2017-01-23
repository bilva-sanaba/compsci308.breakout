import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
	private int XSpeed;
	private int YSpeed;
	private ImageView Ball_Image;
	private int lastHit;

	public Ball(int x, int y, String BALL_IMAGE){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		Ball_Image = new ImageView(image);
		Ball_Image.setPreserveRatio(true);
		Ball_Image.setFitHeight(20);
		XSpeed=x;
		YSpeed=y;
		lastHit=3;
	}
	public void updateLastHit(Paddle paddle){
		this.hit(paddle.getPlayer());
	}
	public void setXSpeed(int Speed){
		XSpeed = Speed;
	}
	public void setYSpeed(int Speed){
		YSpeed = Speed;
	}
	public void shiftBall(){
		this.getBall().setX(this.getBall().getX()+this.getXSpeed()*Main.SECOND_DELAY);
		this.getBall().setY(this.getBall().getY()+this.getYSpeed()*Main.SECOND_DELAY);
		this.getBall().setRotate(this.getBall().getRotate() - 1);
	}
	public void hit(int player){
		lastHit=player;
	}
	public int getLastHit(){
		return lastHit;

	}
	public int getXSpeed(){
		return XSpeed;
	}
	public int getYSpeed(){
		return YSpeed;
	}
	//changes X direction if wall is hit on x side
	public void rightX(){
		XSpeed = Math.abs(XSpeed);
	}
	//changes Y direction if wall is hit on Y side
	public void upY(){
		YSpeed = Math.abs(YSpeed);
	}
	public void leftX(){
		XSpeed = -Math.abs(XSpeed);
	}
	public void downY(){
		YSpeed = -Math.abs(YSpeed);
	}
	//returns Balls from image view
	public ImageView getBall(){
		return Ball_Image;
	}
}

