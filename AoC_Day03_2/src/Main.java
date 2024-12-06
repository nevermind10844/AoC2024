import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		lines.forEach(line -> System.out.println(line));

		State state = State.START;
		
		int result = 0;
		
		boolean enabled = true;

		for (String string : lines) {
			int digitCount = 0;
			int startingAt = 0;
			int endingAt = 0;

			for (int i = 0; i < string.length(); i++) {
				char c = string.charAt(i);
				if (state == State.START) {
					if (c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
				} else if (state == State.M) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (c == 'u') {
						state = State.U;
					} else {
						state = State.START;
					}
				} else if (state == State.U) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (c == 'l') {
						state = State.L;
					} else {
						state = State.START;
					}
				} else if (state == State.L) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (c == '(') {
						state = State.O;
						digitCount = 0;
					} else {
						state = State.START;
					}
				} else if (state == State.O) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (Character.isDigit(c)) {
						state = State.X;
						digitCount = 1;
					} else {
						state = State.START;
					}
				} else if (state == State.X) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (Character.isDigit(c)) {
						if (digitCount < 3) {
							digitCount++;
						} else {
							state = State.START;
						}
					} else if (c == ',') {
						state = State.S;
					} else {
						state = State.START;
					}
				} else if (state == State.S) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
					
					if (Character.isDigit(c)) {
						state = State.Y;
						digitCount = 1;
					} else {
						state = State.START;
					}
				} else if (state == State.Y) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						System.out.println(fourLetterSubstring);
						System.out.println(sevenLetterSubstring);
					}
					
					if (Character.isDigit(c)) {
						if (digitCount < 3) {
							digitCount++;
						} else {
							state = State.START;
						}
					} else if (c == ')') {
						state = State.C;
						endingAt = i;
						String multiplication = string.substring(startingAt, endingAt + 1);
						multiplication = multiplication.replace("mul(", "");
						multiplication = multiplication.replace(")", "");
						String[] split = multiplication.split(",");
						
						if(enabled) {
							int mulResult = Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
							result += mulResult;
						}
						//System.out.println(String.format("start: %d end: %d", startingAt, endingAt));
					} else {
						state = State.START;
					}
				} else if (state == State.C) {
					if(c == 'm') {
						state = State.M;
						startingAt = i;
					} else if(c == 'd') {
						String fourLetterSubstring = string.substring(i, i+4);
						String sevenLetterSubstring = string.substring(i, i+7);
						if(fourLetterSubstring.equals("do()")) {
							enabled = true;
							state = State.START;
						} else if(sevenLetterSubstring.equals("don't()")) {
							enabled = false;
							state = State.START;
						}
					}
				}

			}
		}
		
		System.out.println(result);
	}

	private enum State {
		START, M,	// letter m
		U,			// letter u
		L,			// letter l
		O,			// character (
		C,			// character )
		X,			// digit 0-9
		Y,			// digit 0-9
		S,			// separator ,
		DO,			// instruction do()
		DONT		// instruction don't();
	}

}
