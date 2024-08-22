package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.GameController;
import model.GameState;

public class NicknamePanel extends JPanel {
	private GameController gameController;
	private GameView gameView;

	public NicknamePanel() {
		this.gameController = GameController.getInstance();
		this.gameView = GameView.getInstance();

		setLayout(new GridBagLayout());

		add(new JPanel(new GridBagLayout()) {
			{
				var constraints = new GridBagConstraints();

				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.insets.bottom = 20;

				add(new JLabel("ENTER NICKNAME") {
					{
						setFont(GameView.font.deriveFont(30f));
						setForeground(Color.YELLOW);
					}
				}, constraints);

				constraints.gridy = 1;

				add(new JPanel(new GridLayout(3, 1, 10, 10)) {
					{
						JLabel errorLabel = new JLabel("INVALID NICKNAME");
						errorLabel.setFont(GameView.font.deriveFont(20f));
						errorLabel.setForeground(Color.RED);
						errorLabel.setVisible(false);

						add(errorLabel);

						JTextArea editTextArea = new JTextArea();
						editTextArea.setFont(GameView.font.deriveFont(20f));
						editTextArea.setBackground(Color.BLACK);
						editTextArea.setForeground(Color.WHITE);

						add(editTextArea);

						JButton backToMenu = new JButton("ENTER");
						backToMenu.setFont(GameView.font.deriveFont(20f));
						backToMenu.addActionListener(e -> {
							String text = editTextArea.getText().toUpperCase();

							String validateResult = validateText(text);
							if(validateResult == "OK"){
								gameController.loadPlayer(text);
								Navigator.getInstance().navigate(GameState.MENU);
							}else{
								errorLabel.setText(validateResult);
								errorLabel.setVisible(true);
							}
						});

						add(backToMenu);
					}
				}, constraints);

			}
		});
	}

	private String validateText(String text) {
		String result = "OK";

		int maxLength = 8;
		if(text.length() > maxLength){
			result = "Max length (" + maxLength + ") exceeded!";
		}

		if(text.contains("\n")){
			result = "Can't contain new lines";
		}

		if(text.equals("")){
			result = "Can't be empty";
		}

		return result;
	}
}
