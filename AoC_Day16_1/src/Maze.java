import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.grid.Grid;
import org.jksoft.utils.grid.GridVectorInt;
import org.jksoft.utils.grid.Tile;
import org.jksoft.utils.grid.TileType;

public class Maze {
	
	private static int TURNING_COST = 1000;
	private static int MOVING_COST = 1;
	
	private Grid grid;

	private List<Node> openList;
	private List<Node> closedList;

	private Node start;
	private Node end;
	
	private GridVectorInt heading;
	
	public Maze(Grid grid) {
		this.grid = grid;
		
		start = new Node(this.grid.findTile(TileType.STAR));
		start.setG(0);
		
		end = new Node(this.grid.findTile(TileType.LETTER_E));
		end.setH(0);
		
		Maze.setHeuristic(start, end);

		this.heading = GridVectorInt.right();

		this.openList = new ArrayList<>();
		this.closedList = new ArrayList<>();
	}

	public void findPath() {
		this.openList.add(this.start);

		do {
			Node current = this.openList.get(this.openList.size() - 1);
			this.openList.remove(current);
			
			if(current.equals(this.end)) {
				System.out.println(this.grid.highlightList(this.getChainedTiles(current)));
				System.out.println(current);
				return;
			}
			
			this.closedList.add(current);
			
			this.expandNode(current);
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (openList.size() > 0);
	}
	
	private void expandNode(Node current) {
		List<Tile> neighbours = this.grid.getNeighbors(current.getTile());
		List<Tile> successorTiles = neighbours.stream().filter(tile -> tile.getType().equals(TileType.DOT) || tile.getType().equals(TileType.LETTER_E)).toList();
		
		for (Tile tile : successorTiles) {
			
			Node n = new Node(tile);
//			Maze.setHeuristic(n, this.end);
			
			if(closedList.contains(n))
				continue;
			
			int tentativeG = current.getG() + MOVING_COST;
			
			GridVectorInt neededMovement = GridVectorInt.fromTiles(current.getTile(), n.getTile());
			GridVectorInt lastMovement = null;

			Node prevNode = current.getPredecessor();
			if(prevNode != null) {
				Tile prevTile = prevNode.getTile();
				lastMovement = GridVectorInt.fromTiles(prevTile, current.getTile());
			} else {
				lastMovement = this.heading;
			}

			if(!neededMovement.equals(lastMovement))
				tentativeG += TURNING_COST;
			
			if(openList.contains(n)) {
				n = openList.get(openList.indexOf(n));
				if(tentativeG >= n.getG())
					continue;
				else {
					n.setG(tentativeG);
					n.setPredecessor(current);
					this.openList.sort(null);
				}
			} else {
				n.setG(tentativeG);
				n.setPredecessor(current);
				Maze.setHeuristic(n, this.end);
				this.openList.add(n);
				this.openList.sort(null);
			}
		}
	}
	
	private static void setHeuristic(Node current, Node end) {
		GridVectorInt hVector = GridVectorInt.fromTiles(current.getTile(), end.getTile());
		current.setH(hVector.magnitude());
	}
	
	private List<Tile> getChainedTiles(Node current) {
		List<Tile> tileList = new ArrayList<>();
		Node next = current;
		while(next != null) {
			tileList.add(next.getTile());
			next = next.getPredecessor();
		}
		return tileList;
	}

	@Override
	public String toString() {
		return this.grid.toString();
	}
}
