import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Equation {
	public BigInteger result;
	public List<BigInteger> operands;

	public Equation(BigInteger result) {
		this.result = result;
		this.operands = new ArrayList<>();
	}

	public BigInteger getResult() {
		return this.result;
	}

	public void addOperand(BigInteger operand) {
		this.operands.add(operand);
	}

	public int getOperatorCount() {
		return this.operands.size() - 1;
	}

	@Override
	public String toString() {
		return "%d: %s".formatted(this.result,
				this.operands.stream().map(String::valueOf).collect(Collectors.joining(" ")));
	}
}
