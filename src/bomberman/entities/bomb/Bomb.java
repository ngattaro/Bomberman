package bomberman.entities.bomb;
/**
 * Class Bomb
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */

import  bomberman.Board;
import bomberman.Game;
import bomberman.SoundPlayer;
import  bomberman.entities.AnimatedEntitiy;
import  bomberman.entities.Entity;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import bomberman.level.Coordinates;

import java.io.File;

public class Bomb extends AnimatedEntitiy {
	
	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected Board _board;
	public Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
 
	
	/**
     * constructor
     * @param x vi tri tren board
     * @param y vi tri tren board
     * @param board ban do
     */
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
		
	}
	
	/**
	 * update trang thai bomb
	 */
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded)
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	/**
	 * Hien thi bomb ra man hinh
	 * @param screen
	 */
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	/**
	 * Hien thi cac flame
	 * @param screen
	 */
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	/**
	 * Update flame
	 */
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}
	
	/**
	 * Xử lý Bomb nổ
	 */
	protected void explode() {
		Board.explosionSound.play();
		_exploded = true;
		_allowedToPassThru = true;
		// xử lý khi Character đứng tại vị trí Bomb
		Character a = _board.getCharacterAt(_x, _y);
		if(a != null) a.kill();
		// tạo các Flame
		_flames = new Flame[4];
		
		for (int i = 0; i < _flames.length; i++) {
			_flames[i] = new Flame((int)_x, (int)_y, i, Game.getBombRadius(), _board);
		
		}
		
	}
	
	/**
	 * ham lay flamesegment
	 * @param x vi tri tren board
	 * @param y vi tri tren board
	 * @return flameSegment
	 */
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	
	/**
	 * Xu ly va cham
	 * @param e entity
	 * @return boolean
	 */
	@Override
	public boolean collide(Entity e) {
        // xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
        // xử lý va chạm với Flame của Bomb khác
		if(e instanceof Bomber) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
			if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
				_allowedToPassThru = false;
			}
			
			return _allowedToPassThru;
		}
		
		if(e instanceof FlameSegment || e instanceof Flame) {
			_timeToExplode = 0;
			return true;
		}
		
		return false;
	}
}
