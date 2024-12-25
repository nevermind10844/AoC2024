import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {


	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		
		long result = 0l;
		
		for (String string : lines) {
			result += Long.parseLong(string);
			System.out.println(result);
		}
		
		System.err.println(result);
	}
}