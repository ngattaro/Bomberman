package bomberman.entities.character.enemy;
/**
 * Class Kondoria
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIHigh;
import bomberman.graphics.Sprite;

public class Kondoria extends Enemy
{
	
	
	public Kondoria(int x, int y, Board board) {
		super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed()/1.5, 1000);
		
		_sprite = Sprite.kondoria_right1;
		
		_ai = new AIHigh(_board,_board.getBomber(), this);
	//	_direction  = _ai.calculateDirection();
	}
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 2:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
		}
	}
}
