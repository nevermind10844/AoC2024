import java.util.ArrayList;
import java.util.List;

public class Disc {
	List<Block> blockList;
	private int highestFileId;

	public Disc() {
		this.blockList = new ArrayList<>();
	}
	
	public void addBlock(Block block) {
		this.blockList.add(block);
		if(block.getFileId() > highestFileId)
			this.highestFileId = block.getFileId();
	}
	
	public void compact(boolean debug) {
		for(int i = this.highestFileId; i>=0; i--) {
			final int currentId = i;
			Block someBlock = this.blockList.stream().filter(b -> b.getFileId() == currentId).findFirst().get();
			
			int pos = -1;
			
			if(someBlock.getLength() == 1)
				pos = this.blockList.indexOf(someBlock);
			else
				pos = this.blockList.indexOf(someBlock) + someBlock.getLength()-1;
				
			Block block = this.blockList.get(pos);
			if(block.getType().equals(BlockType.FILE)) {
				int length = block.getLength();
				int swapStart = findSpace(length, pos);{
					if(swapStart >= 0) {
						int k = swapStart;
						for(int j = pos-length+1; j<=pos; j++) {
							Block from = this.blockList.get(j);
							Block space = this.blockList.get(k);
							k++;
							this.swap(space, from);
							if(debug)
								System.out.println(this);
						}
						
						Block next = this.blockList.get(k);
						if(next.getType().equals(BlockType.SPACE)) {
							int remainingSpace = next.getLength() - length;
							for(int j = k; j<k+remainingSpace; j++) {
								this.blockList.get(j).setLength(remainingSpace);
							}
						}
					}
				}
			}
		}
	}
	
	private int findSpace(int length, int swapStop) {
		for(int i=0; i<swapStop; i++) {
			Block b = this.blockList.get(i);
			if(b.getType().equals(BlockType.SPACE)) {
				if(b.getLength() >= length) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void swap(Block a, Block b) {
		Block temp = new Block(a.getType(), a.getFileId(), a.getLength());
		a.setFileId(b.getFileId());
		a.setType(b.getType());
		a.setLength(b.getLength());
		b.setFileId(temp.getFileId());
		b.setType(temp.getType());
		b.setLength(temp.getLength());
	}
	
	public long checksum() {
		long result = 0;
		for(long i=0; i<this.blockList.size(); i++) {
			Block b = this.blockList.get((int)i);
			if(b.getType().equals(BlockType.SPACE))
				continue;
			result += b.getFileId() * i;
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int blockId = -1;
		
		for (Block block : blockList) {
//			if(blockId != block.getFileId())
//				sb.append("|");
			blockId = block.getFileId();
			sb.append(block);
		}
		
//		sb.append("|");
		
		return sb.toString();
	}
}
