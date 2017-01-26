
public class Scorekeeper {
	public static final int PlayerLives=10;
	public static final int StartingLevel=0;
	private int player1Score=0;
	private int player2Score=0;
	private int player1Lives;
	private int player2Lives;
	private int LevelCount;
	Scorekeeper(){
		setPlayer1Lives(PlayerLives);
		setPlayer2Lives(PlayerLives);
		setLevel(StartingLevel);
	}
	public void increaseLevel(int amount){
		LevelCount=LevelCount+amount;
	}
	public void decreaseLevel(int amount){
		LevelCount= LevelCount - amount;
	}
	public void setLevel(int level){
		LevelCount = level;
	}
	public int getLevel(){
		return LevelCount;
	}
	public void updatePlayer1Score(int increase){
		setPlayer1Score(getPlayer1Score() + increase);
	}
	public void updatePlayer2Score(int increase){
		setPlayer2Score(getPlayer2Score() + increase);
	}
	public void reducePlayer1Lives(){
		setPlayer1Lives(getPlayer1Lives() - 1);
	}
	public void reducePlayer2Lives(){
		setPlayer2Lives(getPlayer2Lives() - 1);
	}
	public int getPlayer1Lives() {
		return player1Lives;
	}
	public void setPlayer1Lives(int player1Lives) {
		this.player1Lives = player1Lives;
	}
	public int getPlayer2Lives() {
		return player2Lives;
	}
	public void setPlayer2Lives(int player2Lives) {
		this.player2Lives = player2Lives;
	}
	public int getPlayer2Score() {
		return player2Score;
	}
	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}
	public int getPlayer1Score() {
		return player1Score;
	}
	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}
}
