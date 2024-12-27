import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jksoft.utils.inputreader.InputReader;

public class Main {
	
	private static int ITERATIONS = 10431;
	private static int WIDTH = 101;
	private static int HEIGHT = 103;

	public static void main(String[] args) {

		List<String> lines = InputReader.readInput();
//		List<String> lines = InputReader.readTestInput();
//		List<String> lines = InputReader.readTestInput(2);
//		lines.forEach(System.out::println);
		
		List<Robot> robotList = new ArrayList<>();
		
		int id = 0;
		
		for (String string : lines) {
			String[] splitLine = string.split(" ");
			
			String[] position = splitLine[0].split("=")[1].split(",");
			GridVectorInt positionVector = new GridVectorInt(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			
			String[] vector = splitLine[1].split("=")[1].split(",");
			GridVectorInt movementVector = new GridVectorInt(Integer.parseInt(vector[0]), Integer.parseInt(vector[1]));

			Robot r = new Robot(id++, positionVector, movementVector);
			robotList.add(r);
		}
		
		
		Grid grid = Grid.create(WIDTH, HEIGHT);
		for (Robot robot : robotList) {
			Tile t = grid.getTile(robot.getPosition().getX(), robot.getPosition().getY());
			t.increaseCounter();
			t.setType(TileType.HASH);
		}
		System.out.println(grid);
		
		int[] bounds = new int[] {WIDTH-1, HEIGHT-1};
		
		Map<Integer, Integer> mapOfHashes = new HashMap<>();
		
		for(int i=0; i<ITERATIONS; i++) {
			robotList.forEach(robot -> robot.move(bounds));
			grid = Grid.create(WIDTH, HEIGHT);
			for (Robot robot : robotList) {
				Tile t = grid.getTile(robot.getPosition().getX(), robot.getPosition().getY());
				t.increaseCounter();
				t.setType(TileType.HASH);
				t.setSymbol('#');
			}
			
			List<Grid> quadrants = grid.getQuadrants();
			int a = quadrants.get(0).findTiles(TileType.HASH).size();
			int b = quadrants.get(1).findTiles(TileType.HASH).size();
			int c = quadrants.get(2).findTiles(TileType.HASH).size();
			int d = quadrants.get(3).findTiles(TileType.HASH).size();
			
			int ab = a + b;
			int cd = c + d;
			
			if(cd > ab*2) {
				System.err.println(i+1);
				System.out.println(grid.highlightSymbol('#'));
			}
		}
	}
}