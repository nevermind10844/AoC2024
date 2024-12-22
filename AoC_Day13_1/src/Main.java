import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		// lines.forEach(System.out::println);

		List<ClawMachine> machines = new ArrayList<>();

		for (int i = 0; i <= lines.size() - 3; i += 4) {
			String values = lines.get(i).split(": ")[1];
			String[] splitValues = values.split(", ");

			int x = Integer.parseInt(splitValues[0].split("\\+")[1]);
			int y = Integer.parseInt(splitValues[1].split("\\+")[1]);

			Button a = new Button('A', 3, new GridVector(x, y));

			values = lines.get(i + 1).split(": ")[1];
			splitValues = values.split(", ");

			x = Integer.parseInt(splitValues[0].split("\\+")[1]);
			y = Integer.parseInt(splitValues[1].split("\\+")[1]);

			Button b = new Button('B', 1, new GridVector(x, y));

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

		List<String> permutations = new ArrayList<>();

		for (int i = 200; i > 0; i--) {
			findPermutations(i, baseString, permutations);
		}
		
		int result = 0;
		
		for (ClawMachine machine : machines) {
			System.out.println(machine);
			
			int targetX = machine.getTarget().getX();
			int aButtonX = machine.getButton('a').getMovement().getX();
			int bButtonX = machine.getButton('b').getMovement().getX();
			
			int targetY = machine.getTarget().getY();
			int aButtonY = machine.getButton('a').getMovement().getY();
			int bButtonY = machine.getButton('b').getMovement().getY();
			
			int aButtonCost = machine.getButton('a').getCost();
			int bButtonCost = machine.getButton('b').getCost();
			
			int cheapest = Integer.MAX_VALUE;
			
			for(int i=0; i<permutations.size(); i++) {
				String permString = permutations.get(i);
				int aCount = countCharacter(permString, 'a');
				int bCount = permString.length() - aCount;
				int permXValue = aCount * aButtonX + bCount * bButtonX;
				if(permXValue == targetX) {
					int permYValue = aCount * aButtonY + bCount * bButtonY;
					if(targetY == permYValue) {
						int permCost = aCount * aButtonCost + bCount * bButtonCost;
						if(permCost < cheapest) {
							System.out.println(aCount + " : " + bCount);
							System.out.println(aButtonCost + " : " + bButtonCost);
							cheapest = permCost;
						}
					}
				}
			}
			
			if(cheapest == Integer.MAX_VALUE)
				cheapest = 0;
			
			System.out.println(cheapest);
			
			result += cheapest;
			
		}
		System.err.println(result);
	}
	
	private static int countCharacter(String str, char c) {
		int count = 0;
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i) == c)
				count++;
		}
		return count;
	}

	private static void findPermutations(int len, String baseString, List<String> permutations) {
		int pos = 0;
		while (baseString.length() >= pos + len) {
			String current = baseString.substring(pos, pos + len);
			if (!permutations.contains(current)) {
				permutations.add(current);
			}
			pos++;
		}

	}

}