
public class Tile {
	private TileType type;
	private char symbol;
	
	private int x;
	private int y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
	
	public TileType getType() {
		return this.type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}
