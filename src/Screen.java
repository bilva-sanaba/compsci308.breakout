import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Screen {
	private Group root;
	public Scene s;
	Rectangle start;
	Rectangle instructions;
	Screen(){
	root = new Group();
	Text t = new Text(210, 150, "Press Space \nto Start");
	t.setFont(new Font(20));
	start = new Rectangle(200,100,200,100);
	start.setFill(Color.WHITE);
	Text t2 = new Text(210, 450, "Press i \nfor Instructions");
	t2.setFont(new Font(20));
	instructions = new Rectangle(200,400,200,100);
	instructions.setFill(Color.WHITE);
	
	Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(Main.BACKGROUND_IMAGE));
	ImageView background_disp = new ImageView(background_image);
	background_disp.setFitWidth(Main.Width);
	background_disp.setFitHeight(Main.Height);
	background_disp.setX(0);
	background_disp.setY(0);
	
	root.getChildren().add(background_disp);
	root.getChildren().add(start);
	root.getChildren().add(instructions);
	root.getChildren().add(t);
	root.getChildren().add(t2);
	
	s = new Scene(root,Main.SIZE,Main.SIZE,Color.WHITE);
	
	}
	public void changeScreen (KeyCode code){
		if (code == KeyCode.SPACE) {
			Main.stage.setScene(Main.getMyScene());
		}		
	}
	public void startMouseInput (double x, double y) {
		if (start.contains(x, y)) {
			Main.stage.setScene(Main.getMyScene());
            }
	}
}
