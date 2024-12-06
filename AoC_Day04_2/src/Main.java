import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		lines.forEach(line -> System.out.println(line));

		int width = lines.get(0).length();
		int height = lines.size();
		
		Board board = new Board(width, height);
		
		for(int y=0; y<height; y++) {
			String line = lines.get(y);
			for(int x = 0; x< width; x++) {
				char c = line.charAt(x);
				Tile tile = new Tile(x, y, c);
				board.addTile(tile);
			}
		}
		
		System.out.println(board.getLength());
		
		WordFinder finder = new XWordFinder(board, "MAS");
		
		int result = 0;
		
		for(int i=0; i<board.getLength(); i++) {
			Tile tile = board.getTile(i);
			char letter = tile.getLetter();
			if(letter == 'A') {
				int found = finder.findWord(tile.getX(), tile.getY());
				System.out.println("found %d words at %d::%d".formatted(found, tile.getX(), tile.getY()));
				result += found;
			}
		}
		
		System.out.println(result);
	}

}
