import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	private static long newValueBase = 0l;

	public static void main(String[] args) {

		List<String> lines = InputReader.readTestInput();
//		List<String> lines = InputReader.readInput();
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

			long cheapest = findCheapestSolution(targetX, aButtonX, bButtonX, targetY, aButtonY, bButtonY, aButtonCost,
					bButtonCost);

			System.out.println(cheapest);

			result += cheapest;
		}
		System.err.println(result);
	}

	/**
	 * After hours of digging the internet, I learned 3 things: 1. a * aStepSizeX +
	 * b * bStepSizeX = targetX is called the diophantine problem. (Linear
	 * Diophantine Equation in two variables) 2. if a number (such as targetX) is
	 * divisible by the GCD of 2 numbers (like aStepSizeX and bStepSizeX there is a
	 * solution to that diophantine problem! 3. my math skills are not good enough
	 * but i was able to convert "the internet" to the following abbomination!
	 * 
	 * @param targetX
	 * @param aButtonX
	 * @param bButtonX
	 * @param targetY
	 * @param aButtonY
	 * @param bButtonY
	 * @param aButtonCost
	 * @param bButtonCost
	 * @return
	 */
	private static long findCheapestSolution(long cX, long x1, long x2, long targetY, long aButtonY,
			long bButtonY, long aButtonCost, long bButtonCost) {
		long gcd = gcd(x1, x2);

	    if (cX % gcd != 0) {
	        System.out.println("No solutions exist.");
	        return 0l;
	    }

	    // Scale the coefficients and target
	    long k = cX / gcd;
	    long x1Scaled = x1 / gcd;
	    long x2Scaled = x2 / gcd;

	    // Get one solution using Extended GCD
	    long[] initialSolution = extendedGCD(x1Scaled, x2Scaled);
	    long a1 = initialSolution[0] * k;
	    long a2 = initialSolution[1] * k;

	    // Step sizes for general solution
	    long stepA1 = x2Scaled;
	    long stepA2 = x1Scaled;

	    // Calculate bounds for k to ensure non-negative solutions
	    long kMinA1 = (a1 < 0) ? (-a1 + stepA1 - 1) / stepA1 : 0; // Ceiling of -a1 / stepA1
	    long kMaxA2 = (a2 < 0) ? (-a2 / stepA2) : Long.MAX_VALUE;  // Floor of a2 / stepA2
	    long kMin = Math.max(kMinA1, 0); // k must be at least 0
	    long kMax = Math.min(kMaxA2, Long.MAX_VALUE); // Upper bound for k

	    // Generate solutions within the valid range
	    for (long kCurrent = kMin; kCurrent <= kMax; kCurrent++) {
	        long currentA1 = a1 + kCurrent * stepA1;
	        long currentA2 = a2 - kCurrent * stepA2;

	        if (currentA1 >= 0 && currentA2 >= 0) {
	            System.out.println("a1 = " + currentA1 + ", a2 = " + currentA2);
	        }
	    }
	    return 0l;
	}

	public static long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	public static long[] extendedGCD(long a, long b) {
		if (b == 0)
			return new long[] { 1, 0, a };
		long[] result = extendedGCD(b, a % b);
		long x = result[1];
		long y = result[0] - (a / b) * result[1];
		return new long[] { x, y, result[2] };
	}

}