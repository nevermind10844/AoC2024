import java.util.ArrayList;
import java.util.List;

public class Board implements CartesianWordDeliverer{
	private List<Tile> tileList;
	private int width;
	private int height;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.tileList = new ArrayList<>();
	}

	public void addTile(Tile tile) {
		this.tileList.add(tile);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= this.width || y >= this.height)
			return null;
		int index = this.width * y + x;
		if(index > this.getLength() - 1 || index < 0)
			return null;
		return this.tileList.get(this.width * y + x);
	}
	
	public Tile getTile(int i) {
		if(i < 0 || i > this.getLength() - 1)
			return null;
		return this.tileList.get(i);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getLength() {
		return this.width * this.height;
	}

	@Override
	public String getNorth(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			y--;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getNorthEast(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			y--;
			x++;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getEast(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			x++;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getSouthEast(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			x++;
			y++;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getSouth(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			y++;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getSouthWest(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			x--;
			y++;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getWest(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			x--;
		}
		return String.valueOf(charArr);
	}

	@Override
	public String getNorthWest(int x, int y, int length) {
		char[] charArr = new char[length];
		for(int j=0; j<length; j++) {
			Tile tile = this.getTile(x, y);
			if(tile != null) {
				charArr[j] = tile.getLetter();
			}
			y--;
			x--;
		}
		return String.valueOf(charArr);
	}


	
}
