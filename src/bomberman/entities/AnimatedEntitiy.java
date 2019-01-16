package  bomberman.entities;
/**
 * Class AnimatedEntity
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
/**
 * Entity có hiệu ứng hoạt hình
 */
public abstract class AnimatedEntitiy extends Entity {

	protected int _animate = 0;
	protected final int MAX_ANIMATE = 7500;
	
	protected void animate() {
		if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
	}

}
