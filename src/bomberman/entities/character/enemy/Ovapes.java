package bomberman.entities.character.enemy;
/**
 * Class Ovapes
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */

import bomberman.Board;
import bomberman.Game;
import bomberman.entities.character.enemy.ai.AILow;
import bomberman.graphics.Sprite;

public class Ovapes extends Enemy {
    
    
    public Ovapes(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed(), 200);
        
        _sprite = Sprite.ovapes_left1;
        
        _ai = new AILow();
  //      _direction = _ai.calculateDirection();
    }
    
    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
                _sprite = Sprite.movingSprite(Sprite.ovapes_right1, Sprite.ovapes_right2, Sprite.ovapes_right3, _animate, 60);
                break;
            case 1:
                _sprite = Sprite.movingSprite(Sprite.ovapes_right1, Sprite.ovapes_right2, Sprite.ovapes_right3, _animate, 60);
                break;
            case 2:
                _sprite = Sprite.movingSprite(Sprite.ovapes_left1, Sprite.ovapes_left2, Sprite.ovapes_left3, _animate, 60);
                break;
            case 3:
                _sprite = Sprite.movingSprite(Sprite.ovapes_left1, Sprite.ovapes_left2, Sprite.ovapes_left3, _animate, 60);
                break;
        }
    }
}
