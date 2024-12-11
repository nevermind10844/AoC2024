import java.util.ArrayList;
import java.util.List;

public class Disc {
	List<Block> blockList;

	public Disc() {
		this.blockList = new ArrayList<>();
	}
	
	public void addBlock(Block block) {
		this.blockList.add(block);
	}
	
	public void compact(boolean debug) {
		for(int i = this.blockList.size()-1; i>=0; i--) {
			Block lastFileBlock = this.blockList.get(i);
			if(lastFileBlock.getType().equals(BlockType.FILE)) {
				for(int j=0; j< i; j++) {
					Block firstSpaceBlock = this.blockList.get(j);
					if(firstSpaceBlock.getType().equals(BlockType.SPACE)) {
						this.swap(lastFileBlock, firstSpaceBlock);
						if(debug)
							System.out.println(this);
						break;
					}
				}
			}
		}
	}
	
	private void swap(Block a, Block b) {
		Block temp = new Block(a.getType(), a.getFileId());
		a.setFileId(b.getFileId());
		a.setType(b.getType());
		b.setFileId(temp.getFileId());
		b.setType(temp.getType());
	}
	
	public long checksum() {
		long result = 0;
		for(long i=0; i<this.blockList.size(); i++) {
			Block b = this.blockList.get((int)i);
			if(b.getType().equals(BlockType.SPACE))
				break;
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
