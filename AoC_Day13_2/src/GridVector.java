
public class GridVector {
	private long x;
	private long y;

	public GridVector(long x, long y) {
		this.x = x;
		this.y = y;
	}


	public long getX() {
		return this.x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return this.y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public void invert() {
		this.x *= -1;
		this.y *= -1;
	}

	public long magnitude() {
		return Math.abs(this.x) + Math.abs(this.y);
	}

	public static GridVector fromTiles(Tile a, Tile b) {
		return new GridVector(b.getX() - a.getX(), b.getY() - a.getY());
	}

	@Override
	public String toString() {
		return "[%d,%d]".formatted(this.x, this.y);
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
}