import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readInput();
		//lines.forEach(System.out::println);
		
		String line = lines.get(0);
		
		Disc disc = new Disc();
		
		int fileCounter = 0;
		
		for(int i = 0; i< line.length(); i++) {
			int number = Character.getNumericValue(line.charAt(i));
			if(i % 2 != 0) {
				for(int j = 0; j<number; j++) {
					disc.addBlock(new Block(BlockType.SPACE, -1, number));
				}
			} else {
				for(int j = 0; j<number; j++) {
					disc.addBlock(new Block(BlockType.FILE, fileCounter, number));
				}
				fileCounter++;
			}
		}
		System.out.println(disc);
		disc.compact(false);
		System.out.println(disc);
		long result = disc.checksum();
		System.out.println(result);
		
	}
}
