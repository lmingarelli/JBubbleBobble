package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.GameController;
import model.Block;
import model.Bonus;
import model.Bubble;
import model.BubblesManager;
import model.ElementalBubble;
import model.Enemy;
import model.Fire;
import model.GameModel;
import model.Grid;
import model.Level;
import model.Player;
import model.PowerUp;
import model.Thunder;

public class GamePanel extends JPanel {
	private Player player;
	private GameController gameController;
	private GameModel gameModel;
	private int lastRecordedPlayerLives;
	private int playerMaxBlinkingTimes;
	private int playerCurrentBlinkingTimes;
	private boolean isBlinking;
	private boolean toHide;

	private KeyListener keyListener;

	public GamePanel() {
		this.gameController = GameController.getInstance();
		this.gameModel = GameModel.getInstance();
		this.playerMaxBlinkingTimes = 5;
		this.lastRecordedPlayerLives = 4;

		this.setBackground(Color.BLACK);
		this.addKeyListener(gameController.getKeyAdapter());

		this.setFocusable(true);

//		setLayout(new GridBagLayout());
//
//		add(new JPanel(new GridBagLayout()) {
//			{
//				var constraints = new GridBagConstraints();
//				constraints.weightx = 1;
//				constraints.weighty = 1;
//				constraints.insets.bottom = 20;
//				constraints.gridy = 1;
//			}
//		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		this.player = gameModel.getPlayer();

		int scaleFactor = ViewConstants.SCALE_FACTOR;
		g2.scale(scaleFactor, scaleFactor);

		// Drawing the map level
		drawMap(g2);

		// Drawing header
		drawHeader(g2);

		// Drawing bubbles
		drawBubbles(g2);

		// Drawing bonuses
		drawItems(g2);

		// Drawing enemies
		drawEnemies(g2);

		// Drawing the player
		Image playerImage = new ImageIcon(player.getImagePath()).getImage();

		if(this.isPlayerToBeShown()){
			g2.drawImage(playerImage, player.getX(), player.getY(), this);
		}
	}

	private boolean isPlayerToBeShown() {
		if(this.player.isInvulnerable()){
			if(this.toHide){
				this.toHide = false;
				return false;
			}else{
				this.toHide = true;
				return true;
			}
		}

		return true;
	}

	private void drawHeader(Graphics2D g2) {
		g2.setFont(GameView.font.deriveFont(10f));
		g2.setColor(Color.WHITE);
		g2.drawString("Score \n" + player.score, 0, 268);
	}

	private void drawMap(Graphics2D g2) {
		Grid map = gameModel.getCurrentLevelObject();
		Block[][] matrix = map.getMatrix();

		for(int y = 0; y < map.gridDimension; y++){
			for(int x = 0; x < map.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null){
					Image blockImage = new ImageIcon(currentBlock.getImagePath())
							.getImage();
					g2.drawImage(blockImage, currentBlock.getX(), currentBlock.getY(),
							this);
				}
			}
		}

		Image lifeImage = new ImageIcon("assets/sprites/misc/life.png")
				.getImage();
		for(int i = 0; i < player.getLives(); i++){
			g2.drawImage(lifeImage, 0 + (i * 8), 244,
					this);
		}
	}

	private void drawBubbles(Graphics2D g2) {
		BubblesManager bubblesManager = gameModel.getBubblesManager();
		ArrayList<Bubble> bubbles = bubblesManager.getBubbles();

		for(int i = 0; i < bubbles.size(); i++){
			Bubble currentBubble = bubbles.get(i);
			Image bubbleImage = new ImageIcon(currentBubble.getImagePath())
					.getImage();

			g2.drawImage(bubbleImage, currentBubble.getX(), currentBubble.getY(),
					this);
		}

		// Drawing fire and thunder bubbles
		ElementalBubble fireBubble = bubblesManager.getFireBubble();
		if(fireBubble != null){
			Image fireBubbleImage = new ImageIcon(fireBubble.getImagePath())
					.getImage();
			g2.drawImage(fireBubbleImage, fireBubble.getX(), fireBubble.getY(),
					this);
		}

		ElementalBubble thunderBubble = bubblesManager.getThunderBubble();
		if(thunderBubble != null){
			Image thunderBubbleImage = new ImageIcon(thunderBubble.getImagePath())
					.getImage();
			g2.drawImage(thunderBubbleImage, thunderBubble.getX(),
					thunderBubble.getY(),
					this);
		}

	}

	private void drawEnemies(Graphics2D g2) {
		Level level = gameModel.getCurrentLevelObject();
		ArrayList<Enemy> enemies = level.getEnemies();

		for(int i = 0; i < enemies.size(); i++){
			Enemy currentEnemy = enemies.get(i);

			if(currentEnemy.getImagePath().contains("pon")){
				int debug = 0;
			}

			Image enemyImage = new ImageIcon(currentEnemy.getImagePath())
					.getImage();

			g2.drawImage(enemyImage, currentEnemy.getX(), currentEnemy.getY(),
					this);
		}
	}

	private void drawItems(Graphics2D g2) {
		Level level = gameModel.getCurrentLevelObject();

		// Drawing bonuses
		ArrayList<Bonus> bonuses = level.getBonuses();

		for(int i = 0; i < bonuses.size(); i++){
			Bonus currentBonus = bonuses.get(i);
			Image bonusImage = new ImageIcon(currentBonus.getImagePath())
					.getImage();

			g2.drawImage(bonusImage, currentBonus.getX(), currentBonus.getY(),
					this);
		}

		// Drawing power-up
		PowerUp powerup = level.getPowerUp();
		if(powerup != null && powerup.isHasToBeShowed()){
			Image powerupImage = new ImageIcon(powerup.getImagePath())
					.getImage();
			g2.drawImage(powerupImage, powerup.getX(), powerup.getY(),
					this);
		}

		// Drawing special bubbles sprites
		ArrayList<Fire> flames = level.getFlames();
		for(int i = 0; i < flames.size(); i++){
			Fire currentFlame = flames.get(i);
			Image fireImage = new ImageIcon(currentFlame.getImagePath())
					.getImage();

			g2.drawImage(fireImage, currentFlame.getX(), currentFlame.getY(),
					this);
		}

		Thunder rightThunder = level.getRightThunder();
		if(rightThunder != null){
			Image thunderImage = new ImageIcon(rightThunder.getImagePath())
					.getImage();
			g2.drawImage(thunderImage, rightThunder.getX(), rightThunder.getY(),
					this);
		}

		Thunder leftThunder = level.getLeftThunder();
		if(leftThunder != null){
			Image thunderImage = new ImageIcon(leftThunder.getImagePath())
					.getImage();
			g2.drawImage(thunderImage, leftThunder.getX(), leftThunder.getY(),
					this);
		}

	}
}
