package  bomberman.entities.character.enemy.ai;
/**
 * Class AI
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import java.util.Random;

public abstract class AI {
	
	protected Random random = new Random();

	/**
	 * Thuật toán tìm đường đi
	 * @return hướng đi up/right/down/left tương ứng với các giá trị 0/1/2/3
	 */
	public abstract int calculateDirection();
}
