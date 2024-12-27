
public class Robot implements Movable{
	private int id;
	
	private GridVectorInt position;
	private GridVectorInt vector;
	
	public Robot(int id, GridVectorInt position, GridVectorInt vector) {
		this.id = id;
		this.position = position;
		this.vector = vector;
	}

	public int getId() {
		return id;
	}

	public GridVectorInt getPosition() {
		return position;
	}

	public GridVectorInt getVector() {
		return vector;
	}
	
	@Override
	public String toString() {
		return """
				Robot [
					id: %d
					position: %s
					vector: %s
				]
				""".formatted(this.id, this.position, this.vector);
	}

	@Override
	public void move(int[] bounds) {
		if(bounds.length == 2) {
			this.position.add(this.vector);
			
			int x = this.position.getX();
			int y = this.position.getY();
			
			if(x > bounds[0]) {
				this.position.setX(x-1 - bounds[0]);
			} else if(x < 0 ) {
				this.position.setX(bounds[0] + x +1);
			}
			
			if(y > bounds[1]) {
				this.position.setY(y-1 - bounds[1]);
			} else if(y < 0) {
				this.position.setY(bounds[1] + y +1);
			}
		}
	}
	
}
