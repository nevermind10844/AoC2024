import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {
	
	private static int ITERATIONS = 100;
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
		}
		System.out.println(grid);
		
		int[] bounds = new int[] {WIDTH-1, HEIGHT-1};
		
		for(int i=0; i<ITERATIONS; i++) {
			robotList.forEach(robot -> robot.move(bounds));
			grid = Grid.create(WIDTH, HEIGHT);
			for (Robot robot : robotList) {
				Tile t = grid.getTile(robot.getPosition().getX(), robot.getPosition().getY());
				t.increaseCounter();
				t.setType(TileType.OCCUPIED);
			}
			
			System.out.println(grid);
			
		}
		
		List<Grid> quadrants = grid.getQuadrants();
		
		System.out.println(quadrants.get(0));
		System.out.println(quadrants.get(1));
		System.out.println(quadrants.get(2));
		System.out.println(quadrants.get(3));

		int result = 0;
		
		for (Grid q : quadrants) {
			int qResult = q.findTiles(TileType.OCCUPIED).stream().mapToInt(Tile::getCounter).sum();
			if(result == 0)
				result = qResult;
			else
				result *= qResult;
		}
		
		System.err.println(result);
	}
}