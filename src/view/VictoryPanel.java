package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameState;

public class VictoryPanel extends JPanel {

	public VictoryPanel() {
		setLayout(new GridBagLayout());

		add(new JPanel(new GridBagLayout()) {
			{
				var constraints = new GridBagConstraints();

				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.insets.bottom = 20;

				add(new JLabel("VICTORY") {
					{
						setFont(GameView.font.deriveFont(40f));
						setForeground(Color.YELLOW);
					}
				}, constraints);

				constraints.gridy = 1;

				add(new JPanel(new GridLayout(1, 1, 10, 10)) {
					{
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
}
