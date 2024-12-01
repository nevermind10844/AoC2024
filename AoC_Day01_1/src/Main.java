import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		
		List<Integer> leftList = new ArrayList<>();
		List<Integer> rightList = new ArrayList<>();
		
		for (String line : lines) {
			String[] lineArray = line.split("   ");
			leftList.add(Integer.parseInt(lineArray[0]));
			rightList.add(Integer.parseInt(lineArray[1]));
			
			leftList.sort(null);
			rightList.sort(null);
		}
		
		Integer result = 0;
		
		for(int i=0; i<rightList.size(); i++) {
			result += Math.abs(rightList.get(i) - leftList.get(i));
		}
		
		System.out.println(result);
	}

}
