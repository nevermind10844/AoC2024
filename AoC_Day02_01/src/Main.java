import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		List<List<Integer>> reports = new ArrayList<List<Integer>>();
		
		for (String line : lines) {
			String[] splitList = line.split(" ");
			List<Integer> report = new ArrayList<Integer>();
			for(int i=0; i<splitList.length; i++) {
				report.add(Integer.parseInt(splitList[i]));
			}
			reports.add(report);
		}
		
		//reports.forEach(report -> System.out.println(report));
	
		int result = 0;
		for (List<Integer> report : reports) {
			boolean safe = Main.isSafe(report);
			if(safe)
				result++;
		}
		System.out.println(result);
	}

	public static boolean isSafe(List<Integer> report) {
		boolean safe = true;
		boolean increasing = true;
		for (int i = 0; i < report.size() - 1; i++) {
			Integer currentNumber = report.get(i);
			Integer nextNumber = report.get(i + 1);
			Integer diff = nextNumber - currentNumber;
			
			// check if report keeps increasing / decreasing throughout
			if(i == 0) {
				if(diff < 0)
					increasing = false;
			} else if(increasing && diff < 0 || !increasing && diff > 0) {
				safe = false;
				break;
			}
			
			// check if difference stays in bounds
			if(Math.abs(diff) < 1 || Math.abs(diff) > 3) {
				safe = false;
				break;
			}
			
		}
		return safe;
	}

}
