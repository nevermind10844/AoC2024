
public class Tile {
	private TileType type;
	private char symbol;
	private int counter;
	
	private int x;
	private int y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.type = TileType.EMPTY;
		this.symbol = TileType.EMPTY.getTileChar();
		this.counter = 0;
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
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void increaseCounter() {
		this.counter++;
	}

	@Override
	public String toString() {
		return "%s (%d::%d) [%d]".formatted(this.symbol, this.x, this.y, this.counter);
	}
}
