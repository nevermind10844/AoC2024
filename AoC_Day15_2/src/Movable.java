import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;

public interface Movable {
	public GridVectorInt getPosition();
	public List<GridVectorInt> inTheWay(GridVectorInt movement);
	public void move(Grid grid, GridVectorInt movement) throws IllegalMoveException;
}
