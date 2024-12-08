
public class Tile implements Restorable{
	private TileType type;
	
	private int x;
	private int y;
	
	private TileType savedType;
	private int savedX;
	private int savedY;
	
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

	@Override
	public void save() {
		this.savedType = this.type;
		this.savedX = this.x;
		this.savedY = this.y;
	}

	@Override
	public void restore() {
		this.type = this.savedType;
		this.x = this.savedX;
		this.y = this.savedY;
	}
}
