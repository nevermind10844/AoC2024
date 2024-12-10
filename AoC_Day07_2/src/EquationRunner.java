import java.math.BigInteger;
import java.util.List;

public class EquationRunner extends Thread {

	private Equation eq;
	private List<List<OperatorEnum>> permutations;

	private boolean valid;

	public EquationRunner(Equation eq, List<List<OperatorEnum>> permutations) {
		this.valid = false;
		this.permutations = permutations;
		this.eq = eq;
	}

	@Override
	public void run() {
		BigInteger result = this.eq.result;
		for (List<OperatorEnum> permutation : permutations) {
			BigInteger left = this.eq.operands.get(0);
			for (int i = 0; i < permutation.size(); i++) {
				OperatorEnum operator = permutation.get(i);
				BigInteger right = this.eq.operands.get(i + 1);
				switch (operator) {
				case PLUS:
					left = left.add(right);
					break;
				case COMBINE:
					left = new BigInteger("%s%s".formatted(left.toString(), right.toString()));
					break;
				case TIMES:
					left = left.multiply(right);
					break;
				}
			}
			
			if(result.equals(left)) {
				this.valid = true;
				break;
			}
			
		}
	}
	
	public boolean isValid() {
		return this.valid;
	}

	public BigInteger evaluate() {
		return this.valid ? this.eq.result : new BigInteger("0");
	}

}
