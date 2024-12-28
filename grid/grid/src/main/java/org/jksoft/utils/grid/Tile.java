package org.jksoft.utils.grid;

public class Tile {
	private TileType type;
	private char symbol;
	
	private int x;
	private int y;
	
	private boolean topBorder;
	private boolean rightBorder;
	private boolean bottomBorder;
	private boolean leftBorder;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.type = TileType.EMPTY;
		this.symbol = TileType.EMPTY.getTileChar();
		this.topBorder = false;
		this.rightBorder = false;
		this.bottomBorder = false;
		this.leftBorder = false;
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
	
	public boolean isTopBorder() {
		return topBorder;
	}

	public void setTopBorder(boolean topBorder) {
		this.topBorder = topBorder;
	}

	public boolean isRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(boolean rightBorder) {
		this.rightBorder = rightBorder;
	}

	public boolean isBottomBorder() {
		return bottomBorder;
	}

	public void setBottomBorder(boolean bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public boolean isLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(boolean leftBorder) {
		this.leftBorder = leftBorder;
	}

	@Override
	public String toString() {
		return "%s (%d::%d)".formatted(this.symbol, this.x, this.y);
	}
}
