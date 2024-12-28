import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;
import org.jksoft.utils.grid.Tile;
import org.jksoft.utils.grid.TileType;

public class Warehouse {
	private Grid map;
	
	public Warehouse(Grid map) {
		this.map = map;
	}
	
	public void moveRobot(GridVectorInt movement) {
		Tile fish = this.map.findTile(TileType.PLUS);
		this.move(fish, movement);
	}
	
	private boolean move(Tile current, GridVectorInt movement) {
		Tile target = this.map.getTile(current.getX() + movement.getX(), current.getY() + movement.getY());
		if(isFree(target)) {
			Grid.swap(current, target);
			return true;
		} else if(isMovable(target)) {
			boolean hasMoved = move(target, movement);
			if(hasMoved)
				Grid.swap(current, target);
			return hasMoved;
		} else {
			return false;
		}
			
	}
	
	private boolean isFree(Tile tile) {
		return tile.getType().equals(TileType.DOT);
	}
	
	private boolean isMovable(Tile tile) {
		return !tile.getType().equals(TileType.HASH);
	}
	
	public int getResult() {
		int result = 0;
		List<Tile> boxes = this.map.findTiles(TileType.STAR);
		for (Tile box : boxes) {
			result += box.getY() * 100 + box.getX();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}
}
