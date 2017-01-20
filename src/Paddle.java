import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {

	private ImageView Paddle_Image;
	private int player;

	public Paddle(int playerNumber, int Width, int Height){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("paddle.gif"));
		Paddle_Image = new ImageView(image);
		Paddle_Image.setX(Width/2);
		player = playerNumber;
		switch (player){
		case 1: Paddle_Image.setY(Height*.9);
		break;
		case 2: Paddle_Image.setY(Height*.1);
		}
	}
	public ImageView getPaddle(){
		return Paddle_Image;
	}
	public int getPlayer(){
		return player;
	}
	public void updateLastHit(Ball ball){
		switch (this.getPlayer()){
		case 1: ball.hit1();
		break;
		case 2: ball.hit2();
		}
	}
	public void checkPaddle(Ball ball){
		if (ball.getBall().getBoundsInParent().intersects(this.getPaddle().getBoundsInParent())){
			boolean atLeftBorder = this.getPaddle().getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
			boolean atRightBorder = this.getPaddle().getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
			boolean atTopBorder = this.getPaddle().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
			boolean atBottomBorder = this.getPaddle().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

			if (atRightBorder && !atLeftBorder) {
				ball.rightX();
				ball.updateLastHit(this);

			}else{
				if (!atRightBorder && atLeftBorder){
					ball.leftX();
					ball.updateLastHit(this);
				} else{ 
					if (atBottomBorder && !atTopBorder){
						ball.downY();	
						ball.updateLastHit(this);
					}
					else{
						if (!atBottomBorder && atTopBorder) {
							ball.upY();
							ball.updateLastHit(this);
						}
					}
				}
			}
		}


	}

}
