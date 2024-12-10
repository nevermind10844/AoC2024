import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();

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
				permutationLib.put(opCount, OperatorEnum.getPermutations(opCount));
			}
		}

		BigInteger result = new BigInteger("0");

		for (Equation equation : equations) {
			EquationRunner runner = new EquationRunner(equation, permutationLib.get(equation.getOperatorCount()));
			runner.start();
			try {
				runner.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = result.add(runner.evaluate());
			System.err.println(result);
		}
	}
}
