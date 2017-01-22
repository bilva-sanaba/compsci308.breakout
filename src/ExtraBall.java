public class ExtraBall extends PowerupSettings implements Powerup {
	
	ExtraBall(Ball ball, int YSpeed){
		super(ball, YSpeed, "extraballpower.gif");	
	}
	public void operate(Paddle paddle) {
		
		Ball ball = new Ball(0,100,"soccerball.gif");
		ball.getBall().setX(paddle.getPaddle().getX()+paddle.getPaddle().getImage().getWidth()/2);
		
		switch(this.getPlayer()){
		case 1: ball.hit1();
				ball.upY();
				ball.getBall().setY(.13*Main.Height);
				break;
		case 2: ball.hit2();
				ball.downY();
				ball.getBall().setY(.85*Main.Height);
				break;
		}
		for (int i=0;i<Main.myBalls.length;i++){
			if (Main.myBalls[i]==null){
				Main.myBalls[i]=ball;
				Main.root.getChildren().remove(this.getImage());
				Main.root.getChildren().add(ball.getBall());
				break;
			}
		}

		
		// TODO Auto-generated method stub
		
	}
}
