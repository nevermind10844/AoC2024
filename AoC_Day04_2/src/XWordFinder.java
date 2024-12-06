
public class XWordFinder implements WordFinder{
	private CartesianWordDeliverer wordDeliverer;
	private String word;
	private String reversedWord;
	
	public XWordFinder(CartesianWordDeliverer wordDeliverer, String word) {
		this.wordDeliverer = wordDeliverer;
		this.word = word;
		this.reversedWord = new StringBuilder(this.word).reverse().toString();
	}

	@Override
	public int findWord(int x, int y) {
		int count = 0;
		
		String firstWord = this.wordDeliverer.getSouthEast(x-1, y-1, this.word.length());
		String secondWord = this.wordDeliverer.getNorthEast(x-1, y+1, this.word.length());
		
		if((firstWord.equals(this.word) || firstWord.equals(this.reversedWord)) &&
				(secondWord.equals(this.word) || secondWord.equals(this.reversedWord))){
			count++;
		}
		
		return count;
	}
}
