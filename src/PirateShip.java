import java.util.List;

public class PirateShip implements Observer {
	Point point;
	List<Observer> pirateShips = null;
	boolean mayVisit = false;
	final static String NORTH = "NORTH";
	final static String SOUTH = "SOUTH";
	final static String EAST = "EAST";
	final static String WEST = "WEST";

	public PirateShip(int x, int y) {
		this.point = new Point(x, y, false);
	}

	public Point getShipLocation() {
		return point;
	}

	public void setShipLocation(Point point) {
		this.point = point;
	}

	private boolean ifAvailable(int x, int y, List<Point> islandsMap) {
		for(Observer observer : pirateShips){
			PirateShip pirateShip = (PirateShip)observer;
			Point point = pirateShip.getShipLocation();
			if (x == point.getX() && y == point.getY()){
				return false;
			}
		}
		for (Point point : islandsMap) {
			if (x == point.getX() && y == point.getY())
				return false;
		}
		return true;
	}

	public boolean isMoveAllowed(Point point, List<Point> islandsMap) {
		if (point.getX() >= 0 && point.getX() <= 9 && point.getY() >= 0 && point.getY() <= 9) {
			if (ifAvailable(point.getX(), point.getY(), islandsMap)){
				return true;
			}
		}
		return false;
	}

	public Point checkEast(Point ship, Point point, List<Point> islandsMap) {
		Point p = new Point(point.getX() + 1, point.getY(), true);
		if (!p.isSamePoint(ship) && !point.isSamePoint(ship) && isMoveAllowed(p, islandsMap)) {
			return p;
		}
		return null;
	}

	public Point checkWest(Point ship, Point point, List<Point> islandsMap) {
		Point p = new Point(point.getX() - 1, point.getY(), true);
		if (!p.isSamePoint(ship) && !point.isSamePoint(ship) && isMoveAllowed(p, islandsMap)) {
			return p;
		}
		return null;
	}

	public Point checkNorth(Point ship, Point point, List<Point> islandsMap) {
		Point p = new Point(point.getX(), point.getY() + 1, true);
		if (!p.isSamePoint(ship) && !point.isSamePoint(ship) && isMoveAllowed(p, islandsMap)) {
			return p;
		}
		return null;
	}

	public Point checkSouth(Point ship, Point point, List<Point> islandsMap) {
		Point p = new Point(point.getX(), point.getY() - 1, true);
		if (!p.isSamePoint(ship) && !point.isSamePoint(ship) && isMoveAllowed(p, islandsMap)) {
			return p;
		}
		return null;
	}

	public Point optimalMove(Point ship, Point pirateShip, List<Point> islandsMap) {
		Point east = checkEast(ship, pirateShip, islandsMap);
		Point west = checkWest(ship,  pirateShip, islandsMap);
		Point north = checkNorth(ship, pirateShip, islandsMap);
		Point south = checkSouth(ship, pirateShip, islandsMap);

		if (ship.getY() == pirateShip.getY()) {
			if (ship.getX() - pirateShip.getX() > 0) {
				// EAST Priority wise (EAST, NORTH, SOUTH, WEST)
				if (east != null)
					return east;
				else if (north != null)
					return north;
				else if (south != null)
					return south;
				else if (west != null)
					return west;
				System.out.println("Ship is bounded");
			} else {
				// WEST Priority wise (WEST, NORTH, SOUTH, EAST)
				if (west != null)
					return west;
				else if (north != null)
					return north;
				else if (south != null)
					return south;
				else if (east != null)
					return east;
				System.out.println("Ship is bounded");
			}
		} else {
			if (ship.getY() - pirateShip.getY() > 0) {
				if (ship.getX() - pirateShip.getX() > 0) {
					// NORTH Priority wise (NORTH, EAST, WEST, SOUTH)
					if (north != null)
						return north;
					else if (east != null)
						return east;
					else if (west != null)
						return west;
					else if (south != null)
						return south;
					System.out.println("Ship is bounded");
				} else {
					// NORTH Priority wise (NORTH, WEST, EAST, SOUTH)
					if (north != null)
						return north;
					else if (west != null)
						return west;
					else if (east != null)
						return east;
					else if (south != null)
						return south;
					System.out.println("Ship is bounded");
				}
			} else {
				if (ship.getX() - pirateShip.getX() > 0) {
					// SOUTH Priority wise (SOUTH, EAST, WEST, NORTH)
					if (south != null)
						return south;
					else if (east != null)
						return east;
					else if (west != null)
						return west;
					else if (north != null)
						return north;
					System.out.println("Ship is bounded");
				} else {
					// SOUTH Priority wise (SOUTH, WEST, EAST, NORTH)
					if (south != null)
						return south;
					else if (west != null)
						return west;
					else if (east != null)
						return east;
					else if (north != null)
						return north;
					System.out.println("Ship is bounded");
				}
			}
		}
		return null;
	}

	@Override
	public boolean update(Ship ship, List<Point> islandsMap) {
		if (ship instanceof Ship) {
			pirateShips = ship.observers;
			Point shipLocation = ((Ship) ship).getShipLocation();
			Point pirateLocation = this.getShipLocation();
			Point optimal = this.optimalMove(shipLocation, pirateLocation, islandsMap);
			if(optimal != null){
				this.setShipLocation(optimal);
			}else{
				return false;
			}
		}else
			return false;
		return true;
	}

}