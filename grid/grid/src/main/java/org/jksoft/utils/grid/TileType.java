package org.jksoft.utils.grid;

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
	LETTER_F('F'),
	DIGIT_0('0'),
	DIGIT_1('1'),
	DIGIT_2('2'),
	DIGIT_3('3'),
	DIGIT_4('4'),
	DIGIT_5('5'),
	DIGIT_6('6'),
	DIGIT_7('7'),
	DIGIT_8('8'),
	DIGIT_9('9');
	
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
