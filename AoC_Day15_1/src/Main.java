import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;
import org.jksoft.utils.grid.MappingConfiguration;
import org.jksoft.utils.grid.Tile;
import org.jksoft.utils.grid.TileType;
import org.jksoft.utils.inputreader.InputReader;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
//		List<String> lines = InputReader.readTestInput();
//		List<String> lines = InputReader.readTestInput(2);
//		lines.forEach(System.out::println);
		
		List<String> mapLines = lines.stream().filter(line -> line.startsWith("#")).toList();
		Grid map = parseMap(mapLines);
		
//		System.out.println(map);
		
		List<String> movementLines = lines.stream().filter(line -> !line.startsWith("#") && !line.isEmpty()).toList();
		List<GridVectorInt> movements = parseMovements(movementLines);
		
//		movements.forEach(System.out::println);
		
		Warehouse warehouse = new Warehouse(map);
		
		System.out.println(warehouse);
		
		for (GridVectorInt movement : movements) {
			warehouse.moveRobot(movement);
			System.out.println(warehouse);
		}
		
		
		System.out.println(warehouse.getResult());
	}
	
	private static Grid parseMap(List<String> lines) {
		MappingConfiguration mapping = new MappingConfiguration();
		mapping.addMapping('@', TileType.PLUS);
		mapping.addMapping('O', TileType.STAR);
		Grid g = Grid.parse(lines, mapping);
		
		return g;
	}
	
	private static List<GridVectorInt> parseMovements(List<String> lines) {
		List<GridVectorInt> movements = new ArrayList<>();
		for (String line : lines) {
			for(int i=0; i<line.length(); i++) {
				char c = line.charAt(i);
				switch(c) {
					case '^':
						movements.add(GridVectorInt.up());
						break;
					case '>':
						movements.add(GridVectorInt.right());
						break;
					case 'v':
						movements.add(GridVectorInt.down());
						break;
					case '<':
						movements.add(GridVectorInt.left());
						break;
					default:
						break;
				}
			}
		}
		return movements;
	}
}