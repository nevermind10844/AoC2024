import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OperatorEnum {
	PLUS("+"), TIMES("*"), COMBINE("||");

	private String operandString;

	private OperatorEnum(String operandString) {
		this.operandString = operandString;
	}

	public String getOperandAsString() {
		return this.operandString;
	}

	public static OperatorEnum getEnumByString(String operandString) {
		return Arrays.stream(OperatorEnum.values())
				.filter(operandEnum -> operandEnum.getOperandAsString().equals(operandString)).findFirst()
				.orElseThrow();
	}

	public static List<List<OperatorEnum>> getPermutations(int length) {
		List<List<OperatorEnum>> results = new ArrayList<>();
		generatePermutations(Arrays.asList(OperatorEnum.values()), length, new ArrayList<>(), results);
		return results;
	}

	private static void generatePermutations(List<OperatorEnum> input, int length, List<OperatorEnum> current,
			List<List<OperatorEnum>> results) {
		if (current.size() == length) {
			results.add(new ArrayList<>(current));
			return;
		}

		for (OperatorEnum operator : input) {
			current.add(operator);
			generatePermutations(input, length, current, results);
			current.remove(current.size() - 1);
		}
	}
}
