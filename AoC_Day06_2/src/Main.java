import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();

		int width = lines.get(0).length();
		int height = lines.size();
		
		int stuckThreshhold = width * height;
		System.out.println(stuckThreshhold);

		Map map = new Map(width, height);

		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < width; x++) {
				char typeChar = line.charAt(x);
				Tile t = new Tile(x, y);
				t.setType(TileType.tryParse(typeChar));
				map.addTile(t);
			}
		}
		
		map.save();

		System.out.println(map.toString());

		int stuckCounter = 0;
		int freeCounter = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map.restore();
				Tile t = map.getTile(x, y);
				if (t.getType().equals(TileType.UNCONTROLLED)) {
					Guard guard = new Guard(map, new GridVector(0, -1), stuckThreshhold);

					t.setType(TileType.OBSTUCTION);
					
					while (!guard.isOffMap()) {
						if (!guard.walk()) {
							guard.turn();
						}
						if(guard.isStuck()) {
							stuckCounter++;
							System.out.println("stuck: " + stuckCounter);
							break;
						}
						if(guard.isOffMap()) {
							freeCounter++;
							System.out.println("free: " + freeCounter);
							break;
						}
					}
				}
			}
		}

		System.out.println(stuckCounter);
	}

}
