package  bomberman.gui;
/**
 * Class InforPanel
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {
	
	private JLabel timeLabel;
	private JLabel pointsLabel;
	private JLabel livesLabel;
	private JLabel HighScoreLabel;
	
	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.YELLOW);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		
		livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.RED);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		
		HighScoreLabel = new JLabel("High Score: " + game.getBoard().getHighScore());
		HighScoreLabel.setForeground(Color.ORANGE);
		HighScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(timeLabel);
		add(pointsLabel);
		add(livesLabel);
		add(HighScoreLabel);
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
	}
	
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}
	public void setLives(int t) {
		livesLabel.setText("Lives: " + t);
		
	}
	public void setPoints(int t) {
		pointsLabel.setText("Score: " + t);
	}
	
	public void setHighScore(int t) {
		HighScoreLabel.setText("High Score: " + t);
	}
	
}
