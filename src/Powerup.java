// This entire file is part of my masterpiece.
// Bilva Sanaba
//In addition to the PowerupSettings, this interface is important for declaring the operate(Paddle paddle) method
//Each different powerup uses an operate method once the powerup contacts a paddle
//However, each powerup also does something different when it operates which is why this interface is important and good design
import javafx.scene.image.ImageView;
/**
 * 
 * Interface for Powerups
 *Dependencies: Paddle
 */
public interface Powerup {
	
	boolean checkPaddleContact(Paddle paddle);
	ImageView getImage();
	int getY();
	int getPlayer(); 
	void operate(Paddle paddle);
	boolean contactsWall();
	
}
