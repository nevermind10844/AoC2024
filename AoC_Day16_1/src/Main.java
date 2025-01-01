import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.MappingConfiguration;
import org.jksoft.utils.grid.TileType;
import org.jksoft.utils.inputreader.InputReader;

public class Main {

	public static void main(String[] args) {
//		List<String> lines = InputReader.readInput();
//		List<String> lines = InputReader.readTestInput();
		List<String> lines = InputReader.readTestInput(2);
//		lines.forEach(System.out::println);

//		lines.forEach(System.out::println);
		
		MappingConfiguration config = new MappingConfiguration();
		config.addMapping('S', TileType.STAR);
		
		Maze maze = new Maze(Grid.parse(lines, config));
		maze.findPath();
	}
	
}