import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * Class extended by all classes which only generate a scene to be displayed
 * 
 *
 */
public abstract class Screen {
	public static final int xTextSpace = 10;
	public static final int yTextSpace = 50;
	public static final Paint ButtonColor = Color.WHITE;
	private Group root;
	private Scene scene;
	private Rectangle[] buttons;
	private Text[] texts;
	private int ButtonCount;
	/**
	 * determines  number of rectangles with text inside will be displayed in the scene
	 * @param NumberOfButtons
	 */
	Screen(int NumberOfButtons){
		setButtons(new Rectangle[NumberOfButtons]);
		setTexts(new Text[NumberOfButtons]);
		setButtonCount(NumberOfButtons);
		root = new Group();
		createBackground(Main.BACKGROUND_IMAGE);
	}
	/**
	 * adds created Rectangles and text to scene
	 */
	public void drawButtons(){
		for (int i=0;i<getButtonCount();i++){
			getRoot().getChildren().add(getButton(i));
			getRoot().getChildren().add(getText(i));	
		}
	}
	/**
	 * creates background of scene as image stored from BACKGROUND_IMAGE
	 * @param BACKGROUND_IMAGE
	 */
	public void createBackground(String BACKGROUND_IMAGE){
		Image background_image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
		ImageView background_disp = new ImageView(background_image);
		background_disp.setFitWidth(Main.WIDTH);
		background_disp.setFitHeight(Main.HEIGHT);
		background_disp.setX(0);
		background_disp.setY(0);
		root.getChildren().add(background_disp);
	}
	/**
	 * setter method for text array whose contents will be displayed on screen
	 * @param texts2
	 */
	public void setTexts(Text[] texts2) {
		texts = texts2;	
	}
	/**
	 * setter method for Rectangle array whose contents will be displayed on screen
	 * @param rectangles
	 */
	public void setButtons(Rectangle[] rectangles) {
		buttons = rectangles;
	}
	/**
	 * returns root of scene
	 * @return
	 */
	public Group getRoot(){
		return root;
	}
	/**
	 * returns rectangle at certain index 
	 * @param index
	 * @return
	 */
	public Rectangle getButton(int index) {
		return buttons[index];
	}
	/**
	 * sets buttons[index] to buttons
	 * @param buttons
	 * @param index
	 */
	public void setButton(Rectangle buttons, int index) {
		this.buttons[index] = buttons;
	}
	/**
	 * gets text from texts[index]
	 * @param index
	 * @return
	 */
	public Text getText(int index) {
		return texts[index];
	}
	/**
	 * sets texts[index] to be text
	 * @param text
	 * @param index
	 */
	public void setText(Text text,int index) {
		this.texts[index] = text;
	}
	/**
	 * counts number of buttons that will be displayed on screen
	 * @return
	 */
	public int getButtonCount() {
		return ButtonCount;
	}
	/**
	 * sets number of buttons that can be displayed on screen
	 * @param buttonCount
	 */
	public void setButtonCount(int buttonCount) {
		ButtonCount = buttonCount;
	}
	/**
	 * returns Scene that can be displayed on screen
	 * @return
	 */
	public Scene getScene() {
		return scene;
	}
	/**
	 * sets scene
	 * @param scene
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
