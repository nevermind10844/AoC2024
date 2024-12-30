import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;

public class Robot implements Movable {
	GridVectorInt position;

	public Robot(GridVectorInt position) {
		this.position = position;
	}
	
	@Override
	public GridVectorInt getPosition() {
		return this.position;
	}

	@Override
	public List<GridVectorInt> inTheWay(GridVectorInt movement) {
		return List.of(GridVectorInt.add(this.position, movement));
	}

	@Override
	public void move(Grid grid, GridVectorInt movement) {
		GridVectorInt newPosition = GridVectorInt.add(this.position, movement);
		if(grid.isEmpty(newPosition)) {
			Grid.swap(grid.getTile(this.position), grid.getTile(newPosition));
			this.position = newPosition;
		}
	}

}