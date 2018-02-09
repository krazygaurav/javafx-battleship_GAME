
public class Point 
{
	boolean travelAble = true;
	boolean isThisAnIsland = false;
    public Point(int x, int y, boolean travelAble)
    {
		super();
		this.x = x;
		this.y = y;
		this.travelAble = travelAble;
	}
	private  int x;
    private  int y;
    
    public boolean isMayVisit() 
    {
		return travelAble;
	}
	public void setMayVisit(boolean mayVisit)
	{
		this.travelAble = mayVisit;
	}
	public int getX() 
    {
        return x;
    }
    public int getY() 
    {
        return y;
    }
    public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
    public String toString()
    {
        return ("(" + y + "," + x + ")"); 
    }
    
    public boolean isSamePoint(Point point ){
    	return this.x ==point.x && this.y== point.y; 
    }

}