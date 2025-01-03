
public class Border {
	private Tile plotTile;
	private Tile borderTile;
	private GridVector direction;

	public Tile getPlotTile() {
		return plotTile;
	}

	public void setPlotTile(Tile plotTile) {
		this.plotTile = plotTile;
	}

	public Tile getBorderTile() {
		return borderTile;
	}

	public void setBorderTile(Tile borderTile) {
		this.borderTile = borderTile;
	}
	
	public GridVector getDirection() {
		return direction;
	}

	public void setDirection(GridVector direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(plotTile);
		sb.append(" | ");
		sb.append(borderTile);
		
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Border border) {
			return this.borderTile.equals(border.getBorderTile()) && this.plotTile.equals(border.getPlotTile());
		}
		return false;
	}
}
