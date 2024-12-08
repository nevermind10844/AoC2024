import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Rule {
	private int number;
	private List<Integer> before;

	public Rule(int number) {
		this.number = number;
		this.before = new ArrayList<>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Integer> getBefore() {
		return before;
	}

	public void addBefore(int before) {
		if (!this.before.contains(before))
			this.before.add(before);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		return number == other.number;
	}

	@Override
	public String toString() {
		String beforeString = this.before
				.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(","));
		
		return "%d [%s] ".formatted(this.number, beforeString);
	}

}
