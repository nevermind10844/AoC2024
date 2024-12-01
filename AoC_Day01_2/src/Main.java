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
			Integer leftInt = leftList.get(i);
			
			int similar = (int) rightList.stream().filter(rightInt -> rightInt.equals(leftInt)).count();
			//System.out.println(String.format("%d :: %d", leftInt, similar));
			result += (leftInt * similar);
		}
		
		System.out.println(result);
	}

}
