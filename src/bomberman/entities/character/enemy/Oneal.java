package  bomberman.entities.character.enemy;
/**
 * Class Oneal
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */

import  bomberman.Board;
import  bomberman.Game;
import  bomberman.entities.character.enemy.ai.AIMedium;
import  bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);
		
		_sprite = Sprite.oneal_left1;
		
		_ai = new AIMedium(_board,_board.getBomber(), this);
	//	_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_up1, Sprite.oneal_up2, Sprite.oneal_up3, _animate, 60);
				else
					_sprite = Sprite.oneal_up1;
				break;
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_down1, Sprite.oneal_down2, Sprite.oneal_down3, _animate, 60);
				else
					_sprite = Sprite.oneal_down1;
				break;
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
}
