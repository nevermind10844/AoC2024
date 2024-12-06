
public interface CartesianWordDeliverer {

	public String getNorth(int x, int y, int length);
	public String getNorthEast(int x, int y, int length);
	public String getEast(int x, int y, int length);
	public String getSouthEast(int x, int y, int length);
	public String getSouth(int x, int y, int length);
	public String getSouthWest(int x, int y, int length);
	public String getWest(int x, int y, int length);
	public String getNorthWest(int x, int y, int length);
	
}
