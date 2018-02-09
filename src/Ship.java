
//2347284729999
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Ship implements Subject {
	Point point;
	boolean mayVisit = true;
	Island island;
	Point nextPoint;
	boolean visitAble;
	List<Observer> observers = new LinkedList<Observer>();

	public Ship(int x, int y) {
		this.point = new Point(x, y, mayVisit);
	}

	public Point getShipLocation() {
		return point;
	}

	public void goEast() {
		if (point.getX() + 1 >= 0 && point.getX() + 1 <= 9) {
			point.setX(point.getX() + 1);
		}
	}

	public boolean checkEast() {
		if (point.getX() + 1 >= 0 && point.getX() + 1 <= 9) {
			visitAble = point.isMayVisit();
			if (visitAble == true) {
				System.out.println("East is visitable");
				return visitAble;
			} else if (visitAble = false) {
				System.out.println("East is not visitable");
				visitAble = false;
				return visitAble;
			}
		}
		visitAble = false;
		return visitAble;

	}

	public Point getEastPoint() {
		Point k = new Point(point.getX(), point.getY(), mayVisit);
		if (point.getX() + 1 >= 0 && point.getX() + 1 <= 9) {
			k.setX(k.getX() + 1);
			return k;
		}else
			return null;
	}

	public void goWest() {
		if (point.getX() - 1 >= 0 && point.getX() - 1 <= 9) {
			point.setX(point.getX() - 1);
		}
	}

	public boolean checkWest() {
		if (point.getX() - 1 >= 0 && point.getX() - 1 <= 9) {
			visitAble = point.isMayVisit();
			if (visitAble == true) {
				System.out.println("East is visitable");
				return visitAble;
			} else if (visitAble = false) {
				System.out.println("East is not visitable");
				visitAble = false;
				return visitAble;
			}
		}
		visitAble = false;
		return visitAble;

	}

	public Point getWestPoint() {
		Point k = new Point(point.getX(), point.getY(), mayVisit);
		if (point.getX() - 1 >= 0 && point.getX() - 1 <= 9) {
			k.setX(k.getX() - 1);
			return k;
		}else
			return null;
	}

	public void goNorth() {
		if (point.getY() - 1 >= 0 && point.getY() - 1 <= 9) {
			point.setY(point.getY() - 1);
		}

		for (Island e : OceanExplorer.islands) {
			if (e.getIslandLocation() == point)
				System.out.println("Equals ");
		}
	}

	public boolean checkNorth() {
		if (point.getY() - 1 >= 0 && point.getY() - 1 <= 9) {
			visitAble = point.isMayVisit();
			if (visitAble == true) {
				System.out.println("North is visitable");
				return visitAble;
			} else if (visitAble = false) {
				System.out.println("North is not visitable");
				visitAble = false;
				return visitAble;
			}
		}
		visitAble = false;
		return visitAble;
	}

	public Point getNorthPoint(Point point) {
		if (point.getY() - 1 >= 0 && point.getY() - 1 <= 9) {
			return point;
		}
		return point;
	}

	public Point getNorthPoint() {
		Point k = new Point(point.getX(), point.getY(), mayVisit);
		if (point.getY() - 1 >= 0 && point.getY() - 1 <= 9) {
			k.setY(k.getY() - 1);
			return k;
		}else
			return null;
	}

	public void goSouth() {
		if (point.getY() + 1 >= 0 && point.getY() + 1 <= 9) {
			point.setY(point.getY() + 1);
		}
	}

	public Point getSouthPoint() {
		Point k = new Point(point.getX(), point.getY(), mayVisit);
		if (point.getY() + 1 >= 0 && point.getY() + 1 <= 9) {
			k.setY(k.getY() + 1);
			return k;
		}else
			return null;
	}

	public boolean checkSouth() {
		if (point.getY() + 1 >= 0 && point.getY() + 1 <= 9) {
			visitAble = point.isMayVisit();
			if (visitAble == true) {
				System.out.println("South is visitable");
				return visitAble;
			} else if (visitAble = false) {
				System.out.println("South is not visitable");
				visitAble = false;
				return visitAble;
			}
		}
		visitAble = false;
		return visitAble;
	}

	public String[] checkNeighbors() {
		String[] message = { "", "", "", "" };
		// creating a list of neighbors (horizontal and vertical points)
		// next to/relative to the current position
		List<Point> mapOfCurrentNeighbors = new ArrayList<>();
		// check point isVisitable before adding to arraylist
		if (checkEast() == true) {
			// mapOfCurrentNeighbors.add(getEastPoint(point));
		} else if (checkEast() == false) {
			message[0] = "EastIsIsland";
		}
		if (checkWest() == true) {
			mapOfCurrentNeighbors.add(getWestPoint());
		} else if (checkWest() == false) {
			message[1] = "WestIsIsland";
		}
		if (checkNorth() == true) {
			// mapOfCurrentNeighbors.add(getNorthPoint(point));
		} else if (checkNorth() == false) {
			message[2] = "NorthIsIsland";
		}
		if (checkSouth() == true) {
			// mapOfCurrentNeighbors.add(getSouthPoint(point));
		} else if (checkSouth() == false) {
			message[3] = "SouthIsIsland";
		}
		return message;

	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		if (observers.contains(o))
			observers.remove(o);
	}

	@Override
	public boolean notifyObservers(List<Point> islandsMap) {
		for (Observer pirateObserver : observers)
			if(!pirateObserver.update(this, islandsMap))
				return false;
		return true;
	}

}