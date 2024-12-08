import java.util.Arrays;

public enum TileType {
	UNCONTROLLED('.'),
	CONTROLLED('X'),
	OBSTUCTION('#'),
	OCCUPIED('^');
	
	private char tileChar;
	
	private TileType(char tileChar) {
		this.tileChar = tileChar;
	}
	
	public static TileType tryParse(char c) {
		return Arrays.stream(TileType.values())
				.filter(type -> type.getTileChar() == c)
				.findFirst()
				.orElseThrow();
	}
	
	public char getTileChar() {
		return this.tileChar;
	}
}
