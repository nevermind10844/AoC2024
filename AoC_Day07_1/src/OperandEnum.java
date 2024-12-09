import java.util.Arrays;
import java.util.Random;

public enum OperandEnum {
	PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/");

	private String operandString;

	private OperandEnum(String operandString) {
		this.operandString = operandString;
	}

	public String getOperandAsString() {
		return this.operandString;
	}

	public static OperandEnum getEnumByString(String operandString) {
		return Arrays.stream(OperandEnum.values())
				.filter(operandEnum -> operandEnum.getOperandAsString().equals(operandString)).findFirst()
				.orElseThrow();
	}

	public static OperandEnum getRandomEnum() {
		Random random = new Random();
		int randomIndex = random.nextInt(4); // Generates a number between 0 and 3

		switch (randomIndex) {
			case 0:
				return PLUS;
			case 1:
				return MINUS;
			case 2:
				return TIMES;
			case 3:
				return DIVIDE;
			default:
				return PLUS;
		}
	}
}
