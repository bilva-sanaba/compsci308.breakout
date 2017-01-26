import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Creates scene to be displayed when instructions are needed to be displayed
 * Dependencies: Main, Screen
 *
 */
public class InstructionScreen extends Screen{
	public static final int xLocInst = 200;
	public static final int yLocInst = 100;
	public static final int xSizeInst = 200;
	public static final int ySizeInst = 400;
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
	/**
	 * Gives arrays appropriate size dependent on NumberOfButtons
	 * Creates Background image for scene and then draws buttons 
	 * @param NumberOfButtons
	 */
	InstructionScreen(int NumberOfButtons){
		super(NumberOfButtons);
		createBackground(Main.BACKGROUND_IMAGE);
		createInstructions(0);
		drawButtons();	
		setScene(new Scene(getRoot(),Main.SIZE,Main.SIZE,ButtonColor));

	}
	/**
	 * creates a button filled with instructions stored in various arrays at int index
	 * @param index
	 */
	public void createInstructions(int index){
		Text instText = new Text(xLocInst+xTextSpace, yLocInst+yTextSpace, instructionText);
		instText.setFont(new Font(TextSize));
		instructions = new Rectangle(xLocInst,yLocInst,xSizeInst,ySizeInst);
		instructions.setFill(ButtonColor);
		setButton(instructions,index);
		setText(instText,index);
	}
	/**
	 * handles SPACE input which triggers the change of scene to main game
	 * @param code
	 */
	public void changeScreen (KeyCode code,GameSettings game){
		if (code == KeyCode.SPACE) {
			game.getStage().setScene(game.getMyScene());
		}		
	}

}
