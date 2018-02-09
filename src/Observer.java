import java.util.List;

public interface Observer {
	public boolean update(Ship ship, List<Point> islandsMap);
}
