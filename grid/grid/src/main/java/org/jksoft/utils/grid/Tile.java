package org.jksoft.utils.grid;

public class Tile {
	private TileType type;
	private char symbol;
	
	private int x;
	private int y;
	
	private int externalId;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.type = TileType.EMPTY;
		this.symbol = TileType.EMPTY.getTileChar();
		this.externalId = -1;
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

	public int getExternalId() {
		return externalId;
	}

	public void setExternalId(int externalId) {
		this.externalId = externalId;
	}

	@Override
	public String toString() {
		return "%s (%d::%d) [%d]".formatted(this.symbol, this.x, this.y, this.externalId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Tile t) {
			return t.getX() == this.getX() && t.getY() == this.getY();
		}
		return false;
	}
}
