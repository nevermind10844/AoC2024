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
		System.out.println(plot.highlightSymbol('L'));

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
			Tile start = regionTiles.get(0);
			Tile leftTile = plot.getWesternNeighbor(start);
			if (leftTile != null) {
				while (leftTile != null && regionTiles.contains(leftTile))) {
				}
			}

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
