import javafx.scene.image.ImageView;

public interface Powerup {
	
	boolean checkPaddleContact(Paddle paddle);
	ImageView getImage();
	int getY();
	int getPlayer(); 
	void operate(Paddle paddle);
	boolean contactsWall();
	
}
