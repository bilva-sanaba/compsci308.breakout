import java.io.*;
import java.util.ArrayList;
/**This class is used to read an input text and create an array of information for creating bricks
 * Dependencies: Main
 * 
 *
 */
public class LayoutReader {
	private BufferedReader bufferedReader;
	private String file;
	private int[][] BrickSpecifications;
	private int numberLevels;
	private int maxBricks;
	/**
	 * Reads file stored as fileName line by line in the form (brick x location, brick y location, brick strength) or "New Level"
	 * changes text in line to be a collection of ints that are stored in a matrix and determine tbe bricks
	 * each New Level that is read, leads to numbers being stored in the next row of matrix
	 * @param fileName
	 * @throws IOException 
	 */
	LayoutReader(String fileName) throws IOException{
		numberLevels=0;
		FileReader fileReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(fileReader);
		file = fileName;
		String line = new String();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			setLines(line,lines);
			setBrickSpecs(lines);
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ file + "'"); 
		}	
	}
	/**
	 * Goes through lines of text in form (x,y,strength)
	 * and stores them in an array
	 * @param lines
	 */
	private void setBrickSpecs(ArrayList<String> lines){
		BrickSpecifications = new int[numberLevels][getMaxBricks()*3];
		int count = -1;
		int numCount=0;
		for (String text : lines){
			if (text.equals("New Level")){
				numCount=0;
				count ++;
			}else{
				String substring = text.substring(1, text.length()-1);
				String[] stringNums =substring.split(",");
				for (String num : stringNums){
					BrickSpecifications[count][numCount] = Integer.parseInt(num);
					numCount++;
				}
			}
		}
	}
	/**
	 * loops through all lines of text stored in text file and adds them to a list
	 * if the text file line reads "New Level" then the number of bricks in the previous level are counted
	 * @param line
	 * @param lines
	 * @throws IOException
	 */
	private void setLines(String line, ArrayList<String> lines) throws IOException{
		int numberOfBricks =0;
		while((line = bufferedReader.readLine()) != null) {
			lines.add(line);
			numberOfBricks++;
			if (line.equals("New Level")){
				if (numberOfBricks>getMaxBricks()){
					setMaxBricks(numberOfBricks);
				}
				numberLevels++;
				numberOfBricks =0;
			}
		}
		if (numberOfBricks>getMaxBricks()){
			setMaxBricks(numberOfBricks);
		}
		bufferedReader.close();
	}
	/**
	 * returns a matrix containing information needed to generate brick images
	 * @return
	 */
	public int[][] getBrickSpecs(){
		return BrickSpecifications;
	}
	public int getMaxBricks() {
		return maxBricks;
	}
	public void setMaxBricks(int maxBricks) {
		this.maxBricks = maxBricks;
	}

}