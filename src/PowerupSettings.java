import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerupSettings {
	public static final int dropSpeed = 30;
	private int YDirection;
	private ImageView Powerup_Image;
	private int player;
	PowerupSettings(Ball ball, int YSpeed, String PowerupImage){
		player= ball.getLastHit();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(PowerupImage));
		Powerup_Image = new ImageView(image);
		switch (player){
		case 1: YDirection =-YSpeed;
		break; 
		case 2: YDirection = YSpeed;	
		}
		Powerup_Image.setX(ball.getBall().getX());
		Powerup_Image.setY(ball.getBall().getY());

	}
	public void remove(){
		Main.root.getChildren().remove(this.getImage());
	}
	public int getY(){
		return YDirection;
	}
	public boolean checkPaddleContact(Paddle paddle) {
		// TODO Auto-generated method stub
		return (this.Powerup_Image.getBoundsInParent().intersects(paddle.getPaddle().getBoundsInParent()));	
	}
	public int getPlayer(){
		return player;
	}
	public ImageView getImage(){
		return Powerup_Image;
	}
	public boolean contactsWall(){
		return (this.getImage().getY()<0 || this.getImage().getY()>Main.Height);
	}
	public static void makePowerup(Ball ball, Group root, int number){
		if (Main.myPowerup==null){
			if (number>0 && number < 1){
				Main.myPowerup = new ExtraPoints(ball, dropSpeed);
			}else {
				if (number >20 && number <40){
					
					Main.myPowerup = new ExtraBall(ball,dropSpeed);
				}
				else{
					if (number>40 && number <60){
						Main.myPowerup= new SizePower(ball,dropSpeed);
					}
				}
			}
			if (Main.myPowerup!=null){
				root.getChildren().add(Main.myPowerup.getImage());
			}
		}
	}
}