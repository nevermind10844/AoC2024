import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Grid {
	private List<Tile> tileList;
	private int width;
	private int height;

	public Grid(int width, int height) {
		this.tileList = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public void addTile(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();
		if (x < 0 || y < 0 || x >= this.width || y >= this.height)
			throw new IndexOutOfBoundsException(
					"Index is out of bounds for x = %d and y = %d with grid of width %d and height %d".formatted(x, y,
							this.width, this.height));
		this.tileList.add(tile);
	}

	public Tile getTile(int index) {
		if (index < 0 || index >= this.tileList.size())
			throw new IndexOutOfBoundsException("Index is out of bounds for index = %d with list of size %d"
					.formatted(index, this.tileList.size()));
		return this.tileList.get(index);
	}

	public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
		if (x < 0 || y < 0 || x >= this.width || y >= this.height)
			throw new IndexOutOfBoundsException(
					"Index is out of bounds for x = %d and y = %d with grid of width %d and height %d".formatted(x, y,
							this.width, this.height));
		return this.getTile(y * this.width + x);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Tile findTile(TileType type) {
		return this.tileList.stream().filter(tile -> tile.getType().equals(type)).findFirst().orElseThrow();
	}

	public List<Tile> findTiles(TileType type) {
		return this.tileList.stream().filter(tile -> tile.getType().equals(type)).toList();
	}

	public List<Tile> getNeighbors(Tile tile) {
		List<Tile> neighbours = new ArrayList<>();

		Tile neighbor = this.northernNeighbor(tile);
		if(neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.easternNeighbor(tile);
		if(neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.southernNeighbor(tile);
		if(neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.westernNeighbor(tile);
		if(neighbor != null)
			neighbours.add(neighbor);

		return neighbours;
	}

	public Tile northernNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX(), tile.getY() - 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile easternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() + 1, tile.getY());
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile southernNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX(), tile.getY() + 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile westernNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() - 1, tile.getY());
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public static Grid parse(List<String> lines) {
		int width = lines.get(0).length();
		int height = lines.size();
		Grid map = new Grid(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile t = new Tile(x, y);
				t.setSymbol(lines.get(y).charAt(x));
				TileType type = null;
				try {
					type = TileType.tryParse(t.getSymbol());
				} catch (NoSuchElementException e) {
					type = TileType.EMPTY;
				}
				t.setType(type);
				map.addTile(t);
			}
		}
		return map;
	}

	public static Grid create(int width, int height) {
		Grid map = new Grid(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile t = new Tile(x, y);
				t.setType(TileType.EMPTY);
				t.setSymbol(TileType.EMPTY.getTileChar());
				map.addTile(t);
			}
		}
		return map;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				sb.append(this.getTile(x, y).getSymbol());
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String highlightSymbol(Character symbol) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Tile t = this.getTile(x, y);
				if (symbol == t.getSymbol())
					sb.append(this.getTile(x, y).getSymbol());
				else
					sb.append(TileType.EMPTY.getTileChar());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
