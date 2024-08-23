package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.GameModel;
import model.Level;
import model.Player;
import view.GameView;

public class GameController implements Observer {
	private static GameController gameController;
	private GameModel gameModel;
	private Player player;
	private GameView gameView;
	private KeyboardInput keyAdapter;
	private PlayersRecord playersRecord;
	private AudioManager audioManager;

	private long powerUpSpawnBaseTime;
	private int powerUpSpawnCooldown;
	private int powerUpDuration;
	private long timerDuration;
	private long startTimer;

	public static GameController getInstance() {
		if(gameController == null) gameController = new GameController();
		return gameController;
	}

	private GameController() {
		// Creating the model
		this.gameModel = GameModel.getInstance();
		this.player = gameModel.getPlayer();

		// Creating the view
		this.gameView = GameView.getInstance();

		// Starting the Observer-Observable mechanism
		this.gameModel.addObserver(this.gameView);
		this.gameModel.addObserver(this);

		// Players record
		this.playersRecord = new PlayersRecord();

		// Keyboard input adapter
		this.keyAdapter = new KeyboardInput();

		// Timers
		this.powerUpSpawnCooldown = new Random().nextInt(30000, 60000);
		this.timerDuration = 0;
		this.startTimer = 0;

		// Audio
		this.audioManager = AudioManager.getInstance();
		this.audioManager.play();
	}

	public void startGame() {
		gameModel.startGame();
		this.powerUpSpawnBaseTime = System.currentTimeMillis();
	}

	public void gameOver() {
		this.keyAdapter.pressedKeys = new ArrayList();
	}

	public void loadPlayer(String playerNickname) {
		gameModel.setPlayerNickname(playerNickname);
		boolean initFileResult = this.playersRecord.initXMLFile(this.player);

		if(!initFileResult){
			boolean isPlayerInRecord = this.playersRecord
					.isPlayerInRecord(this.player);

			if(!isPlayerInRecord){
				this.playersRecord.registerPlayer(this.player);
			}
		}

		gameView.loadPlayerInfoOnPage();

		gameView.loadLeaderboard();
	}

	public void onExit() {
		this.player.saveBestScore();
		this.playersRecord.updatePlayer(this.player);
	}

	public void updateController() {
		manageKeyboardInput();

		// Check power up spawn cooldown
		Level currentLevelObj = gameModel.getCurrentLevelObject();

		if(currentLevelObj.getPowerUp() == null
				&& System.currentTimeMillis() > this.powerUpSpawnBaseTime
						+ this.powerUpSpawnCooldown){

			this.powerUpDuration = gameModel.getCurrentLevelObject().addPowerUp()
					.getDuration();
		}

		// Check if the current power-up has to be stopped
		if(currentLevelObj.getPowerUp() != null
				&& currentLevelObj.getPowerUp().isPickedUp()){
			if((this.powerUpDuration == 0)
					|| (this.powerUpDuration > 0
							&& System.currentTimeMillis() > currentLevelObj.getPowerUp()
									.getUseStartTime() + this.powerUpDuration)){

				gameModel.stopPowerUp();
				this.powerUpSpawnBaseTime = System.currentTimeMillis();
				;
			}
		}

		// Managing timer between all enemies defeated and next level loading
		if(startTimer > 0
				&& timerDuration > 0
				&& System.currentTimeMillis() >= startTimer + timerDuration){

			startTimer = 0;
			timerDuration = 0;
			gameModel.levelUp(gameModel.getCurrentLevel() + 1);
		}
	}

	public void manageKeyboardInput() {
		ArrayList<Integer> pressedKeys = this.keyAdapter.pressedKeys;

		for(int i = 0; i < pressedKeys.size(); i++){
			switch(pressedKeys.get(i)) {
				case KeyEvent.VK_LEFT:
					gameModel.movePlayerLeft();
					break;

				case KeyEvent.VK_RIGHT:
					gameModel.movePlayerRight();
					break;

				case KeyEvent.VK_X:
					gameModel.movePlayerJump();
					break;

				case KeyEvent.VK_Z:
					gameModel.makePlayerShoot();
					break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GameModel && arg instanceof Long){
			this.startTimer = System.currentTimeMillis();
			this.timerDuration = (long) arg;
		}
	}

	// Getter and setter methods -------------------------------------------------

	public KeyAdapter getKeyAdapter() {
		return this.keyAdapter;
	}

	// Private class to handle keyboard input ------------------------------------

	private class KeyboardInput extends KeyAdapter {
		public ArrayList<Integer> pressedKeys = new ArrayList();

		@Override
		public void keyPressed(KeyEvent e) {
			if(!pressedKeys.contains(e.getKeyCode())){
				pressedKeys.add(e.getKeyCode());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(pressedKeys.contains(e.getKeyCode())){
				pressedKeys.remove(pressedKeys.indexOf(e.getKeyCode()));
			}
		}
	}

	public PlayersRecord getPlayersRecord() {
		return this.playersRecord;
	}
}
