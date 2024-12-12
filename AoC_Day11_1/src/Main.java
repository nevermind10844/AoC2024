import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();

		List<Long> numbers = Arrays.stream(lines.get(0).split(" ")).map(Long::valueOf).toList();

		String line = numbers.stream().map(String::valueOf).collect(Collectors.joining(" "));
		System.out.println(line);

		for (int i = 0; i < 75; i++) {
			numbers = applyRules(numbers);
//			line = numbers.stream().map(String::valueOf).collect(Collectors.joining(" "));
//			System.out.println(line);
		}
		
		System.out.println(numbers.size());

	}

	public static List<Long> applyRules(List<Long> currentList) {
		List<Long> numbers = new ArrayList<>();
		for (Long number : currentList) {
			if (number.equals(0L)) {
				numbers.add(1L);
			} else if (String.valueOf(number).length() % 2 == 0) {
				String numberString = String.valueOf(number);
				int length = numberString.length();
				String leftHalf = numberString.substring(0, length / 2);
				String rightHalf = numberString.substring(length / 2, length);
				numbers.add(Long.valueOf(leftHalf));
				numbers.add(Long.valueOf(rightHalf));
			} else {
				numbers.add(number * 2024);
			}
		}
		return numbers;
	}

}
