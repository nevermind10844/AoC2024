import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;

public class Box implements Movable {
	private GridVectorInt position;

	public Box(GridVectorInt position) {
		this.position = position;
	}

	@Override
	public GridVectorInt getPosition() {
		return this.position;
	}

	@Override
	public List<GridVectorInt> inTheWay(GridVectorInt movement) {
		GridVectorInt newPosition = GridVectorInt.add(this.position, movement);
		GridVectorInt bPosition = GridVectorInt.add(this.position, GridVectorInt.right());
		GridVectorInt bNewPosition = GridVectorInt.add(bPosition, movement);

		List<GridVectorInt> relevantPositions = new ArrayList<>();

		if (newPosition.equals(bPosition)) {
			relevantPositions.add(bNewPosition);
		} else if (bNewPosition.equals(this.position)) {
			relevantPositions.add(newPosition);
		} else {
			relevantPositions.add(newPosition);
			relevantPositions.add(bNewPosition);
		}

		return relevantPositions;
	}

	@Override
	public void move(Grid grid, GridVectorInt movement) throws IllegalMoveException {
		GridVectorInt newPosition = GridVectorInt.add(this.position, movement);
		GridVectorInt bPosition = GridVectorInt.add(this.position, GridVectorInt.right());
		GridVectorInt bNewPosition = GridVectorInt.add(bPosition, movement);

		if (newPosition.equals(bPosition)) {
			if (grid.isEmpty(bNewPosition)) {
				Grid.swap(grid, bPosition, bNewPosition);
				Grid.swap(grid, this.position, bPosition);
				this.position = newPosition;
			} else {
				throw new IllegalMoveException("you cant move to %s".formatted(bNewPosition));
			}
		} else if (bNewPosition.equals(this.position)) {
			if (grid.isEmpty(newPosition)) {
				Grid.swap(grid, this.position, newPosition);
				Grid.swap(grid, bPosition, this.position);
				this.position = newPosition;
			} else {
				throw new IllegalMoveException("you cant move to %s".formatted(bNewPosition));
			}
		} else {
			if (grid.isEmpty(newPosition) && grid.isEmpty(bNewPosition)) {
				Grid.swap(grid, this.position, newPosition);
				Grid.swap(grid, bPosition, bNewPosition);
				this.position = newPosition;
			} else {
				throw new IllegalMoveException("you cant move to %s or %s".formatted(newPosition, bNewPosition));
			}
		}
	}

}
