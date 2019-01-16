package  bomberman.entities.character.enemy;
/**
 * Class Enemy
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Board;
import  bomberman.Game;
import bomberman.SoundPlayer;
import  bomberman.entities.Entity;
import  bomberman.entities.Message;
import  bomberman.entities.bomb.Flame;
import bomberman.entities.bomb.FlameSegment;
import  bomberman.entities.character.Bomber;
import  bomberman.entities.character.Character;
import  bomberman.entities.character.enemy.ai.AI;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import bomberman.level.Coordinates;


import java.awt.*;
import java.io.File;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	public static SoundPlayer enemyDieSound = new SoundPlayer(new File("res/sound/enemyDie.wav"));
	
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = 0;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		//  Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// sử dụng move() để di chuyển
		//  nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		int xa = 0, ya = 0;
		if(_steps <= 0){
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}
		
		if(_direction == 0) ya--;
		if(_direction == 2) ya++;
		if(_direction == 3) xa--;
		if(_direction == 1) xa++;
		
		if(canMove(xa, ya)) {
			_steps -= 1 + rest;
			move(xa * _speed, ya * _speed);
			_moving = true;
		} else {
			_steps = 0;
			_moving = false;
		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		// kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
	//	double xr = _x, yr = _y - 16;
		int xx = (int)x, yy = (int) y;
		if(_direction == 0)
		{
			yy += Coordinates.pixelToTile(_y-16+ _sprite.getSize() -1);
			xx += Coordinates.pixelToTile(_x+ _sprite.getSize()/2);
		}
		else
		if(_direction == 1)
		{
			yy += Coordinates.pixelToTile(_y-16+_sprite.getSize()/2);
			xx += Coordinates.pixelToTile(_x+ 1);
		}else
		if(_direction == 2)
		{
			yy += Coordinates.pixelToTile(_y-16 + 1);
			xx += Coordinates.pixelToTile(_x + _sprite.getSize()/2);
		}else
		if(_direction == 3)
		{
			yy += Coordinates.pixelToTile(_y-16 + _sprite.getSize()/2);
			xx += Coordinates.pixelToTile(_x + _sprite.getSize() -1);
		}
		else
		{
			xx += Coordinates.pixelToTile(_x) +(int)x;
			yy += Coordinates.pixelToTile(_y-16) +(int)y;
		}
		Entity a = _board.getEntity(xx, yy, this);
		return a.collide(this);
	}

	@Override
	public boolean collide(Entity e) {
		// xử lý va chạm với Flame
		if(e instanceof Flame || e instanceof FlameSegment)
		{
			kill();
			return false;
		}
		// xử lý va chạm với Bomber
		if(e instanceof Bomber)
		{
			if(!isCollisionHappenWith((Character)e)) return true;
			((Bomber)e).kill();
			return false;
		}
		return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
		enemyDieSound.play();
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
