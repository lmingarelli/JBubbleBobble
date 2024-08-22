package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameModel;
import model.GameState;
import model.Player;

public class UserPanel extends JPanel {
	private Player player;
	private String nickname;
	private int games;
	private int win;
	private int lose;
	private int bestScore;

	public UserPanel() {
		this.player = GameModel.getInstance().getPlayer();

		drawPanel();
	}

	public void drawPanel() {
		this.removeAll();

		updatePlayerInfo();

		setLayout(new GridBagLayout());

		add(new JPanel(new GridBagLayout()) {
			{
				var constraints = new GridBagConstraints();

				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.insets.bottom = 20;

				add(new JLabel("USER PAGE") {
					{
						setFont(GameView.font.deriveFont(40f));
						setForeground(Color.WHITE);
					}
				}, constraints);

				constraints.gridy = 1;

				add(new JPanel(new GridLayout(6, 1, 10, 10)) {
					{
						// Nickname label
						JLabel nicknameLabel = new JLabel(
								"NICKNAME: \"" + nickname + "\"");
						nicknameLabel.setFont(GameView.font.deriveFont(20f));
						add(nicknameLabel);

						// Games player label
						JLabel gamesPlayedLabel = new JLabel(
								"GAMES PLAYED: " + games);
						gamesPlayedLabel.setFont(GameView.font.deriveFont(20f));
						add(gamesPlayedLabel);

						// Games won label
						JLabel winLabel = new JLabel(
								"GAMES WON: " + win);
						winLabel.setFont(GameView.font.deriveFont(20f));
						add(winLabel);

						// Games lost label
						JLabel loseLabel = new JLabel(
								"GAMES LOST: " + lose);
						loseLabel.setFont(GameView.font.deriveFont(20f));
						add(loseLabel);

						// Best score label
						JLabel bestScoreLabel = new JLabel(
								"BEST SCORE: " + bestScore);
						bestScoreLabel.setFont(GameView.font.deriveFont(20f));
						add(bestScoreLabel);

						JButton backToMenu = new JButton("BACK TO MENU");
						backToMenu.setFont(GameView.font.deriveFont(30f));
						backToMenu.addActionListener(e -> {
							Navigator.getInstance().navigate(GameState.MENU);
						});

						add(backToMenu);
					}
				}, constraints);
			}
		});
	}

	public void updatePlayerInfo() {
		this.nickname = player.getNickname();
		this.games = player.getGamesNumber();
		this.win = player.getWinNumber();
		this.lose = player.getLoseNumber();
		this.bestScore = player.getBestScore();
	}

}
