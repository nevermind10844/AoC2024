
public class Block {
	private BlockType type;
	private int fileId;
	private int length;
	
	public Block(BlockType type, int fileId, int length) {
		this.type = type;
		this.fileId = fileId;
		this.length = length;
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
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return this.type.equals(BlockType.SPACE) ? "." : "%d".formatted(this.fileId);
	}
}
