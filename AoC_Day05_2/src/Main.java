import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
//		lines.forEach(line -> System.out.println(line));

		RuleSet ruleSet = new RuleSet();
		List<Update> updateList = new ArrayList<>();

		boolean updates = false;

		for (String line : lines) {
			if (updates) {
				Update update = new Update();
				List<Integer> split = Arrays.stream(line.split(",")).map(Integer::valueOf).toList();
				split.forEach(page -> update.addPage(page));
				updateList.add(update);
				continue;
			}

			if (line.isEmpty()) {
				updates = true;
				continue;
			}

			String[] split = line.split("\\|");
			int left = Integer.parseInt(split[0]);
			int right = Integer.parseInt(split[1]);

			ruleSet.getRule(right, true).addBefore(left);
		}

		System.out.println(ruleSet);

		List<Update> invalidUpdates = new ArrayList<>();

		for (Update update : updateList) {
			boolean valid = true;
			List<Integer> pageList = update.getPageList();
			for (int i = 0; i < pageList.size(); i++) {
				Integer page = pageList.get(i);
				Rule rule = ruleSet.getRule(page);
				if (rule != null) {
					List<Integer> mustBeBefore = rule.getBefore();
					for (int y = i + 1; y < pageList.size(); y++) {
						Integer isAfter = pageList.get(y);
						if (mustBeBefore.contains(isAfter))
							valid = false;
					}
				}
			}

			if (!valid)
				invalidUpdates.add(update);
		}

		int result = 0;
		
		for (Update update : invalidUpdates) {
			while (sort(update, ruleSet)) {
			}
			result += update.getPageList().get(update.getPageList().size()/2);
		}
		
		System.out.println(result);

	}

	private static boolean sort(Update update, RuleSet ruleSet) {
		boolean swapped = false;

		List<Integer> pageList = update.getPageList();

		for (int i = 0; i < pageList.size() - 1; i++) {
			Integer left = pageList.get(i);
			Integer right = pageList.get(i + 1);
			Rule rule = ruleSet.getRule(left);
			if (rule != null && rule.getBefore().contains(right)) {
				Collections.swap(pageList, i, i + 1);
				update.setPageList(pageList);
				swapped = true;
			}
		}

		return swapped;
	}

}
