
//rt8858487483
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class OceanExplorer extends Application {

	final int dimensions = 10; // we are creating 10x10 maps
	final int scale = 50; // scale everything by 50
	Pane myPane;
	Image shipImage;
	ImageView shipImageView, pirateShip1ImageView, pirateShip2ImageView;
	Image islandImage;
	ImageView islandImageView;
	GridPane gridpaneBoard;
	Scene scene;
	Ship ship;
	PirateShip pirateShip1, pirateShip2;
	Island island;
	public static List<Point> oceanMap = new ArrayList<Point>();
	public static List<Island> islands = new ArrayList<Island>();

	@Override
	public void start(Stage oceanStage) throws Exception {
		// Group root = new Group();
		myPane = new AnchorPane();
		drawMap();
		loadIslandImages();
		for (Island island : islands) {
			oceanMap.add(island.getIslandLocation());
		}
		loadShipImage();
		loadPirateShips();
		scene = new Scene(myPane, 510, 510, Color.WHITE);
		oceanStage.setTitle("Christopher Columbus Sailed the Ocean Blue");
		oceanStage.setScene(scene);
		oceanStage.show();
		startSailing();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void drawMap() {
		gridpaneBoard = new GridPane();
		for (int x = 0; x < dimensions; x++) {
			for (int y = 0; y < dimensions; y++) {
				Rectangle rect = new Rectangle(x * scale, y * scale, scale, scale);
				rect.setStroke(Color.BLACK);
				rect.setFill(Color.PALETURQUOISE);
				gridpaneBoard.add(rect, x, y);
			}
		}
		myPane.getChildren().add(gridpaneBoard);
	}

	private boolean ifAvailable(int x, int y) {
		for (Point point : oceanMap) {
			if (x == point.getX() && y == point.getY())
				return false;
		}
		return true;
	}

	private int[] getRandomCordinates() {
		int randomX, randomY;
		int points[] = new int[2];
		while (true) {
			randomX = ThreadLocalRandom.current().nextInt(0, 9 + 1);
			randomY = ThreadLocalRandom.current().nextInt(0, 9 + 1);
			if (ifAvailable(randomX, randomY)) {
				oceanMap.add(new Point(randomX, randomY, false));
				break;
			}
		}
		points[0] = randomX;
		points[1] = randomY;
		return points;
	}

	private void loadShipImage() throws FileNotFoundException {
		Image shipImage = new Image(new FileInputStream("ship.png"), scale, scale, true, true);
		shipImageView = new ImageView(shipImage);
		int points[] = getRandomCordinates();
		ship = new Ship(points[0], points[1]);
		shipImageView.setX(ship.getShipLocation().getX() * (scale + 1.25));
		shipImageView.setY(ship.getShipLocation().getY() * (scale + 1.25));
		myPane.getChildren().add(shipImageView);
	}

	private void loadPirateShips() throws FileNotFoundException {
		Image pirateShipImage = new Image(new FileInputStream("pirateShip.png"), scale, scale, true, true);
		pirateShip1ImageView = new ImageView(pirateShipImage);

		int points[] = getRandomCordinates();
		pirateShip1 = new PirateShip(points[0], points[1]);
		pirateShip1ImageView.setX(pirateShip1.getShipLocation().getX() * (scale + 1.25));
		pirateShip1ImageView.setY(pirateShip1.getShipLocation().getY() * (scale + 1.25));
		myPane.getChildren().add(pirateShip1ImageView);
		// Register Pirate Ship for Ship
		ship.registerObserver(pirateShip1);

		pirateShip2ImageView = new ImageView(pirateShipImage);
		points = getRandomCordinates();
		pirateShip2 = new PirateShip(points[0], points[1]);
		pirateShip2ImageView.setX(pirateShip2.getShipLocation().getX() * (scale + 1.25));
		pirateShip2ImageView.setY(pirateShip2.getShipLocation().getY() * (scale + 1.25));
		myPane.getChildren().add(pirateShip2ImageView);
		// Register Pirate Ship for Ship
		ship.registerObserver(pirateShip2);
	}

	public static boolean useLoop(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}

	public void createIsland(Image islandImage, int randomX, int randomY, int extra) {
		islandImageView = new ImageView(islandImage);
		island = new Island(randomX, randomY, false);
		islandImageView.setX(island.getIslandLocation().getX() * (scale + 1.25));
		islandImageView.setY(island.getIslandLocation().getY() * (scale + 1.25));
		myPane.getChildren().add(islandImageView);
		islands.add(island);
	}

	public void loadIslandImages() throws FileNotFoundException {

		Image islandImage = new Image(new FileInputStream("island.jpg"), scale, scale, false, true);
		// add 10 random islands to the grid
		// any two islands cannot be ontop of eachother in the same location
		int randomX = 0;
		int randomY = 0;
		int numIslands = 0;
		Map<Integer, Integer> islandMap = new HashMap<Integer, Integer>();
		while (numIslands < 10) {
			randomX = ThreadLocalRandom.current().nextInt(0, 9 + 1);
			randomY = ThreadLocalRandom.current().nextInt(0, 9 + 1);
			if (islandMap.containsKey(randomX)) {
				int y = islandMap.get(randomX);
				if (y != randomY) {
					islandMap.put(randomX, randomY);
					createIsland(islandImage, randomX, randomY, numIslands);
					numIslands++;
				} // Else -> Island already present at that location
			} else {
				islandMap.put(randomX, randomY);
				createIsland(islandImage, randomX, randomY, numIslands);
				numIslands++;
			}
		}
	}

	private void startSailing() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				switch (ke.getCode()) {
				case RIGHT:
					Point eastPoint = ship.getEastPoint();
					if(eastPoint != null){
						for (Island is : islands) {
							Point islandPoint = is.getIslandLocation();
							if (islandPoint.isSamePoint(eastPoint)) {
								System.out.println("Found a match");
								return;
							}
						}
						ship.goEast();
						// Finding optimal move for pirates - If notify return false then move ship back to its place
						if(!ship.notifyObservers(oceanMap))
							ship.goWest();
						break;
					}
				case LEFT:
					Point westPoint = ship.getWestPoint();
					if(westPoint != null){
						for (Island is : islands) {
							Point islandPoint = is.getIslandLocation();
							if (islandPoint.isSamePoint(westPoint)) {
								System.out.println("Found a match");
								return;
							}
							
						}
						ship.goWest();
						// Finding optimal move for pirates - If notify return false then move ship back to its place
						if(!ship.notifyObservers(oceanMap))
							ship.goEast();
					}
					break;
				case UP:
					Point northPoint = ship.getNorthPoint();
					if(northPoint != null){
						for (Island is : islands) {
							Point islandPoint = is.getIslandLocation();
							if (islandPoint.isSamePoint(northPoint)) {
								System.out.println("Found a match");
								return;
							}
						}
						ship.goNorth();
						// Finding optimal move for pirates - If notify return false then move ship back to its place
						if(!ship.notifyObservers(oceanMap))
							ship.goSouth();
					}
					break;
				case DOWN:
					Point southPoint = ship.getSouthPoint();
					if(southPoint != null){
						for (Island is : islands) {
							Point islandPoint = is.getIslandLocation();
							if (islandPoint.isSamePoint(southPoint)) {
								System.out.println("Found a match");
								return;
							}
						}
						ship.goSouth();
						// Finding optimal move for pirates - If notify return false then move ship back to its place
						if(!ship.notifyObservers(oceanMap))
							ship.goNorth();
					}
					break;
				default:
					break;
				}
				shipImageView.setX(ship.getShipLocation().getX() * (scale + 1.25));
				shipImageView.setY(ship.getShipLocation().getY() * (scale + 1.25));

				// Changing locations
				pirateShip1ImageView.setX(pirateShip1.getShipLocation().getX() * (scale + 1.25));
				pirateShip1ImageView.setY(pirateShip1.getShipLocation().getY() * (scale + 1.25));

				pirateShip2ImageView.setX(pirateShip2.getShipLocation().getX() * (scale + 1.25));
				pirateShip2ImageView.setY(pirateShip2.getShipLocation().getY() * (scale + 1.25));
			}
		});
	}

}