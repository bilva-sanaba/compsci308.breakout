import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
	public int XSpeed;
	public int YSpeed;
	private ImageView Ball_Image;
	
    public Ball(int x, int y, String BALL_IMAGE){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        Ball_Image = new ImageView(image);
        Ball_Image.setPreserveRatio(true);
        Ball_Image.setFitHeight(20);
        XSpeed=x;
    	YSpeed=y;
    	
    	
      
    }
	//changes X direction if wall is hit on x side
    public void updateX(){
    	
    	XSpeed = -1 * XSpeed;
    	
    	
    }
    //changes Y direction if wall is hit on Y side
    public void updateY(){
    	YSpeed = -1 * YSpeed;
    	}
    //returns Balls from image view
    public ImageView getBall(){
    	return Ball_Image;
    }
}

