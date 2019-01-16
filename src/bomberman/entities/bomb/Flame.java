package  bomberman.entities.bomb;
/**
 * Class Flame
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Board;
import  bomberman.entities.Entity;
import bomberman.entities.LayeredEntity;
import bomberman.entities.character.Character;
import bomberman.entities.tile.destroyable.Brick;
import  bomberman.graphics.Screen;

import java.util.Iterator;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	public FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */
		boolean last = false;

		// tạo các segment dưới đây
		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++) {
			last = (i == _flameSegments.length -1);
			
			if(_direction==0) y--;
			else if (_direction == 1) x++;
			else if(_direction==2) y++;
			else if(_direction==3) x--;
			_flameSegments[i] = new FlameSegment(x, y, _direction, last);
			
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	public int calculatePermitedDistance() {
		// thực hiện tính toán độ dài của Flame
		int radius = 0;
		int x = (int)_x;
		int y = (int)_y;
		while(radius < _radius) {
			if(_direction == 0) y--;
			else if(_direction == 1) x++;
			else if(_direction == 2) y++;
			else if(_direction == 3) x--;
			
			Entity a = _board.getEntity(x, y, null);
			if(a instanceof Character)
			{
				++radius;
				Iterator<Character> itr = _board._characters.iterator();
				
				Character cur;
				while (itr.hasNext())
				{
					cur = itr.next();
					if (cur.getXTile() == x && cur.getYTile() == y)
					{
						cur.collide(this);
					}
					
				}
				a = _board.getEntityAt(x,y);
				a.collide(this);
				break;
			}
			
			
			if(!a.collide(this)) break;
			++radius;
		}
		return radius;
	}
	
	/**
	 * Ham lay FlameSefment
	 * @param x vi tri tren board
	 * @param y vi tri tren board
	 * @return flameSegment
	 */
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}
	
	@Override
	public boolean collide(Entity e)
	{
		// xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
		if(e instanceof Character)
		{
			((Character)e).kill();
			return false;
		}
		return true;
		
	}
}
