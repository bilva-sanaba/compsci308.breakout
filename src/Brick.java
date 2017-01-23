import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
	private ImageView Brick_Image;
	private int Strength; 
	private int xLocation;
	private int yLocation;
	public Brick(int x, int y, int strength){
		Strength = strength;
		pickImage();
		xLocation= x;
		yLocation = y;
		Brick_Image.setX(xLocation);
		Brick_Image.setY(yLocation);
	}
	public int getStrength(){
		return Strength;
	}
	public ImageView getBrick(){
		return Brick_Image;
	}
	public boolean checkBrickStrength(){
		return (Strength == 0);
	}
	public void lowerStrength(){
		Strength = Strength -1;
	}
	public void pickImage(){
		Image image;
		switch (Strength){
		case 1: image = new Image(getClass().getClassLoader().getResourceAsStream("brick1.gif"));
				Brick_Image = new ImageView(image);
				break;
		case 2: image = new Image(getClass().getClassLoader().getResourceAsStream("brick2.gif"));
				Brick_Image = new ImageView(image);	
				break;
		case 3: image = new Image(getClass().getClassLoader().getResourceAsStream("brick4.gif"));
				Brick_Image = new ImageView(image);
				break;
		case Main.MAX_BRICK_STRENGTH: image = new Image(getClass().getClassLoader().getResourceAsStream("brick3.gif"));
				Brick_Image = new ImageView(image);
				break;
		}
	}
	public void weakenBrick(Group root){
		if (Strength == Main.MAX_BRICK_STRENGTH ){
			return; 
		} else {
			lowerStrength();
			root.getChildren().remove(this.getBrick());
			if (!this.checkBrickStrength()){
				pickImage();
				Brick_Image.setX(xLocation);
				Brick_Image.setY(yLocation);
				root.getChildren().add(this.getBrick());
			}
			else{
				Main.breakableBricks=Main.breakableBricks-1;
			}	
		}
	}
	private void updateScore(Ball ball){
		if (ball.getLastHit()==1){
			Main.player1Score +=this.getStrength()*10;
		} else{
			Main.player2Score +=this.getStrength()*10;
		}
	}
	public void updateBrick(Ball ball, Group root){
		if (this.getStrength()!=Main.MAX_BRICK_STRENGTH){
			updateScore(ball);
			this.weakenBrick(root);
		}
		
		
	}

	public void generatePowerup(Ball ball, Group root){
		int randomNumber = Main.rand.nextInt(100);
		PowerupSettings.makePowerup(ball,root,randomNumber);
	}
	public boolean checkLeftBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMinX() >= (ball.getBall().getBoundsInLocal().getMaxX());
	}
	public boolean checkRightBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMaxX() <= (ball.getBall().getBoundsInLocal().getMinX());
	}
	public boolean checkTopBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
	}
	public boolean checkBottomBorder(Ball ball){
		return this.getBrick().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());
	}
	public boolean shouldDirectionChange(boolean atLeftBorder, boolean atRightBorder, 
			boolean atTopBorder, boolean atBottomBorder, Ball ball){
		return (atRightBorder && !atLeftBorder && ball.getXSpeed()<0
				|| !atRightBorder && atLeftBorder && ball.getXSpeed()>0
				|| atBottomBorder && !atTopBorder && ball.getYSpeed()>0
				||!atBottomBorder && atTopBorder && ball.getYSpeed()<0);
	}
	public void changeDirection(boolean atLeftBorder, boolean atRightBorder, 
			boolean atTopBorder, boolean atBottomBorder, Ball ball){
		if (atRightBorder){
			ball.rightX();
		}
		else {
			if (atLeftBorder){
				ball.leftX();
			}
			else{
				if (atTopBorder){
					ball.upY();
				}
				else {
					if (atBottomBorder){
						ball.downY();
					}
				}
			}
		}
	}
	public void allPossibleUpdates(boolean atLeftBorder, boolean atRightBorder, 
			boolean atTopBorder, boolean atBottomBorder, Ball ball, Group root){
		if (shouldDirectionChange(atLeftBorder, atRightBorder, atTopBorder, atBottomBorder, ball)){
			updateBrick(ball,root);
			changeDirection(atLeftBorder, atRightBorder, atTopBorder, atBottomBorder, ball);
		}
	}
	public void checkBricks(Ball ball, Group root){
		if (ball.getBall().getBoundsInParent().intersects(this.getBrick().getBoundsInParent())){
			if (this.getStrength()>0){
				generatePowerup(ball,root);
				boolean atLeftBorder = checkLeftBorder(ball);
				boolean atRightBorder = checkRightBorder(ball);
				boolean atTopBorder = checkTopBorder(ball);
				boolean atBottomBorder = checkBottomBorder(ball);
				allPossibleUpdates(atLeftBorder, atRightBorder, atTopBorder,atBottomBorder,ball, root);
			}
		}
	}
}