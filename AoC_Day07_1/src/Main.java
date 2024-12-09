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
			Arrays.stream(split[1].split(" ")).map(Long::parseLong).forEach(operand -> eq.addOperand(operand));
			equations.add(eq);
		}

		Context context = Context.create();

		List<EquationRunner> runnerList = new ArrayList<>();

		for (Equation equation : equations) {
			EquationRunner runner = new EquationRunner(equation, 30000, context);
			runnerList.add(runner);
		}

		RunnerQueue queue = new RunnerQueue(8, runnerList);
		queue.start();

		while (!queue.isDone()) {
			List<EquationRunner> doneList = queue.getDoneList();
			if (doneList != null && doneList.size() > 0) {
				BigInteger result = doneList.stream().map(EquationRunner::evaluate).reduce(BigInteger.ZERO,
						BigInteger::add);
				System.err.println(result);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		equations.forEach(System.out::println);

	}

}
