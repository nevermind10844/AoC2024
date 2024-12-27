import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TileType {
	EMPTY(' '),
	DOT('.'),
	STAR('*'),
	HASH('#'),
	PLUS('+'),
	LETTER_A('A'),
	LETTER_B('B'),
	LETTER_C('C'),
	LETTER_D('D'),
	LETTER_E('E'),
	LETTER_F('F');
	
	private char tileChar;
	
	private TileType(char tileChar) {
		this.tileChar = tileChar;
	}
	
	public static TileType tryParse(char c) throws NoSuchElementException{
		return Arrays.stream(TileType.values())
				.filter(type -> type.getTileChar() == c)
				.findFirst()
				.orElseThrow();
	}
	
	public char getTileChar() {
		return this.tileChar;
	}
}
