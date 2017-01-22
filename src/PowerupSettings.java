import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerupSettings {
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
	
}
