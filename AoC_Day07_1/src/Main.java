import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.graalvm.polyglot.Context;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
//		lines.forEach(System.out::println);
		
		List<Equation> equations = new ArrayList<>();

		for (String line : lines) {
			String[] split = line.split(": ");
			Equation eq = new Equation(new BigInteger(split[0]));
			Arrays.stream(split[1].split(" "))
					.map(Long::parseLong)
					.forEach(operand -> eq.addOperand(operand));
			equations.add(eq);
		}
		
		Context context = Context.create();
		
		BigInteger result = new BigInteger("0");
		for (Equation equation : equations) {
			EquationRunner runner = new EquationRunner(equation, 100000, context);
			runner.start();
			try {
				runner.join();
				result = result.add(runner.evaluate());
				System.out.println(result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		equations.forEach(System.out::println);
		
	}

}
