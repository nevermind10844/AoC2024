import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
//		lines.forEach(System.out::println);

		List<Equation> equations = new ArrayList<>();

		for (String line : lines) {
			String[] split = line.split(": ");
			Equation eq = new Equation(new BigInteger(split[0]));
			Arrays.stream(split[1].split(" ")).map(BigInteger::new).forEach(operand -> eq.addOperand(operand));
			equations.add(eq);
		}

		Map<Integer, List<List<OperatorEnum>>> permutationLib = new HashMap<>();
		
		for (Equation eq : equations) {
			int opCount = eq.getOperatorCount();
			if(!permutationLib.containsKey(opCount)) {
				System.out.println("producing permutations for operatorCount: " + opCount);

				List<List<OperatorEnum>> permutations = OperatorEnum.getPermutations(opCount);
				
//				permutations.forEach(System.out::println);
				System.out.println(permutations.size() + " produced for operatorCount: " + opCount);
				permutationLib.put(opCount, permutations);
			}
		}

		BigInteger result = new BigInteger("0");

		List<EquationRunner> runners = new ArrayList<>();
		
		for (Equation equation : equations) {
			EquationRunner runner = new EquationRunner(equation, permutationLib.get(equation.getOperatorCount()));
			runners.add(runner);
			runner.start();
			try {
				runner.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = result.add(runner.evaluate());
			System.err.println(result);
		}
		
		int validCount = runners.stream().filter(EquationRunner::isValid).toList().size();
		System.out.println(validCount);
	}

}
