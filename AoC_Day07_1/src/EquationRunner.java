import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class EquationRunner extends Thread {

	private Equation eq;
	private int tryCount;
	private Context engine;
	private String templateString;
	
	private boolean done;

	private Map<String, BigInteger> resultMap;

	public EquationRunner(Equation eq, int multiplier, Context engine) {
		this.done = false;
		this.engine = engine;
		this.eq = eq;
		this.tryCount = this.eq.operands.size() * multiplier;
		this.templateString = "(" + this.eq.operands.get(0) + "%s" + this.eq.operands.get(1) + ")";
		for (int i = 2; i < this.eq.operands.size(); i++) {
			this.templateString = "(" + this.templateString + "%s" + this.eq.operands.get(i) + ")";
		}
		this.resultMap = new HashMap<>();
	}

	@Override
	public void run() {
		for (int i = 0; i < this.tryCount; i++) {
			String expression = this.createExpression();
			if (!this.resultMap.containsKey(expression)) {
				Value result = this.engine.eval("js", expression);

				try {
					BigInteger bigIntValue;

					if (result.fitsInInt()) {
						// If the result is an int, convert to BigInteger
						bigIntValue = BigInteger.valueOf(result.asInt());
					} else if (result.fitsInLong()) {
						// If the result is a long, convert to BigInteger
						bigIntValue = BigInteger.valueOf(result.asLong());
					} else {
						// For larger numbers that fit directly in BigInteger
						bigIntValue = result.as(BigInteger.class);
					}
					
//					System.out.println(bigIntValue);
					
					this.resultMap.put(expression, bigIntValue);
				} catch (ClassCastException cce) {

				}
			}
		}
		this.done = true;
	}

	private String createExpression() {
		String[] arr = new String[this.eq.operands.size() - 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = OperandEnum.getRandomEnum().getOperandAsString();
		}
		String exp = this.templateString.formatted((Object[]) arr);
		return exp;
	}

	public BigInteger evaluate() {
		List<String> filteredMap = this.resultMap.entrySet().stream()
				.filter(entry -> entry.getValue().equals(this.eq.result)).map(Map.Entry::getKey).toList();
		return filteredMap.size() > 0 ? this.eq.result : new BigInteger("0");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.resultMap.entrySet().stream()
				.forEach((entry) -> sb.append("%s: %d\n".formatted(entry.getClass(), entry.getValue())));
		return sb.toString();
	}
	
	public boolean isDone() {
		return this.done;
	}

}
