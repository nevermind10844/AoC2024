import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	private static int maxIterations = 75;

	public static void main(String[] args) {

		List<String> lines = InputReader.readInput();

		List<Long> immutableNumbers = Arrays.stream(lines.get(0).split(" ")).map(Long::valueOf).toList();
		List<Long> numbers = new ArrayList<>(immutableNumbers);

		String line = numbers.stream().map(String::valueOf).collect(Collectors.joining(" "));
		System.out.println(line);

		List<Long> currentNumbers = new ArrayList<>();
		for (Long number : numbers) {
			currentNumbers.add(number);
		}

		Map<Integer, Map<Long, Long>> lookupTable = new HashMap<>();

		long result = solveProblem(0, currentNumbers, lookupTable);

		System.out.println(result);

	}

	public static long solveProblem(int iteration, List<Long> currentNumbers,
			Map<Integer, Map<Long, Long>> lookupTable) {
		if (iteration == maxIterations) {
			return currentNumbers.size();
		}

		long count = 0;

		iteration++;
		for (Long currentNumber : currentNumbers) {
			lookupTable.putIfAbsent(iteration, new HashMap<>());
			Map<Long, Long> iterationCache = lookupTable.get(iteration);

			if (iterationCache.containsKey(currentNumber)) {
				count += iterationCache.get(currentNumber);
			} else {
				List<Long> chunkResult = applyRules(currentNumber);
				long result = solveProblem(iteration, chunkResult, lookupTable);

				iterationCache.put(currentNumber, result);
				count += result;
			}
		}
		return count;
	}

	public static List<Long> applyRules(Long input) {
		List<Long> numbers = new ArrayList<>(2);
		if (input.equals(0L)) {
			numbers.add(1L);
		} else if (String.valueOf(input).length() % 2 == 0) {
			String numberString = String.valueOf(input);
			int length = numberString.length();
			
			String leftHalf = numberString.substring(0, length / 2);
			String rightHalf = numberString.substring(length / 2, length);
			
			numbers.add(Long.valueOf(rightHalf));
			numbers.add(Long.valueOf(leftHalf));
		} else {
			numbers.add(input * 2024);
		}
		return numbers;
	}

}
