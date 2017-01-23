import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Screen {
	public static final int xTextSpace = 10;
	public static final int yTextSpace = 50;
	public static final Paint ButtonColor = Color.WHITE;
	private Group root;
	private Scene scene;
	private Rectangle[] buttons;
	private Text[] texts;
	private int ButtonCount;
	
	Screen(int NumberOfButtons){
		setButtons(new Rectangle[NumberOfButtons]);
		setTexts(new Text[NumberOfButtons]);
		setButtonCount(NumberOfButtons);
		root = new Group();
		createBackground(Main.BACKGROUND_IMAGE);
	}
	public void drawButtons(){
		for (int i=0;i<getButtonCount();i++){
			getRoot().getChildren().add(getButton(i));
			getRoot().getChildren().add(getText(i));	
		}
	}
	public void createBackground(String BACKGROUND_IMAGE){
		Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
		ImageView background_disp = new ImageView(background_image);
		background_disp.setFitWidth(Main.Width);
		background_disp.setFitHeight(Main.Height);
		background_disp.setX(0);
		background_disp.setY(0);
		root.getChildren().add(background_disp);
	}
	public void setTexts(Text[] texts2) {
		texts = texts2;	
	}
	public void setButtons(Rectangle[] rectangles) {
		buttons = rectangles;
	}
	public Group getRoot(){
		return root;
	}
	public Rectangle getButton(int index) {
		return buttons[index];
	}
	public void setButton(Rectangle buttons, int index) {
		this.buttons[index] = buttons;
	}
	public Text getText(int index) {
		return texts[index];
	}
	public void setText(Text text,int index) {
		this.texts[index] = text;
	}
	public int getButtonCount() {
		return ButtonCount;
	}
	public void setButtonCount(int buttonCount) {
		ButtonCount = buttonCount;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
