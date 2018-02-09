
public class Island
{
	Point point;
	boolean travelAble = false;
	public Island(int x, int y, boolean travelAble)
	{
		
		this.travelAble = travelAble;
		this.point = new Point(x,y, travelAble);
	}
	
	public Point getIslandLocation() 
	{
		return point;
	}
	
	
	
}