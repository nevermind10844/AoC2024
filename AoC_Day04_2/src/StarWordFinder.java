
public class StarWordFinder implements WordFinder{
	private CartesianWordDeliverer wordDeliverer;
	private String word;
	
	public StarWordFinder(CartesianWordDeliverer wordDeliverer, String word) {
		this.wordDeliverer = wordDeliverer;
		this.word = word;
	}
	
	@Override
	public int findWord(int x, int y) {
		int count = 0;
		
		String word = this.wordDeliverer.getNorth(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getNorthEast(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getEast(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getSouthEast(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getSouth(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getSouthWest(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getWest(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		word = this.wordDeliverer.getNorthWest(x, y, this.word.length());
		if(word.equals(this.word))
			count++;
		
		return count;
	}
}
