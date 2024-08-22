package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import controller.GameController;
import model.GameModel;
import model.GameState;
import model.Player;

@SuppressWarnings("deprecation")
public class GameView extends JFrame implements Observer {
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private UserPanel userPanel;
	private GameOverPanel gameOverPanel;
	private VictoryPanel victoryPanel;
	private NicknamePanel nicknamePanel;
	private LeaderboardPanel leaderboardPanel;
	private static GameView gameView;
	public static Font font;

	private JPanel deck;

	public static GameView getInstance() {
		if(gameView == null) gameView = new GameView();
		return gameView;
	}

	private GameView() {
		super("BubbleBobble");

		// UIManager setup

		try{
			font = Font.createFont(Font.TRUETYPE_FONT,
					new File(ViewConstants.FONT_PATH));
		}catch (FontFormatException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}

		UIManager.put("Label.font", font);
		UIManager.put("Label.foreground", Color.WHITE);
		UIManager.put("Label.background", Color.BLACK);

		UIManager.put("Button.font", font);
		UIManager.put("Button.border",
				BorderFactory.createEmptyBorder(10, 15, 10, 15));

		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.background", Color.BLACK);

		UIManager.put("Panel.background", Color.BLACK);

		// JFrame setup
		this.setIconImage(new ImageIcon(ViewConstants.LOGO_IMG_PATH).getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(
				new Dimension(ViewConstants.SCREEN_WIDTH, ViewConstants.SCREEN_HEIGHT));

		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				GameController.getInstance().onExit();
			}
		});

		// Navigator setup
		Navigator.getInstance().addObserver(this);
	}

	public void buildCardLayout() {
		deck = new JPanel(new CardLayout());

		this.nicknamePanel = new NicknamePanel();
		deck.add(nicknamePanel, GameState.NICKNAME.name());

		this.menuPanel = new MenuPanel();
		deck.add(menuPanel, GameState.MENU.name());

		this.gamePanel = new GamePanel();
		deck.add(gamePanel, GameState.GAME.name());

		this.gameOverPanel = new GameOverPanel();
		deck.add(gameOverPanel, GameState.GAMEOVER.name());

		this.victoryPanel = new VictoryPanel();
		deck.add(victoryPanel, GameState.VICTORY.name());

		this.add(deck);
	}

	public void loadPlayerInfoOnPage() {
		this.userPanel = new UserPanel();
		deck.add(userPanel, GameState.USERPAGE.name());
	}

	public void loadLeaderboard(ArrayList<Player> leaderboard) {
		this.leaderboardPanel = new LeaderboardPanel(leaderboard);
		deck.add(leaderboardPanel, GameState.LEADERBOARD.name());
	}

	@Override
	public void update(Observable o, Object arg) {
		// If there was a game state change
		if(o instanceof Navigator && arg instanceof GameState){
			// Change visual
			GameState newState = (GameState) arg;

			switch(newState) {
				case GameState.GAME:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.gamePanel.requestFocusInWindow();
					break;
				case GameState.MENU:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.menuPanel.requestFocusInWindow();
					break;
				case GameState.USERPAGE:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.userPanel.requestFocusInWindow();
					userPanel.drawPanel();
					break;
				case GameState.LEADERBOARD:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.userPanel.requestFocusInWindow();
					break;
				case GameState.EXIT:
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
					break;
			}
		}

		if(o instanceof GameModel && arg instanceof GameState){
			GameState newState = (GameState) arg;

			switch(newState) {
				case GameState.GAME:
					gamePanel.repaint();
					break;

				case GameState.GAMEOVER:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.gameOverPanel.requestFocusInWindow();
					GameController.getInstance().gameOver();
					break;

				case GameState.VICTORY:
					((CardLayout) deck.getLayout()).show(deck, (newState).name());
					this.victoryPanel.requestFocusInWindow();
					break;
			}
		}
	}

	public void updateInfoOnPlayerPage() {
		userPanel.updatePlayerInfo();
	}
}
