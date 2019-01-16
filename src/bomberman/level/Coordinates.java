package  bomberman.level;
/**
 * Class Coordinates
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Game;

public class Coordinates {
	
	public static int pixelToTile(double i) {
		return (int)(i / Game.TILES_SIZE);
	}
	
	public static int tileToPixel(int i) {
		return i * Game.TILES_SIZE;
	}
	
	public static int tileToPixel(double i) {
		return (int)(i * Game.TILES_SIZE);
	}
	
	
}
