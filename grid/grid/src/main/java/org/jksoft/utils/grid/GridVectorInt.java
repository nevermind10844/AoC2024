package org.jksoft.utils.grid;

public class GridVectorInt {
	private int x;
	private int y;
	
	public GridVectorInt(int x, int y) {
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

	public void add(GridVectorInt other) {
		this.x += other.x;
		this.y += other.y;
	}

	public int magnitude() {
		return Math.abs(this.x) + Math.abs(this.y);
	}

	public static GridVectorInt fromTiles(Tile a, Tile b) {
		return new GridVectorInt(b.getX() - a.getX(), b.getY() - a.getY());
	}
	
	public static GridVectorInt add(GridVectorInt a, GridVectorInt b) {
		return new GridVectorInt(a.getX() + b.getX(), a.getY() + b.getY());
	}
	
	@Override
	public String toString() {
		return "[%d,%d]".formatted(this.x, this.y);
	}

	public static GridVectorInt copy(GridVectorInt v) {
		return new GridVectorInt(v.getX(), v.getY());
	}

	public static GridVectorInt copyInverted(GridVectorInt v) {
		GridVectorInt v2 = new GridVectorInt(v.getX(), v.getY());
		v2.invert();
		return v2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GridVectorInt v) {
			return this.x == v.x && this.y == v.y;
		}
		return false;
	}
	
	public static GridVectorInt up() {
		return new GridVectorInt(0, -1);
	}

	public static GridVectorInt right() {
		return new GridVectorInt(1, 0);
	}

	public static GridVectorInt down() {
		return new GridVectorInt(0, 1);
	}

	public static GridVectorInt left() {
		return new GridVectorInt(-1, 0);
	}
}