package bomberman.entities.character.enemy;

/**
 * Class Minvo
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AIMedium;
import bomberman.graphics.Sprite;

public class Minvo extends Enemy {
	
	
	public Minvo(int x, int y, Board board) {
		super(x, y, board, Sprite.minvo_dead, Game.getBomberSpeed() * 2, 800);
		
		_sprite = Sprite.minvo_right1;
		
		_ai = new AIMedium(_board,_board.getBomber(), this);
//		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, 60);
				else
					_sprite = Sprite.minvo_left1;
				break;
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, 60);
				else
					_sprite = Sprite.minvo_left1;
				break;
			case 2:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, 60);
				else
					_sprite = Sprite.minvo_left1;
				break;
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, 60);
				else
					_sprite = Sprite.minvo_left1;
				break;
		}
	}
}
