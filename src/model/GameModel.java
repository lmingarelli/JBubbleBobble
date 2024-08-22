package model;

import java.util.Observable;

import model.PowerUp.PowerUpType;

@SuppressWarnings("deprecation")
public class GameModel extends Observable {
	private Player player;
	private GameState gameState;
	private int currentLevel;
	private Level currentLevelObject;
	private BubblesManager bubblesManager;

	public boolean isNextLevelTimerStarted;

	private static GameModel game;

	private GameModel() {
		this.player = new Player();
		this.gameState = GameState.MENU;
		this.currentLevel = 0;
		this.bubblesManager = new BubblesManager();
		this.isNextLevelTimerStarted = false;
	}

	public static GameModel getInstance() {
		if(game == null) game = new GameModel();
		return game;
	}

	public void startGame() {
		this.player.setGamesNumber(this.player.getGamesNumber() + 1);
		this.currentLevelObject = LevelsManager.getFirstLevel();
		this.currentLevel = 1;
		this.gameState = GameState.GAME;
		updateModel();
	}

	public void updateModel() {
		// Move Player
		player.updateModel(this.game);

		// Move bubbles
		bubblesManager.updateBubbles(this.currentLevelObject);

		// Move enemies and check collision between player and enemies
		currentLevelObject.updateModel();

		if(this.player.isDead()){
			gameOver();
		}

		// Update observers
		this.setChanged();
		this.notifyObservers(gameState);
	}

	public void gameOver() {
		this.currentLevelObject = LevelsManager.getFirstLevel();
		this.bubblesManager = new BubblesManager();
		this.player.resetPlayer();
		this.gameState = GameState.GAMEOVER;
		this.currentLevel = 0;
	}

	public void win() {
		this.gameState = GameState.VICTORY;
		this.player.resetPlayer();

		// Update observers
		this.setChanged();
		this.notifyObservers(this.gameState);
	}

	public void loadNextLevel() {
		if(!this.isNextLevelTimerStarted){
			this.setChanged();
			this.notifyObservers((long) 5000);
			this.isNextLevelTimerStarted = true;
		}
	}

	public void levelUp(int level) {
		switch(level) {
			case 2:
				this.currentLevelObject = LevelsManager.getSecondLevel();
				break;

			case 3:
				this.currentLevelObject = LevelsManager.getThirdLevel();
				break;

			case 4:
				this.currentLevelObject = LevelsManager.getFourthLevel();
				break;

			case 5:
				this.currentLevelObject = LevelsManager.getFifthLevel();
				break;

			case 6:
				this.currentLevelObject = LevelsManager.getSixthLevel();
				break;

			case 7:
				this.currentLevelObject = LevelsManager.getSeventhLevel();
				break;

			case 8:
				this.currentLevelObject = LevelsManager.getEighthLevel();
				break;

			case 9:
				this.win();
				break;
		}

		this.player.moveToStart();
		this.bubblesManager.resetBubblesArray();
		this.bubblesManager.resetElementalBubbles();
		this.isNextLevelTimerStarted = false;
		this.currentLevel = level;
	}

	public void movePlayerLeft() {
		player.setPlayerLeftCommand(true);
	}

	public void movePlayerRight() {
		player.setPlayerRightCommand(true);
	}

	public void movePlayerJump() {
		player.setPlayerJumpCommand(true);
	}

	public void makePlayerShoot() {
		bubblesManager.add(new Bubble(player));
	}

	public void applyPowerUp() {
		switch(currentLevelObject.getPowerUp().getType()) {
			case PowerUpType.HEALTH:
				this.player.setLives(this.player.getLives() + 1);
				break;

			case PowerUpType.BUBBLESPEED:
				this.bubblesManager.bubblesSpeedUp();
				break;

			case PowerUpType.BUBBLERATE:
				this.bubblesManager.bubblesRateUp();
				break;

			case PowerUpType.BUBBLETRAVEL:
				this.bubblesManager.bubblesTravelUp();
				break;

			case PowerUpType.PLAYERSPEED:
				this.player.setMoveSpeed(this.player.getMoveSpeed() + 2);
				break;

			case PowerUpType.RAGEDELAYED:
				this.currentLevelObject.extendEnemyEnragementTime(5000);
				break;

			case PowerUpType.LUCK:
				Bonus.setLucky(true);
				break;

			case PowerUpType.INVULNERABILITY:
				this.player.setInvulnerable(true);
				this.player.setInvulnerabilityStartTime(System.currentTimeMillis());
				break;

			case PowerUpType.STOPENEMIES:
				this.currentLevelObject.setEnemiesFreezed(true);
				break;

			case PowerUpType.ONESHOT:
				this.currentLevelObject.killAllEnemies();
				break;
		}
	}

	public void stopPowerUp() {
		// Stop power-up
		switch(currentLevelObject.getPowerUp().getType()) {
			case PowerUpType.HEALTH:
				break;

			case PowerUpType.BUBBLESPEED:
				this.bubblesManager.bubblesSpeedDown();
				break;

			case PowerUpType.BUBBLERATE:
				this.bubblesManager.bubblesRateDown();
				break;

			case PowerUpType.BUBBLETRAVEL:
				this.bubblesManager.bubblesTravelDown();
				break;

			case PowerUpType.PLAYERSPEED:
				this.player.setMoveSpeed(this.player.getMoveSpeed() - 2);
				break;

			case PowerUpType.RAGEDELAYED:
				break;

			case PowerUpType.LUCK:
				Bonus.setLucky(false);
				break;

			case PowerUpType.INVULNERABILITY:
				break;

			case PowerUpType.STOPENEMIES:
				this.currentLevelObject.setEnemiesFreezed(false);
				break;

			case PowerUpType.ONESHOT:
				break;
		}

		this.currentLevelObject.setPowerup(null);
	}

	// Getter and setter methods -------------------------------------------------

	public Player getPlayer() {
		return player;
	}

	public GameState getGameState() {
		return gameState;
	}

	public Level getCurrentLevelObject() {
		return this.currentLevelObject;
	}

	public BubblesManager getBubblesManager() {
		return this.bubblesManager;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setPlayerNickname(String playerNickname) {
		this.player.setNickname(playerNickname);
	}
}
