
public class LevelSettings {
	public static int numberBricks;
	public static int breakableBricks;
	public void setNumberBricks(int brickCount){
		numberBricks = brickCount;
	}
	public void setNumberBreakable(int breakableCount){
		breakableBricks = breakableCount;
	}
	public void reduceNumberBreakable(){
		breakableBricks=breakableBricks-1;
	}
	public int getNumberBricks(){
		return numberBricks;
	}
	public int getNumberBreakable(){
		return breakableBricks;
	}
	
}
