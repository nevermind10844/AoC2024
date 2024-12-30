import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;
import org.jksoft.utils.grid.Tile;
import org.jksoft.utils.grid.TileType;

public class Warehouse {
	private Grid map;
	private Robot robot;
	private Map<Integer, Movable> movableMap;

	public Warehouse(Grid map) {
		this.map = map;
		Tile rTile = this.map.findTile(TileType.PLUS);
		this.robot = new Robot(new GridVectorInt(rTile.getX(), rTile.getY()));

		this.movableMap = new HashMap<>();

		List<Tile> boxTiles = this.map.findTiles(TileType.LETTER_A);

		int id = 0;

		for (Tile aTile : boxTiles) {
			Tile bTile = this.map.getTile(aTile.getX() + 1, aTile.getY());
			aTile.setExternalId(id);
			bTile.setExternalId(id);
			this.movableMap.put(id, new Box(new GridVectorInt(aTile.getX(), aTile.getY())));
			id++;
		}
	}

	public void moveRobot(GridVectorInt movement) {
		List<Movable> initialList = List.of(this.robot);
		try {
			boolean canMove = this.findPath(initialList, movement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean findPath(List<Movable> current, GridVectorInt movement) throws Exception {
		if (current.size() <= 0)
			return true;

		List<Movable> next = new ArrayList<>();
		for (Movable movable : current) {
			List<GridVectorInt> followUpPositions = movable.inTheWay(movement);
			for (GridVectorInt position : followUpPositions) {
				Tile t = this.map.getTile(position);
				if (t.getType().equals(TileType.HASH))
					return false;
				if (t.getType().equals(TileType.LETTER_A) || t.getType().equals(TileType.LETTER_B))
					next.add(this.movableMap.get(t.getExternalId()));
			}
		}

		boolean valid = findPath(next, movement);
		
		if (valid) {
			for (Movable movable : current) {
				movable.move(map, movement);
			}
		}

		return valid;
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
