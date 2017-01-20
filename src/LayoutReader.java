import java.io.*;
import java.util.ArrayList;
public class LayoutReader {
	private BufferedReader bufferedReader;
	private String file;
	public int[][] a;
	public int numberLevels;
	public int maxBricks;
	LayoutReader(String fileName) throws FileNotFoundException{
		numberLevels=0;
		FileReader fileReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(fileReader);
		file = fileName;
		String line;
		ArrayList<String> apple = new ArrayList<String>();
		try {
			int count =0;
			while((line = bufferedReader.readLine()) != null) {
				apple.add(line);
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
			a = new int[numberLevels][maxBricks*3];
			count = -1;
			int numCount=0;
			for (String text : apple){

				if (text.equals("New Level")){
					numCount=0;
					count ++;
				}else{


					String substring = text.substring(1, text.length()-1);

					String[] stringNums =substring.split(",");
					for (String num : stringNums){
						System.out.println(count);
						System.out.println(numCount);
						a[count][numCount] = Integer.parseInt(num);
						numCount++;
					}
				}

			}

			//			for (String triple : apple){
			//				if triple 
			//				for (int i=0;i<triple.length();i++){

			//				}
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


}