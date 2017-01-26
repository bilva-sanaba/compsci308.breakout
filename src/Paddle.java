import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**Paddle class for player control at top and bottom. has the ability to bounce balls 
 * stores player number in balls when contact is made
 * Dependencies: ball, Main
 *
 */

public class Paddle {
	public static final String PADDLE_IMAGE="paddle.gif";
	private ImageView Paddle_Image;
	private int player;
	private int size;
	/**
	 * creates a paddle attributed to a certain PlayerNumber with location based on a ratio from width and height
	 * @param playerNumber
	 * @param Width
	 * @param Height
	 */
	public Paddle(int playerNumber, int Width, int Height){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
		Paddle_Image = new ImageView(image);
		Paddle_Image.setPreserveRatio(false);
		size = Main.PADDLESIZE;
		Paddle_Image.setFitWidth(size);
		Paddle_Image.setX((Width-this.getPaddle().getImage().getWidth())/2);
		player = playerNumber;
		setPaddleHeight(playerNumber,Height);


	}
	private void setPaddleHeight(int playerNumber, int Height){
		if (playerNumber==1){
			Paddle_Image.setY(Height*.1);
		}else{
			Paddle_Image.setY(Height*.9);
		}
	}
	/**
	 * returns ImageView of Paddle
	 * @return
	 */
	public ImageView getPaddle(){
		return Paddle_Image;
	}
	/**
	 * returns playerID number of paddle
	 * @return
	 */
	public int getPlayer(){
		return player;
	}
	/**
	 * changes a ball's attributes so that the balls last hit is the paddles player ID Number
	 * @param ball
	 */
	public void updateLastHit(Ball ball){
		if (this.getPlayer()==1){
			ball.hit(1);
		}else{
			ball.hit(2);
		}

	}
	/**
	 * checks location of paddle and updates it to allow for warping
	 * @param Width
	 */
	public void checkLocation(int Width){

		if (this.getPaddle().getX()>Width){
			this.getPaddle().setX(0);
		}else {
			if ((this.getPaddle().getX())<0){
				this.getPaddle().setX(Width);
			}
		}
	}
	/**
	 * checks where ball hits on paddle and sends ball in direction
	 * if closer to edge of paddle, x velocity is more 
	 * @param ball
	 */
	public void scaledHits(Ball ball,GameSettings game){
		double ballLoc = ball.getBall().getX();
		double paddleLoc= this.getPaddle().getX();	
		double dif = ballLoc-paddleLoc;
		double x = ball.getXSpeed();

		double y = ball.getYSpeed();
		x = (dif-(this.getPaddle().getFitWidth()/2))*2;
		double scale = Math.sqrt((x*x+y*y)/(Math.pow(game.getBallSpeed(),2)));
		x=x/scale;
		y=y/scale;
		ball.setXSpeed((int) x);
		ball.setYSpeed((int) y);		
	}
	/**
	 * checks if Ball intersects with paddle at some location and changes ball direction appropriately
	 * @param ball
	 */
	public void checkPaddle(Ball ball, GameSettings game){
		if (ball.getBall().getBoundsInParent().intersects(this.getPaddle().getBoundsInParent())){
			boolean atLeftBorder = this.getPaddle().getBoundsInLocal().getMaxX() >= (ball.getBall().getBoundsInLocal().getMinX());
			boolean atRightBorder = this.getPaddle().getBoundsInLocal().getMinX() <= (ball.getBall().getBoundsInLocal().getMaxX());
			boolean atTopBorder = this.getPaddle().getBoundsInLocal().getMaxY() <= (ball.getBall().getBoundsInLocal().getMinY());
			boolean atBottomBorder = this.getPaddle().getBoundsInLocal().getMinY() >= (ball.getBall().getBoundsInLocal().getMaxY());
			checkBorders(atLeftBorder,atRightBorder,atTopBorder,atBottomBorder,ball, game);
		}	
	}
	private void checkBorders(boolean atLeftBorder,boolean atRightBorder,boolean atTopBorder,boolean atBottomBorder,Ball ball,GameSettings game){
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
					this.scaledHits(ball,game);
					ball.updateLastHit(this);
				}
				else{
					if (!atBottomBorder && atTopBorder) {
						ball.upY();
						this.scaledHits(ball,game);
						ball.updateLastHit(this);
					}
				}
			}
		}
	}
}
