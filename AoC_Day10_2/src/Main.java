import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();

		Grid grid = Grid.parse(lines);
		List<Tile> startList = grid.findTiles(TileType.START);
		List<Tile> endList = grid.findTiles(TileType.END);

		System.out.println(grid);

		System.out.println(startList.size());
		System.out.println(endList.size());

		int result = 0;

		for (int i = 0; i < startList.size(); i++) {
			int trailCount = 0;
			for (int j = 0; j < endList.size(); j++) {
				Tile start = startList.get(i);
				Tile end = endList.get(j);
				GridVector v = GridVector.fromTiles(start, end);
				if (v.magnitude() <= 9) {
					List<List<Tile>> paths = findAllPaths(grid, start, end);
					if (paths != null)
						trailCount += paths.size();
				}
			}
			System.err.println(trailCount);
			result += trailCount;
		}

		System.out.println(result);
	}

	public static List<List<Tile>> findAllPaths(Grid grid, Tile start, Tile end) {
		List<List<Tile>> allPaths = new ArrayList<>();
		List<Tile> path = new ArrayList<>();
		path.add(start);
		findAllPathsRecursive(grid, start, end, allPaths, path);
		return allPaths;
	}

	private static void findAllPathsRecursive(Grid grid, Tile current, Tile end, List<List<Tile>> allPaths,
			List<Tile> path) {
		if (current.equals(end)) {
			allPaths.add(new ArrayList<>(path));
			return;
		}

		List<Tile> neighbors = grid.getNeighbors(current);
		for (Tile neighbor : neighbors) {
			if (Character.getNumericValue(neighbor.getSymbol()) - Character.getNumericValue(current.getSymbol()) == 1) {
				if (!path.contains(neighbor)) {
					path.add(neighbor);
					findAllPathsRecursive(grid, neighbor, end, allPaths, path);
					path.remove(path.size() - 1);
				}
			}
		}
	}

}
