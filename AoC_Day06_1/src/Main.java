import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		
		int width = lines.get(0).length();
		int height = lines.size();
		
		Map map = new Map(width, height);
		
		for(int y = 0; y<lines.size(); y++) {
			String line = lines.get(y);
			for(int x=0; x<width; x++) {
				char typeChar = line.charAt(x);
				Tile t = new Tile(x, y);
				t.setType(TileType.tryParse(typeChar));
				map.addTile(t);
			}
		}
		
		System.out.println(map.toString());
		
		Guard guard = new Guard(map, new GridVector(0, -1));
		
		while(!guard.isOffMap()) {
			if(!guard.walk()) {
				guard.turn();
			}
//			System.out.println(map);
		}
		
		System.out.println(map.getControlledTiles().size());
	}

}
