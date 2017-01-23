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
	public int[][] BrickSpecifications;
	public int numberLevels;
	public int maxBricks;
	/**
	 * Reads file stored as fileName line by line
	 * removes parantheses and commas and stores each number in a matrix
	 * each New Level that is read leads to numbers being stored in the next row of matrix
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	LayoutReader(String fileName) throws FileNotFoundException{
		numberLevels=0;
		FileReader fileReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(fileReader);
		file = fileName;
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			int count =0;
			while((line = bufferedReader.readLine()) != null) {
				lines.add(line);
				count++;
				if (line.equals("New Level")){
					if (count>maxBricks){
						maxBricks=count;
					}
					numberLevels++;
					count =0;
				}
			}
			if (count>maxBricks){
				maxBricks=count;
			}
			bufferedReader.close();
			BrickSpecifications = new int[numberLevels][maxBricks*3];
			count = -1;
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

		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							file + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ file + "'"); 
		}
		
	}
	/**
	 * returns a matrix containing information needed to generate brick images
	 * @return
	 */
	public int[][] getBrickSpecs(){
		return BrickSpecifications;
	}

}