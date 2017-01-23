import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InstructionScreen extends Screen{
	public static final int xLocInst = 200;
	public static final int yLocInst = 100;
	public static final int xSizeInst = 200;
	public static final int ySizeInst = 100;
	public static final int TextSize = 10;
	public static final String instructionText = "Instructions\nIn this Breakout Variant, there are two"
			+ "\nplayers The top player moves with left and"
			+ "\nright arrow keys. The bottom player can "
			+ "\ndo the same with the a and d keys. "
			+ "\nHitting blocks gets points, 30 for pink"
			+ "\n20 for green, 10 for blue, 0 for gray."
			+ "\nAlso pink blocks take 3 hits, green"
			+ "\ntake 2 hits, blue take 1, and gray are "
			+ "\nindestructable. There are bonuses "
			+ "\nsuch as extra points, extra ball, and"
			+ "\nlarger paddle. Finally, "
			+ "\na player gains 100 points if a ball "
			+ "\nlands in the opposing players goal and "
			+ "\nif so the opposing player loses a life. "
			+ "\nThe game ends when a player loses all "
			+ "\ntheir lives or if all levels are "
			+ "\ncleared. Press SPACE to start!";
	
	Rectangle instructions;
	InstructionScreen(int NumberOfButtons){
	super(NumberOfButtons);
	createBackground(Main.BACKGROUND_IMAGE);
	createInstructions(0);
	drawButtons();	
	setScene(new Scene(getRoot(),Main.SIZE,Main.SIZE,ButtonColor));
	
	}
	public void createInstructions(int index){
		Text instText = new Text(xLocInst+xTextSpace, yLocInst+yTextSpace, instructionText);
		instText.setFont(new Font(TextSize));
		instructions = new Rectangle(200,100,200,400);
		instructions.setFill(ButtonColor);
		setButton(instructions,index);
		setText(instText,index);
	}
	public void changeScreen (KeyCode code){
		if (code == KeyCode.SPACE) {
			Main.stage.setScene(Main.getMyScene());
		}		
	}
	
}
