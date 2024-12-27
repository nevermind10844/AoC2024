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

	public int getLength() {
		return this.width * this.height;
	}

	public Tile findTile(TileType type) {
		return this.tileList.stream().filter(tile -> tile.getType().equals(type)).findFirst().orElseThrow();
	}

	public List<Tile> findTiles(TileType type) {
		return this.tileList.stream().filter(tile -> tile.getType().equals(type)).toList();
	}

	public boolean hasNeighbors(Tile tile) {
		return this.getNeighbors(tile).size() > 0;
	}

	public List<Tile> getNeighbors(Tile tile) {
		List<Tile> neighbours = new ArrayList<>();

		Tile neighbor = this.getNorthernNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			neighbours.add(neighbor);
		neighbor = this.getEasternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			neighbours.add(neighbor);
		neighbor = this.getSouthernNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			neighbours.add(neighbor);
		neighbor = this.getWesternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			neighbours.add(neighbor);

		return neighbours;
	}

	public boolean hasDiagonalNeighbor(Tile tile) {
		List<Tile> diagonalNeighbors = new ArrayList<>();

		Tile neighbor = this.getNorthEasternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			diagonalNeighbors.add(neighbor);
		neighbor = this.getSouthEasternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			diagonalNeighbors.add(neighbor);
		neighbor = this.getSouthWesternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			diagonalNeighbors.add(neighbor);
		neighbor = this.getNorthWesternNeighbor(tile);
		if (neighbor != null && neighbor.getType().equals(TileType.HASH))
			diagonalNeighbors.add(neighbor);

		return diagonalNeighbors.size() > 0;
	}

	public Tile getNorthEasternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() + 1, tile.getY() - 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getSouthEasternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() + 1, tile.getY() + 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getSouthWesternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() - 1, tile.getY() + 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getNorthWesternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() - 1, tile.getY() - 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getNorthernNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX(), tile.getY() - 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getEasternNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX() + 1, tile.getY());
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getSouthernNeighbor(Tile tile) {
		Tile neighbor = null;
		try {
			neighbor = this.getTile(tile.getX(), tile.getY() + 1);
		} catch (IndexOutOfBoundsException e) {
			// System.out.println(e.getMessage());
		}
		return neighbor;
	}

	public Tile getWesternNeighbor(Tile tile) {
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
					type = TileType.HASH;
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
				sb.append(this.getTile(x, y).getType().getTileChar());
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
					sb.append(this.getTile(x, y).getType().getTileChar());
				else
					sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static Grid fromList(List<Tile> regionTiles, int padding) {
		int xMin = regionTiles.stream().mapToInt(Tile::getX).min().orElse(0);
		int xMax = regionTiles.stream().mapToInt(Tile::getX).max().orElse(0);

		int yMin = regionTiles.stream().mapToInt(Tile::getY).min().orElse(0);
		int yMax = regionTiles.stream().mapToInt(Tile::getY).max().orElse(0);

		int xOffset = xMin - padding;
		int yOffset = yMin - padding;

		Grid g = new Grid(xMax - xMin + 1 + (2 * padding), yMax - yMin + 1 + (2 * padding));

		for (int y = 0; y < g.getHeight(); y++) {
			for (int x = 0; x < g.getWidth(); x++) {
				int currentX = x;
				int currentY = y;
				Tile t = regionTiles.stream()
						.filter(tile -> tile.getX() == currentX + xOffset && tile.getY() == currentY + yOffset)
						.findFirst().orElse(new Tile(x, y));
				Tile properTile = new Tile(currentX, currentY);
				properTile.setSymbol(t.getSymbol());
				properTile.setType(t.getType());
				properTile.setCounter(t.getCounter());
				g.addTile(properTile);
			}
		}

		return g;
	}

	public static Grid fromList(List<Tile> regionTiles) {
		return fromList(regionTiles, 0);
	}

	public static boolean isAligned(Tile a, Tile b) {
		return a.getX() == b.getX() || a.getY() == b.getY();
	}

	public List<Grid> getQuadrants() {
		List<Grid> gridList = new ArrayList<>();

		int firstWidth = this.width / 2;
		int firstHeight = this.height / 2;

		List<Tile> tileList = this.tileList.stream()
				.filter(tile -> tile.getX() < firstWidth && tile.getY() < firstHeight).toList();
		Grid grid = Grid.fromList(tileList);
		gridList.add(grid);

		tileList = this.tileList.stream().filter(tile -> tile.getX() > firstWidth && tile.getY() < firstHeight)
				.toList();
		grid = Grid.fromList(tileList);
		gridList.add(grid);

		tileList = this.tileList.stream().filter(tile -> tile.getX() < firstWidth && tile.getY() > firstHeight)
				.toList();
		grid = Grid.fromList(tileList);
		gridList.add(grid);

		tileList = this.tileList.stream().filter(tile -> tile.getX() > firstWidth && tile.getY() > firstHeight)
				.toList();
		grid = Grid.fromList(tileList);
		gridList.add(grid);

		return gridList;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
