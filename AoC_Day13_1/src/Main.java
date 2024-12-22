import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readTestInput(2);
		// lines.forEach(System.out::println);

		List<ClawMachine> machines = new ArrayList<>();

		for (int i = 0; i <= lines.size() - 3; i += 4) {
			String values = lines.get(i).split(": ")[1];
			String[] splitValues = values.split(", ");

			int x = Integer.parseInt(splitValues[0].split("\\+")[1]);
			int y = Integer.parseInt(splitValues[1].split("\\+")[1]);

			Button a = new Button('A', 1, new GridVector(x, y));

			values = lines.get(i + 1).split(": ")[1];
			splitValues = values.split(", ");

			x = Integer.parseInt(splitValues[0].split("\\+")[1]);
			y = Integer.parseInt(splitValues[1].split("\\+")[1]);

			Button b = new Button('B', 3, new GridVector(x, y));

			values = lines.get(i + 2).split(": ")[1];
			splitValues = values.split(", ");

			x = Integer.parseInt(splitValues[0].split("=")[1]);
			y = Integer.parseInt(splitValues[1].split("=")[1]);

			GridVector target = new GridVector(x, y);

			ClawMachine machine = new ClawMachine(target);
			machine.addButton('a', a);
			machine.addButton('b', b);
			machines.add(machine);
		}

		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 100; i++) {
			sb.append("a");
		}
		for (int i = 0; i < 100; i++) {
			sb.append("b");
		}
		
		String baseString = sb.toString();
		
		System.out.println(baseString);
		
		for (ClawMachine clawMachine : machines) {
			int targetX = clawMachine.getTarget().getX();
			int targetY = clawMachine.getTarget().getY();
			
			List<List<Integer>> results = new ArrayList<>();
			
			findPermutations(targetX,
					List.of(clawMachine.getButton('a').getMovement().getX(),
							clawMachine.getButton('b').getMovement().getY()),
					new ArrayList<>(), results);
			
			for (List<Integer> validResult : results) {
				System.out.println(validResult);
			}
		}
	}
	
	/**
	 * Stupid Dynamic Programming-Approach
	 * 
	 * @param target
	 * @param numbers
	 * @param current
	 * @param result
	 * 
	 * First: define basecase -> when target hits zero, this is a valid path!
	 * 
	 * Second: define exitcase -> 	when target goes below zero, this is an invalid path!
	 * 
	 * Third: define algorithm ->	iterate over numbers (the 2 possible values to add up)
	 * 									add current number (from iteration) to the current result-list (current)
	 * 									call the method again with current number subtractet from the current target value
	 * 									backtrack for next iteration
	 * 
	 * I hate this!
	 */
	public static void findPermutations(int target, List<Integer> numbers, List<Integer> current, List<List<Integer>> result) {
		System.out.println(current);
		
		if (target == 0) {
			result.add(new ArrayList<>(current));
			return;
		}

		if (target < 0) {
			return;
		}

		for (int number : numbers) {
			current.add(number);
			findPermutations(target - number, numbers, current, result);
			current.remove(current.size() - 1);
		}
	}

}