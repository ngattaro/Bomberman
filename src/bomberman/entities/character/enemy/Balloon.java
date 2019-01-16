package  bomberman.entities.character.enemy;
/**
 * Class Balloon
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Board;
import  bomberman.Game;
import  bomberman.entities.character.enemy.ai.AILow;
import  bomberman.graphics.Sprite;

public class Balloon extends Enemy {
	
	
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.balloom_left1;
		
		_ai = new AILow();
		//_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
				_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 1:
					_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 2:
				_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
			case 3:
					_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
		}
	}
}
