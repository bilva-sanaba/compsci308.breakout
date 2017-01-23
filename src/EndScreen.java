import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndScreen extends Screen {
	public static final int xLocResult = 200;
	public static final int yLocResult = 100;
	public static final int xSizeResult= 200;
	public static final int ySizeResult = 400;
	public static final int TextSize = 20;

	Rectangle Result;

	EndScreen(int NumberOfButtons){
		super(NumberOfButtons);
		createResults(0);
		drawButtons();
    	setScene(new Scene(getRoot(),Main.SIZE,Main.SIZE,ButtonColor));
	}
	public void createResults(int index){
		int LevelDisp = Main.Level+1;
		Text results;
		if (Main.Level<Main.MaxLevels){
			results = new Text(xLocResult+xTextSpace, yLocResult+yTextSpace, "Top Player Score \n= " + Main.player1Score 
				+ "\nBottom Player Score \n= " + Main.player2Score +
				"\nLevel Reached = \n" + LevelDisp);
		}else {
			results = new Text(xLocResult+xTextSpace, yLocResult+yTextSpace, "Top Player Score \n= " + Main.player1Score 
					+ "\nBottom Player Score \n= " + Main.player2Score +
					"\n You Beat the "
					+ "\nLast Level!");
		}
		results.setFont(new Font(TextSize));
		Result = new Rectangle(xLocResult,yLocResult,xSizeResult,ySizeResult);
		Result.setFill(ButtonColor);
		setButton(Result,index);
		setText(results,index);
	}

}
