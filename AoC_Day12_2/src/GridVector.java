
public class GridVector {
	private int x;
	private int y;
	private String name;

	public GridVector(int x, int y) {
		this.x = x;
		this.y = y;
		this.setName("undef.");
	}
	
	public GridVector(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void invert() {
		this.x *= -1;
		this.y *= -1;
	}

	public int magnitude() {
		return Math.abs(this.x) + Math.abs(this.y);
	}

	public static GridVector fromTiles(Tile a, Tile b) {
		return new GridVector(b.getX() - a.getX(), b.getY() - a.getY());
	}

	@Override
	public String toString() {
		return "[%d,%d (%s)]".formatted(this.x, this.y, this.name);
	}

	public static GridVector copy(GridVector v) {
		return new GridVector(v.getX(), v.getY());
	}

	public static GridVector copyInverted(GridVector v) {
		GridVector v2 = new GridVector(v.getX(), v.getY());
		v2.invert();
		return v2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GridVector v) {
			return this.x == v.x && this.y == v.y;
		}
		return false;
	}

	public static GridVector up() {
		return new GridVector(0, -1, "up");
	}

	public static GridVector right() {
		return new GridVector(1, 0, "right");
	}

	public static GridVector down() {
		return new GridVector(0, 1, "down");
	}

	public static GridVector left() {
		return new GridVector(-1, 0, "left");
	}
}