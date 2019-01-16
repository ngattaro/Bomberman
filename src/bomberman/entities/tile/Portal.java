package  bomberman.entities.tile;
/**
 * Class Portal
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import  bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import  bomberman.graphics.Sprite;

public class Portal extends Tile {
	protected Board _board;
	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		_board = board;
	}
	
	@Override
	public boolean collide(Entity e) {
		// xử lý khi Bomber đi vào
		if(e instanceof Bomber) {
			if(_board.detectNoEnemies() == false)
				return false;
			
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(_board.detectNoEnemies())
					_board.nextLevel();
			}
			
			return true;
		}
		return false;
	}

}
