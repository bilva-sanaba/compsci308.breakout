public class ExtraPoints extends PowerupSettings implements Powerup {
	
	ExtraPoints(Ball ball, int YSpeed){
		super(ball, YSpeed,"pointspower.gif");	
	}
	@Override
	public void operate(Paddle paddle) {
		
		switch (paddle.getPlayer()){
		case 1 : Main.player1Score+=100;
		case 2 : Main.player2Score+=100;
		}
		remove();
	}
}
