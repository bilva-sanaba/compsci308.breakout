import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * class that all Powerups extend in this game
 * Dependencies: Main, paddle
 */
public class PowerupSettings {
	public static final int dropSpeed = 30;
	private int YDirection;
	private ImageView Powerup_Image;
	private int player;
	/**
	 * creates an imageview of powerup based on which type defined in String, speed Yspeed, and at location of ball
	 * @param ball
	 * @param YSpeed
	 * @param PowerupImage
	 */
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
	/**
	 * removes imageview of powerup
	 */
	public void removePowerup(){
		Main.root.getChildren().remove(this.getImage());
	}
	/**
	 * getsY speed of powerup
	 * @return
	 */
	public int getY(){
		return YDirection;
	}
	/**
	 * checks if the powerup imageview has made contact with a paddle
	 * @param paddle
	 * @return
	 */
	public boolean checkPaddleContact(Paddle paddle) {
		// TODO Auto-generated method stub
		return (this.Powerup_Image.getBoundsInParent().intersects(paddle.getPaddle().getBoundsInParent()));	
	}
	/**
	 * returns which player generated the powerup
	 * @return
	 */
	public int getPlayer(){
		return player;
	}
	/**
	 * returns stored ImageView of powerup
	 * @return
	 */
	public ImageView getImage(){
		return Powerup_Image;
	}
	/**
	 * determines if  powerup imageview contacts a wall
	 * @return
	 */
	public boolean contactsWall(){
		return (this.getImage().getY()<0 || this.getImage().getY()>Main.Height);
	}
	/**
	 * creates and adds powerup imageview to scene
	 * @param ball
	 * @param root
	 * @param number
	 */
	public static void makePowerup(Ball ball, Group root, int number){
		createPowerup(ball,number);
		addPowerup(root);
	}
	
	private static void createPowerup(Ball ball, int number){
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

		}
	}
	private static void addPowerup(Group root){
		if (Main.myPowerup!=null){
			root.getChildren().remove(Main.myPowerup.getImage());
			root.getChildren().add(Main.myPowerup.getImage());
		}
	}
}