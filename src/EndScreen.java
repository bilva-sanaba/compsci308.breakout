import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * 
 * class to display new scene once game ends
 * Dependencies: Main
 *
 */
public class EndScreen extends Screen {
	public static final int xLocResult = 200;
	public static final int yLocResult = 100;
	public static final int xSizeResult= 200;
	public static final int ySizeResult = 400;
	public static final int TextSize = 20;
	private int lev;
	private Scorekeeper scoreboard;

	Rectangle Result;
	Text results;
	/**
	 * Creates an end screen that has the same methods as screen
	 * differences are purely aesthetic
	 * @param NumberOfButtons
	 * @param Level
	 */
	EndScreen(int NumberOfButtons,Scorekeeper score){
		super(NumberOfButtons);
		scoreboard=score;
		setLevel(score.getLevel());
		createResults(0);
		drawButtons();
		setScene(new Scene(getRoot(),Main.SIZE,Main.SIZE,ButtonColor));  
	}
	private void createResults(int index){
		createText();
		createButton();
		setButton(Result,index);
		setText(results,index);
	}
	private void createText(){
		int LevelDisp = getLevel()+1;
		results = new Text(xLocResult+xTextSpace, yLocResult+yTextSpace, "Top Player Score \n= " + scoreboard.getPlayer1Score()
				+ "\nBottom Player Score \n= " + scoreboard.getPlayer2Score() +
				"\nLevel Reached = \n" + LevelDisp);

		results.setFont(new Font(TextSize));
	}
	private void createButton(){
		Result = new Rectangle(xLocResult,yLocResult,xSizeResult,ySizeResult);
		Result.setFill(ButtonColor);
	}

	private void setLevel(int Level){
		lev=Level;
	}
	private int getLevel(){
		return lev;
	}
}
