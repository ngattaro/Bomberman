package  bomberman.gui;
/**
 * Class GamePanel
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

	private Game _game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		_game = new Game(frame);

		add(_game);

		_game.setVisible(true);

		setVisible(true);
		setFocusable(true);
		
	}

	public Game getGame() {
		return _game;
	}
	
}
