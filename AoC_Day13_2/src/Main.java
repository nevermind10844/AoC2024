import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	private static long newValueBase = 10000000000000L;

	public static void main(String[] args) {

		List<String> lines = InputReader.readTestInput(2);
		// lines.forEach(System.out::println);

		List<ClawMachine> machines = new ArrayList<>();

		for (int i = 0; i <= lines.size() - 3; i += 4) {
			String values = lines.get(i).split(": ")[1];
			String[] splitValues = values.split(", ");

			long x = Long.parseLong(splitValues[0].split("\\+")[1]);
			long y = Long.parseLong(splitValues[1].split("\\+")[1]);

			Button a = new Button('A', 3, new GridVector(x, y));

			values = lines.get(i + 1).split(": ")[1];
			splitValues = values.split(", ");

			x = Long.parseLong(splitValues[0].split("\\+")[1]);
			y = Long.parseLong(splitValues[1].split("\\+")[1]);

			Button b = new Button('B', 1, new GridVector(x, y));

			values = lines.get(i + 2).split(": ")[1];
			splitValues = values.split(", ");

			x = newValueBase + Long.parseLong(splitValues[0].split("=")[1]);
			y = newValueBase + Long.parseLong(splitValues[1].split("=")[1]);

			GridVector target = new GridVector(x, y);

			ClawMachine machine = new ClawMachine(target);
			machine.addButton('a', a);
			machine.addButton('b', b);
			machines.add(machine);
		}

		long result = 0;

		for (ClawMachine machine : machines) {
			System.out.println(machine);

			long targetX = machine.getTarget().getX();
			long aButtonX = machine.getButton('a').getMovement().getX();
			long bButtonX = machine.getButton('b').getMovement().getX();

			long targetY = machine.getTarget().getY();
			long aButtonY = machine.getButton('a').getMovement().getY();
			long bButtonY = machine.getButton('b').getMovement().getY();

			long aButtonCost = machine.getButton('a').getCost();
			long bButtonCost = machine.getButton('b').getCost();

			long cheapest = Long.MAX_VALUE;
			
			boolean valueFound = false;
			
			boolean xValueHasPrio = aButtonX > aButtonY;
			
			long prioValue	= xValueHasPrio ? aButtonX : aButtonY;
			long prioTarget = xValueHasPrio ? targetX : targetY;
			
			long trailingValue = xValueHasPrio ? bButtonX : bButtonY;
			long trailingTarget = xValueHasPrio ? targetY : targetX;

			for (long aButtonValue = 0; aButtonValue <= prioTarget; aButtonValue+=prioValue) {
				long remainder = prioTarget - aButtonValue;
				if (remainder % trailingValue == 0) {
					long bCount = remainder / trailingValue;
					long aCount = aButtonValue / prioValue;
					long permYValue = aCount * aButtonY + bCount * bButtonY;
					if (trailingTarget == permYValue) {
						System.out.println(aCount + " ?? " + bCount);
						long permCost = aCount * aButtonCost + bCount * bButtonCost;
						if (permCost < cheapest) {
							System.out.println(aCount + " !! " + bCount);
							cheapest = permCost;
							valueFound = true;
							break;
						}
					}
				}
			}

			if (!valueFound)
				cheapest = 0;

			System.out.println(cheapest);

			result += cheapest;

		}
		System.err.println(result);
	}

}