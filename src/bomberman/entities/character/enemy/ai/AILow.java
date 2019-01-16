package  bomberman.entities.character.enemy.ai;
/**
 * Class AILow (di random)
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
public class AILow extends AI {

	@Override
	public int calculateDirection() {
		// cài đặt thuật toán tìm đường đi
		return random.nextInt(4);
	}

}
