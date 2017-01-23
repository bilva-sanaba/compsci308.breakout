import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Paddle {

	private ImageView Paddle_Image;
	private int player;
	private int size;

	public Paddle(int playerNumber, int Width, int Height){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("paddle.gif"));
		Paddle_Image = new ImageView(image);
		Paddle_Image.setPreserveRatio(false);
		size = Main.PaddleSize;
		Paddle_Image.setFitWidth(size);
		Paddle_Image.setX((Width-this.getPaddle().getImage().getWidth())/2);
		player = playerNumber;
		switch (player){
		case 1: Paddle_Image.setY(Height*.1);
		break;
		case 2: Paddle_Image.setY(Height*.9);
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
		case 1: ball.hit(1);
		break;
		case 2: ball.hit(2);
		}
	}
	public void checkLocation(int Width){
		
		if (this.getPaddle().getX()>Width){
			this.getPaddle().setX(0);
		}else {
			if ((this.getPaddle().getX())<0){
				this.getPaddle().setX(Width);
			}
		}
		
	}
	public void scaledHits(Ball ball){
		double ballLoc = ball.getBall().getX();
		double paddleLoc= this.getPaddle().getX();	
		double dif = ballLoc-paddleLoc;
		double x = ball.getXSpeed();
		
		double y = ball.getYSpeed();
		x = (dif-(this.getPaddle().getFitWidth()/2))*2;
		double scale = Math.sqrt((x*x+y*y)/(Main.ballSpeed*Main.ballSpeed));
		x=x/scale;
		y=y/scale;
		ball.setXSpeed((int) x);
		ball.setYSpeed((int) y);		
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
						this.scaledHits(ball);
						ball.updateLastHit(this);
					}
					else{
						if (!atBottomBorder && atTopBorder) {
							ball.upY();
							this.scaledHits(ball);
							ball.updateLastHit(this);
						}
					}
				}
			}
		}


	}

}
