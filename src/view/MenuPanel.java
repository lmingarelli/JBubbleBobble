package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameController;
import model.GameState;

public class MenuPanel extends JPanel {
	private GameController gameController;

	public MenuPanel() {
		this.gameController = GameController.getInstance();
		setLayout(new GridBagLayout());

		add(new JPanel(new GridBagLayout()) {
			{
				var constraints = new GridBagConstraints();

				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.insets.bottom = 20;

				add(new JLabel() {
					{
						setIcon(new ImageIcon(ViewConstants.LOGO_IMG_PATH));
					}
				}, constraints);

				constraints.gridy = 1;

				add(new JPanel(new GridLayout(4, 1, 10, 10)) {
					{
						JButton play = new JButton("PLAY");
						play.setFont(GameView.font.deriveFont(30f));
						play.addActionListener(e -> {
							gameController.startGame();
							Navigator.getInstance().navigate(GameState.GAME);
						});

						add(play);

						JButton userPage = new JButton("USER PAGE");
						userPage.setFont(GameView.font.deriveFont(30f));
						userPage.addActionListener(e -> {
							Navigator.getInstance().navigate(GameState.USERPAGE);
						});

						add(userPage);

						JButton leaderboardPage = new JButton("LEADERBOARD");
						leaderboardPage.setFont(GameView.font.deriveFont(30f));
						leaderboardPage.addActionListener(e -> {
							Navigator.getInstance().navigate(GameState.LEADERBOARD);
						});

						add(leaderboardPage);

						JButton exitButton = new JButton("EXIT");
						exitButton.setFont(GameView.font.deriveFont(30f));
						exitButton.addActionListener(e -> {
							Navigator.getInstance().navigate(GameState.EXIT);
						});

						add(exitButton);
					}
				}, constraints);
			}
		});
	}
}
