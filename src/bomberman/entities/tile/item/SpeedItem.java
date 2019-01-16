package  bomberman.entities.tile.item;
/**
 * Class SpeedItem
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.SoundPlayer;
import  bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import  bomberman.graphics.Sprite;

import java.io.File;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// xử lý Bomber ăn Item
		if(isRemoved()) return false;
		
		if(e instanceof Bomber) {
			Game.addBomberSpeed(0.5);
			remove();
			Board.getItemSound.play();
			return true;
		}
		return false;	}
}
