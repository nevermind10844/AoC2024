
public class Guard {
	private int x;
	private int y;

	private Map map;
	private GridVector gridVector;
	
	private boolean offMap;

	public Guard(Map map, GridVector gridVector) {
		this.map = map;
		this.gridVector = gridVector;
		this.offMap = false;
		Tile guardTile = this.map.findGuard();
		this.x = guardTile.getX();
		this.y = guardTile.getY();
	}

	public boolean walk() {
		boolean walked = false;
		
		Tile currentTile = this.map.getTile(this.x, this.y);
		
		int newX = this.x + this.gridVector.getX();
		int newY = this.y + this.gridVector.getY();

		if (newX < 0 || newX >= this.map.getWidth()
				|| newY<0 || newY >= this.map.getHeight()) {
			this.x = newX;
			this.y = newY;
			currentTile.setType(TileType.CONTROLLED);
			this.offMap = true;
			walked = true;
		} else {
			Tile nextTile = this.map.getTile(newX, newY);
			if(nextTile.getType().equals(TileType.OBSTUCTION)) {
				walked = false;
			} else {
				currentTile.setType(TileType.CONTROLLED);
				nextTile.setType(TileType.OCCUPIED);
				this.x = newX;
				this.y = newY;
				walked = true;
			}
		}
		return walked;
	}
	
	public void turn() {
		if(this.gridVector.getY() == -1) {
			this.gridVector.setX(1);
			this.gridVector.setY(0);
		} else if(this.gridVector.getX() == 1) {
			this.gridVector.setX(0);
			this.gridVector.setY(1);
		} else if(this.gridVector.getY() == 1) {
			this.gridVector.setX(-1);
			this.gridVector.setY(0);
		} else if(this.gridVector.getX() == -1) {
			this.gridVector.setX(0);
			this.gridVector.setY(-1);
		}
	}
	
	public boolean isOffMap() {
		return this.offMap;
	}

}
