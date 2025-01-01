import org.jksoft.utils.grid.Tile;

public class Node implements Comparable<Node>{

	private Tile tile;
	private int g;
	private	int h;
	private Node predecessor;
	
	public Node(Tile tile){
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public Node getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	@Override
	public String toString() {
		return "Node: %s + g: %d + h: %d".formatted(this.tile, this.g, this.h); 
	}

	@Override
	public int compareTo(Node o) {
		return (o.getG() + o.getH()) < (this.getG() + this.getH()) ? -1 : 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Node n) {
			return this.tile.equals(n.tile);
		}
		return false;
	}
}
