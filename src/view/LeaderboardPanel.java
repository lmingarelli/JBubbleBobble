package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameController;
import model.GameState;
import model.Player;

public class LeaderboardPanel extends JPanel {
	private ArrayList<Player> leaderboard;

	public LeaderboardPanel() {
		this.drawPanel();
	}

	public void drawPanel() {
		this.removeAll();

		this.updateLeaderboard();

		setLayout(new GridBagLayout());

		add(new JPanel(new GridBagLayout()) {
			{
				var constraints = new GridBagConstraints();

				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.insets.bottom = 20;

				add(new JLabel("LEADERBOARD") {
					{
						setFont(GameView.font.deriveFont(40f));
						setForeground(Color.WHITE);
					}
				}, constraints);

				constraints.gridy = 1;

				add(new JPanel(new GridLayout(6, 1, 10, 10)) {
					{
						JLabel currentLabel;
						for(int i = 0; i < leaderboard.size(); i++){
							Player currentPlayer = leaderboard.get(i);

							currentLabel = new JLabel(
									(i + 1) + " \"" + currentPlayer.getNickname() + "\": "
											+ currentPlayer.getBestScore());
							currentLabel.setFont(GameView.font.deriveFont(20f));
							add(currentLabel);

						}

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

	private void updateLeaderboard() {
		this.leaderboard = GameController.getInstance().getPlayersRecord()
				.getLeaderboard();
	}
}
