import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
	private ImageView Brick_Image;
	private int Strength; 
	private int x;
	private int y;
	
	public Brick(int xLocation, int yLocation, int strength){
		Strength = strength;
		if (Strength == 1){
			Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick1.gif"));
			Brick_Image = new ImageView(image);
		} else {
			if (Strength == 2){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick2.gif"));
				Brick_Image = new ImageView(image);				
			} else {
				if (Strength == 3){
					Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick4.gif"));
					Brick_Image = new ImageView(image);
				}
				else {
					if (Strength == 4){
						Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick3.gif"));
						Brick_Image = new ImageView(image);
					}
				} 
				}
			}
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
	public boolean checkBrick(){
		return (Strength == 0);
	}
	public void lowerStrength(){
		Strength = Strength -1;
	}

	public void updateBrick(){
		if (Strength == 4 ){
			return; 
		} else {
			lowerStrength();
		}
		if (Strength == 1){
			Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick1.gif"));
			Brick_Image = new ImageView(image);
		} else {
			if (Strength == 2){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick2.gif"));
				Brick_Image = new ImageView(image);				
			} else {
				if (Strength == 3){
					Image image = new Image(getClass().getClassLoader().getResourceAsStream("brick4.gif"));
					Brick_Image = new ImageView(image);
				}
		
	}
		}
	Brick_Image.setX(x);
	Brick_Image.setY(y);
	}
}
	
	
	
	

