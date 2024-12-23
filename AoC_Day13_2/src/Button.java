
public class Button {
	private final char name;
	private final int cost;
	private final GridVector movement;

	public Button(char name, int cost, GridVector movement) {
		this.name = name;
		this.cost = cost;
		this.movement = movement;
	}

	public char getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public GridVector getMovement() {
		return movement;
	}

	@Override
	public String toString() {
		return "%s-Button for %d token  moving %s".formatted(name, cost, movement);
	}
}
