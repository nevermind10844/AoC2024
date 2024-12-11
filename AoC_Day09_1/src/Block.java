
public class Block {
	private BlockType type;
	private int fileId;
	
	public Block(BlockType type, int fileId) {
		this.type = type;
		this.fileId = fileId;
	}
	
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
		this.type = type;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	@Override
	public String toString() {
		return this.type.equals(BlockType.SPACE) ? "." : "%d".formatted(this.fileId);
	}
}
