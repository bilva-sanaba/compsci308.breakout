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
		switch (paddle.getPlayer()){
		case 1: this.hit1();
		break;
		case 2: this.hit2();
		}
	}
	public void hit1(){
		lastHit=1;
	}
	public void hit2(){
		lastHit=2;
	}
	public boolean lastHit(){
		return lastHit==1;

	}
	public int getXSpeed(){
		return XSpeed;
	}
	public int getYSpeed(){
		return YSpeed;
	}
	public void reverseX(){
		XSpeed = -1*XSpeed;
	}
	public void reverseY(){
		YSpeed = -1*YSpeed;
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

