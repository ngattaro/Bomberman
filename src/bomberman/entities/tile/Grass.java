package  bomberman.entities.tile;

/**
 * Class Grass
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.entities.Entity;
import  bomberman.graphics.Sprite;

public class Grass extends Tile {

	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	/**
	 * Cho bất kì đối tượng khác đi qua
	 * @param e
	 * @return
	 */
	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
