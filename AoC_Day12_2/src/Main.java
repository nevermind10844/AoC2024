import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
//		lines.forEach(System.out::println);

		Grid plot = Grid.parse(lines);
//		System.out.println(plot.highlightSymbol('L'));

		Map<Integer, List<Tile>> regionMap = new HashMap<>();
		List<Tile> assigned = new ArrayList<>(plot.getWidth() * plot.getHeight());

		int regionId = 0;

		for (int y = 0; y < plot.getHeight(); y++) {
			for (int x = 0; x < plot.getWidth(); x++) {
				Tile tile = plot.getTile(x, y);
				if (!assigned.contains(tile)) {
					List<Tile> tileList = new ArrayList<>();
					tileList.add(tile);
					regionMap.put(regionId, tileList);
					assigned.add(tile);
					explore(regionId, tile, assigned, plot, regionMap);
					regionId++;
				}
			}
		}

		for (Map.Entry<Integer, List<Tile>> set : regionMap.entrySet()) {
			List<Tile> regionTiles = set.getValue();
			Grid region = Grid.fromList(regionTiles, 1);
			List<Tile> potentialBorders = region.findTiles(TileType.EMPTY);
			for (Tile tile : potentialBorders) {
				List<Tile> neighbors = region.getNeighbors(tile);
				if (neighbors.stream().filter(neighbor -> neighbor.getType().equals(TileType.OCCUPIED)).count() <= 0) {
					tile.setType(TileType.DISCARDED);
					tile.setSymbol(TileType.DISCARDED.getTileChar());
				}
			}

			List<Tile> fields = region.findTiles(TileType.OCCUPIED);
			for (Tile tile : fields) {
				List<Tile> neighbors = region.getNeighbors(tile);
				if (neighbors.stream().filter(neighbor -> neighbor.getType().equals(TileType.EMPTY)).count() <= 0) {
					tile.setType(TileType.DISCARDED);
					tile.setSymbol(TileType.DISCARDED.getTileChar());
				}
			}

			List<Tile> borderTiles = region.findTiles(TileType.EMPTY);
			for (Tile tile : borderTiles) {
				tile.setType(TileType.BORDER);
				tile.setSymbol(TileType.BORDER.getTileChar());
			}
			
			List<Border> borderList = new ArrayList<Border>();

			List<Tile> plotTiles = region.findTiles(TileType.OCCUPIED);
			for (Tile tile : plotTiles) {
				List<Tile> neighborTiles = region.getNeighbors(tile);
				for (Tile t : neighborTiles) {
					if(t.getType().equals(TileType.BORDER)) {
						Border b = new Border();
						b.setPlotTile(tile);
						b.setBorderTile(t);
						borderList.add(b);
					}
				}
			}
			
			borderList.forEach(System.out::println);
			
			

			System.out.println(region);
		}

	}

	public static void explore(int regionId, Tile current, List<Tile> assigned, Grid plot,
			Map<Integer, List<Tile>> regionMap) {
		List<Tile> neighbors = plot.getNeighbors(current);
		for (Tile neighbor : neighbors) {
			if (!assigned.contains(neighbor)) {
				if (current.getSymbol() == neighbor.getSymbol()) {
					regionMap.get(regionId).add(neighbor);
					assigned.add(neighbor);
					explore(regionId, neighbor, assigned, plot, regionMap);
				}
			}
		}
	}

}
