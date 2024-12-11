import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		
		Grid grid = Grid.parse(lines);
		
		System.out.println(grid.toString());
		
		List<Tile> antennas = grid.findTiles(TileType.ANTENNA);
		Map<Character, List<Tile>> frequencyMap = new HashMap<>();
		for (Tile tile : antennas) {
			Character frequency = tile.getSymbol();
			List<Tile> antennaList = antennas.stream().filter(antenna -> antenna.getSymbol() == frequency).toList();
			if(!frequencyMap.containsKey(tile.getSymbol())) {
				frequencyMap.put(tile.getSymbol(), antennaList);
			}
		}
		
		Grid antinodeGrid = Grid.create(grid.getWidth(), grid.getHeight());
		
		frequencyMap.forEach((frequency, antennaList) -> {
			for(int i=0; i<antennaList.size()-1; i++) {
				for(int j=i+1; j<antennaList.size(); j++) {
					Tile a = antennaList.get(i);
					Tile b = antennaList.get(j);
					
//					System.out.println("frequency: %s -> antenna A: %d::%d || antennaB: %d::%d".formatted(frequency, a.getX(), a.getY(), b.getX(), b.getY()));
					
					GridVector vector = GridVector.fromTiles(a, b);
					
					
					Tile antinode = null;

					Tile antinodeB = antinodeGrid.getTile(b.getX(), b.getY());
					antinodeB.setType(TileType.ANTINODE);
					
					boolean inBounds = true;
					int x = b.getX() + vector.getX();
					int y = b.getY() + vector.getY();
					
					
					while(inBounds) {
						try{
							antinode = antinodeGrid.getTile(x, y);
							antinode.setType(TileType.ANTINODE);
							x = antinode.getX() + vector.getX();
							y = antinode.getY() + vector.getY();
						} catch (IndexOutOfBoundsException e) {
							System.err.println(e.getMessage());
							inBounds = false;
						}
					}
					
					vector.invert();
					
					Tile antinodeA = antinodeGrid.getTile(a.getX(), a.getY());
					antinodeA.setType(TileType.ANTINODE);
					
					x = a.getX() + vector.getX();
					y = a.getY() + vector.getY();
					
					inBounds = true;
					while(inBounds) {
						try{
							antinode = antinodeGrid.getTile(x, y);
							antinode.setType(TileType.ANTINODE);
							x = antinode.getX() + vector.getX();
							y = antinode.getY() + vector.getY();
						} catch (IndexOutOfBoundsException e) {
							System.err.println(e.getMessage());
							inBounds = false;
						}
					}
					
//					System.out.println(vector);
				}
			}
		});
		
		System.out.println(antinodeGrid);
		System.out.println(antinodeGrid.findTiles(TileType.ANTINODE).size());
	}
}
