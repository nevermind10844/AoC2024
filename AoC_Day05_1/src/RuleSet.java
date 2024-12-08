import java.util.ArrayList;
import java.util.List;

public class RuleSet {
	private List<Rule> ruleList;
	
	public RuleSet() {
		this.ruleList = new ArrayList<>();
	}

	public Rule getRule(int number, boolean createIfNull) {
		Rule rule = this.ruleList.stream()
				.filter(r -> r.getNumber() == number)
				.findFirst()
				.orElse(null);
		if(rule == null && createIfNull) {
			rule = new Rule(number);
			this.ruleList.add(rule);
		}
		return rule;
	}
	
	public Rule getRule(int number) {
		return this.getRule(number, false);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Rule rule : ruleList) {
			sb.append(rule.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
