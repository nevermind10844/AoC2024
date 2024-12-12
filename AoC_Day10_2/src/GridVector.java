
public class GridVector {
	private int x;
	private int y;
	
	public GridVector(int x, int y) {
		this.x = x;
		this.y = y;
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
		return "(%d,%d)".formatted(this.x, this.y);
	}
}
