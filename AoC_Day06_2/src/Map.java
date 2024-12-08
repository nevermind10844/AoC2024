import java.util.ArrayList;
import java.util.List;

public class Map implements Restorable{
	private List<Tile> tileList;
	private int width;
	private int height;

	public Map(int width, int height) {
		this.tileList = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public void addTile(Tile tile) {
		this.tileList.add(tile);
	}

	public Tile getTile(int index) {
		return this.tileList.get(index);
	}

	public Tile getTile(int x, int y) {
		return this.getTile(y * this.width + x);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Tile findGuard() {
		return this.tileList
				.stream()
				.filter(tile -> tile.getType().equals(TileType.OCCUPIED))
				.findFirst()
				.orElseThrow();
	}
	
	public List<Tile> getControlledTiles(){
		return this.tileList
				.stream()
				.filter(tile -> tile.getType().equals(TileType.CONTROLLED))
				.toList();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				sb.append(this.getTile(x, y).getType().getTileChar());
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void save() {
		this.tileList.forEach(tile -> tile.save());
	}

	@Override
	public void restore() {
		this.tileList.forEach(tile -> tile.restore());
	}

}
