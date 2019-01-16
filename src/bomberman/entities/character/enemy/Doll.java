package bomberman.entities.character.enemy;
/**
 * Class Doll
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.graphics.Sprite;

public class Doll extends Enemy
{
	
	public Doll(int x, int y, Board board) {
		super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed(), 400);
		
		_sprite = Sprite.doll_right1;
		
		_ai = new AILow();
	//	_direction = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
			case 2:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
				else
					_sprite = Sprite.doll_left1;
				break;
		}
	}
}
