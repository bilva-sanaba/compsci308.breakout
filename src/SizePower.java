public class SizePower extends PowerupSettings implements Powerup {
	
	SizePower(Ball ball, int YSpeed){
		super(ball, YSpeed,"sizepower.gif");	
	}
	
	@Override
	public void operate(Paddle paddle) {
		for (Paddle pad : Main.Paddles){
		pad.getPaddle().setPreserveRatio(false);
		pad.getPaddle().setFitWidth(Main.PaddleSize);
		}
		paddle.getPaddle().setFitWidth(4*Main.PaddleSize/3);
		Main.root.getChildren().remove(this.getImage());	
	}
}
