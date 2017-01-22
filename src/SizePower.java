public class SizePower extends PowerupSettings implements Powerup {
	
	SizePower(Ball ball, int YSpeed){
		super(ball, YSpeed,"sizepower.gif");	
	}
	
	@Override
	public void operate(Paddle paddle) {
		for (Paddle pad : Main.Paddles){
		pad.getPaddle().setPreserveRatio(true);
		pad.getPaddle().setFitWidth(60);
		}
		paddle.getPaddle().setFitWidth(80);
		Main.root.getChildren().remove(this.getImage());	
	}
}
