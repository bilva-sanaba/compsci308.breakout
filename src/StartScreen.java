import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class StartScreen extends Screen {
	public static final int xLocStart = 200;
	public static final int yLocStart = 100;
	public static final int xSizeStart = 200;
	public static final int ySizeStart = 100;
	public static final int xLocInst = 200;
	public static final int yLocInst = 400;
	public static final int xSizeInst = 200;
	public static final int ySizeInst = 100;
	public static final int TextSize = 20;
	public static final String startingText = "Press Space \nto Start";
	public static final String instructionText = "Press i \nfor Instructions";
	
	
	private Rectangle start;
	private Rectangle instructions;
	
	StartScreen(int NumberOfButtons){
		super(NumberOfButtons);
		createStartButton(0);
		createInstructionsButton(1);
		drawButtons();	
		setScene(new Scene(getRoot(),Main.SIZE,Main.SIZE,ButtonColor));
	}
	public void createStartButton(int index){
		Text startText = new Text(xLocStart+xTextSpace, yLocStart+yTextSpace, startingText);
		startText.setFont(new Font(TextSize));
		start = new Rectangle(xLocStart,yLocStart,xSizeStart,ySizeStart);
		start.setFill(ButtonColor);
		setButton(start,index);
		setText(startText,index);
	}
	public void createInstructionsButton(int index){
		Text instText = new Text(xLocInst+xTextSpace, yLocInst+yTextSpace, instructionText);
		instText.setFont(new Font(TextSize));
		instructions = new Rectangle(xLocInst,yLocInst,xSizeInst,ySizeInst);
		instructions.setFill(ButtonColor);
		setButton(instructions,index);
		setText(instText,index);
	}
	public void changeScreen (KeyCode code){
		if (code == KeyCode.SPACE) {
			Main.stage.setScene(Main.getMyScene());
		}
		if (code == KeyCode.I){
			Main.stage.setScene(Main.getInstructions());
		}
	}
	public void startMouseInput (double x, double y) {
		if (getButton(0).contains(x, y)) {
			Main.stage.setScene(Main.getMyScene());
            }
	}
}
