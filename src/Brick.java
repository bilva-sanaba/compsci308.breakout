import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
	private ImageView Brick_Image;
	private int Strength; 
	private int x;
	private int y;

	public Brick(int xLocation, int yLocation, int strength){
		Strength = strength;
		pickImage();

		x= xLocation;
		y = yLocation;
		Brick_Image.setX(x);
		Brick_Image.setY(y);

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
		case 4: image = new Image(getClass().getClassLoader().getResourceAsStream("brick3.gif"));
		Brick_Image = new ImageView(image);
		break;
		}
	}
	public void weakenBrick(){
		if (Strength == 4 ){
			return; 
		} else {
			lowerStrength();
		}
		pickImage();
		Brick_Image.setX(x);
		Brick_Image.setY(y);
	}
	public void updateBrick(Ball ball, int player1Score, int player2Score, Group root){
		if (this.getStrength()!=4){
			if (ball.lastHit()){
				player1Score = player1Score+10*this.getStrength();
			} else{
				player2Score = player2Score+10*this.getStrength();
			}
		}

		root.getChildren().remove(this.getBrick());
		this.weakenBrick();
		if (!this.checkBrickStrength()){
			root.getChildren().add(this.getBrick());}
	}
	public void checkBricks(Ball ball, int player1score, int player2score, Group root){
		if (ball.getBall().getBoundsInParent().intersects(this.getBrick().getBoundsInParent())){
			if (this.getStrength()>0){
				boolean atLeftBorder = this.getBrick().getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
				boolean atRightBorder = this.getBrick().getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
				boolean atTopBorder = this.getBrick().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
				boolean atBottomBorder = this.getBrick().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

				if (atRightBorder && !atLeftBorder) {
					if (ball.getXSpeed()<0){
						updateBrick(ball,player1score,player2score,root);
						ball.rightX();
					}					
				}else{
					if (!atRightBorder && atLeftBorder){
						if (ball.getXSpeed()>0){
							updateBrick(ball,player1score,player2score,root);
							ball.leftX();	
						}
					} else{ 
						if (atBottomBorder && !atTopBorder){
							if (ball.getYSpeed()>0){
								updateBrick(ball,player1score,player2score,root);
								ball.downY();
							}
						}
						else{
							if (!atBottomBorder && atTopBorder) {
								if (ball.getYSpeed()<0){
									updateBrick(ball,player1score,player2score,root);
									ball.upY();
								}
							}
						}
					}
				}
			}
		}
	}
}





