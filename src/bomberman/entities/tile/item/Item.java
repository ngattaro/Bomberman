package  bomberman.entities.tile.item;
/**
 * Class Item
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */

import bomberman.SoundPlayer;
import  bomberman.entities.tile.Tile;
import  bomberman.graphics.Sprite;

import java.io.File;

public abstract class Item extends Tile {
	
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
