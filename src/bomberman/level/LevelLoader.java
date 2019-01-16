package  bomberman.level;
/**
 * Class LevelLoader
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Board;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected Board _board;

	public LevelLoader(Board board, int level)  {
		_board = board;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) ;
	public abstract void createEntities();
	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}
	public void setLevel(int level) {
		 _level = level;
	}
	
	
}
