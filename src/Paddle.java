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
	private ImageView getPaddle(){
		return Paddle_Image;
	}
	private int getPlayer(){
		return player;
	}
	private void checkPaddle(Ball ball){
		boolean atRightBorder;
		boolean atLeftBorder; 
		boolean atBottomBorder;
		boolean atTopBorder;
		if (ball.getBall().getBoundsInParent().intersects(this.getPaddle().getBoundsInParent())){
			atLeftBorder = this.getPaddle().getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
			atRightBorder = this.getPaddle().getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
			atTopBorder = this.getPaddle().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
			atBottomBorder = this.getPaddle().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());

			if (atRightBorder && !atLeftBorder) {
				ball.rightX();
				switch (this.getPlayer()){
				case 1: ball.hit1();
						break;
				case 2: ball.hit2();
				}
				
			}else{
				if (!atRightBorder && atLeftBorder){
					ball.leftX();
					switch (this.getPlayer()){
					case 1: ball.hit1();
							break;
					case 2: ball.hit2();
					}
				} else{ 
					if (atBottomBorder && !atTopBorder){
						ball.downY();	
						switch (this.getPlayer()){
						case 1: ball.hit1();
								break;
						case 2: ball.hit2();
						}
					}
					else{
						if (!atBottomBorder && atTopBorder) {
							ball.upY();
							switch (this.getPlayer()){
							case 1: ball.hit1();
									break;
							case 2: ball.hit2();
							}
						}
					}
				}
			}
		}
	
	}
}
