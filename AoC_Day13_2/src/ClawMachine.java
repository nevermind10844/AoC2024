import java.util.HashMap;
import java.util.Map;

public class ClawMachine {
	
	private Map<Character, Button> buttonMap;
	private GridVector target;
	
	public ClawMachine(GridVector target) {
		this.target = target;
		this.buttonMap = new HashMap<>(2);
	}

	public void addButton(Character key, Button value) {
		this.buttonMap.put(key, value);
	}
	
	public Button getButton(Character key) {
		return this.buttonMap.get(key);
	}
	
	public GridVector getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return """
			ClawMachine [
				Button A: %s
				Button B: %s
				Target: %s
			]
			""".formatted(this.buttonMap.get('a'), this.buttonMap.get('b'), this.target);
	}
}
