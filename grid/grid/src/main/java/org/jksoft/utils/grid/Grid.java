package org.jksoft.utils.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Grid {
	private List<Tile> tileList;
	private int width;
	private int height;
	
	private MappingConfiguration configuration;

	public Grid(int width, int height) {
		this.tileList = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.configuration = null;
	}
	
	public Grid(int width, int height, MappingConfiguration configuration) {
		this.tileList = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.configuration = configuration;
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

	public Tile getTile(int index) throws IndexOutOfBoundsException {
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
	
	public Tile getTile(GridVectorInt position) throws IndexOutOfBoundsException {
		return this.getTile(position.getX(), position.getY());
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

		Tile neighbor = this.getNorthernNeighbor(tile);
		if (neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.getEasternNeighbor(tile);
		if (neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.getSouthernNeighbor(tile);
		if (neighbor != null)
			neighbours.add(neighbor);
		neighbor = this.getWesternNeighbor(tile);
		if (neighbor != null)
			neighbours.add(neighbor);

		return neighbours;
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

	public static Grid parse(List<String> lines, MappingConfiguration configuration) {
		int width = lines.get(0).length();
		int height = lines.size();
		Grid map = new Grid(width, height, configuration);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile t = new Tile(x, y);
				t.setSymbol(lines.get(y).charAt(x));
				TileType type = null;
				try {
					type = configuration.getMapping(t.getSymbol());
				} catch (NoSuchElementException unused) {
					
				}
				if (type == null) {
					try {
						type = TileType.tryParse(t.getSymbol());
					} catch (NoSuchElementException e) {
						type = TileType.HASH;
					}
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
					sb.append(TileType.EMPTY.getTileChar());
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
				g.addTile(properTile);
			}
		}

		return g;
	}
	
	public boolean isEmpty(GridVectorInt position) {
		return this.isEmpty(position.getX(), position.getY());
	}
	
	public boolean isEmpty(int x, int y) {
		return this.getTile(x, y).getType().equals(TileType.DOT);
	}

	public static Grid fromList(List<Tile> regionTiles) {
		return fromList(regionTiles, 0);
	}

	public static boolean isAligned(Tile a, Tile b) {
		return a.getX() == b.getX() || a.getY() == b.getY();
	}

	public static void swap(Tile a, Tile b) {
		Tile temp = new Tile(b.getX(), b.getY());
		temp.setSymbol(b.getSymbol());
		temp.setType(b.getType());
		temp.setExternalId(b.getExternalId());
		
		b.setSymbol(a.getSymbol());
		b.setType(a.getType());
		b.setExternalId(a.getExternalId());
		
		a.setSymbol(temp.getSymbol());
		a.setType(temp.getType());
		a.setExternalId(temp.getExternalId());
	}
	
	public static void swap(Grid grid, GridVectorInt a, GridVectorInt b) {
		swap(grid.getTile(a), grid.getTile(b));
	}
}
