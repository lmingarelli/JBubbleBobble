package model;

import java.util.ArrayList;

public class Player extends LivingEntities {
	private boolean playerJumpCommand;
	private boolean playerLeftCommand;
	private boolean playerRightCommand;
	private boolean isInvulnerable;
	private int invulnerabilityTime;
	private long invulnerabilityStartTime;
	private String nickname;
	private int gamesNumber;
	private int winNumber;
	private int loseNumber;
	private int bestScore;
	private ArrayList<String> leftImagePaths = new ArrayList<String>();
	private ArrayList<String> rightImagePaths = new ArrayList<String>();
	private int currentImage;

	public final static int defaultLivesNumber = 4;
	public int score = 0;

	public Player() {
		super(32, 224, 16, 16, 2, true,
				"assets/sprites/bubblun/bubblun1L.png",
				"assets/sprites/bubblun/bubblun1R.png", 55, 2, defaultLivesNumber);

		this.leftImagePaths.add("assets/sprites/bubblun/bubblun1L.png");
		this.leftImagePaths.add("assets/sprites/bubblun/bubblun2L.png");
		this.leftImagePaths.add("assets/sprites/bubblun/bubblun3L.png");
		this.leftImagePaths.add("assets/sprites/bubblun/bubblun4L.png");
		this.leftImagePaths.add("assets/sprites/bubblun/bubblun5L.png");

		this.rightImagePaths.add("assets/sprites/bubblun/bubblun1R.png");
		this.rightImagePaths.add("assets/sprites/bubblun/bubblun2R.png");
		this.rightImagePaths.add("assets/sprites/bubblun/bubblun3R.png");
		this.rightImagePaths.add("assets/sprites/bubblun/bubblun4R.png");
		this.rightImagePaths.add("assets/sprites/bubblun/bubblun5R.png");

		this.currentImage = 0;
		this.imagePath = this.leftImagePaths.get(currentImage);
		this.rightImagePath = this.rightImagePaths.get(currentImage);

		this.playerJumpCommand = false;
		this.playerLeftCommand = false;
		this.playerRightCommand = false;
		this.invulnerabilityTime = 5000;
		this.isInvulnerable = false;
		this.gamesNumber = 0;
		this.winNumber = 0;
		this.loseNumber = 0;
	}

	public void updateModel(GameModel game) {
		if(this.isInvulnerable
				&& System.currentTimeMillis() > invulnerabilityStartTime
						+ invulnerabilityTime){

			this.isInvulnerable = false;
			this.invulnerabilityStartTime = 0;

			// Logging
			System.out.println("Player invulnerability turned off");
		}

		this.jump(game, this.playerJumpCommand);
		this.fall(game);

		if(this.playerRightCommand){
			this.moveRight(game);
			this.playerRightCommand = false;
		}

		if(this.playerLeftCommand){
			this.moveLeft(game);
			this.playerLeftCommand = false;
		}

		this.playerJumpCommand = false;
	}

	@Override
	public void loseLife() {
		if(!this.isInvulnerable){
			this.lives -= 1;

			if(this.isDead()){
				return;
			}

			this.isInvulnerable = true;
			this.invulnerabilityStartTime = System.currentTimeMillis();
			this.moveToStart();

			// Logging
			System.out.println("Player lost a life and is now invulnerable for "
					+ this.invulnerabilityTime + " milliseconds");
		}
	}

	@Override
	protected void moveLeft(GameModel gameModel) {
		if(!CollisionManager.isPlayerLeftCollisionBlock(this,
				moveSpeed, isJumping)){
			this.x -= moveSpeed;
			this.isFacingRight = false;
			this.updateCurrentImage();
		}
	}

	@Override
	protected void moveRight(GameModel gameModel) {
		if(!CollisionManager.isPlayerRightCollisionBlock(this,
				moveSpeed, isJumping)){
			this.x += moveSpeed;
			this.isFacingRight = true;
			this.updateCurrentImage();
		}
	}

	public void updateCurrentImage() {
		this.imagePath = this.leftImagePaths.get(currentImage / 10);
		this.rightImagePath = this.rightImagePaths.get(currentImage / 10);

		this.currentImage = this.currentImage == 49
				? 0
				: this.currentImage + 1;
	}

	public void moveToStart() {
		this.x = 32;
		this.y = 224;
		this.jumpStartY = 0;
		this.isJumping = false;
		this.isFalling = false;
	}

	public void resetPlayer() {
		this.moveToStart();

		// Win and lose counter management
		if(this.lives > 0){
			this.winNumber += 1;
		}else{
			this.loseNumber += 1;
		}
		this.lives = defaultLivesNumber;

		// Best score management
		this.saveBestScore();

		// Setting various command booleans to false
		this.playerJumpCommand = false;
		this.playerLeftCommand = false;
		this.playerRightCommand = false;
		this.isInvulnerable = false;
	}

	public void saveBestScore() {
		this.bestScore = this.score > this.bestScore
				? this.score
				: this.bestScore;
	}

	// Getter and setter methods -----------------------------------------------

	public void setPlayerJumpCommand(boolean playerJumpCommand) {
		this.playerJumpCommand = playerJumpCommand;
	}

	public void setPlayerLeftCommand(boolean playerLeftCommand) {
		this.playerLeftCommand = playerLeftCommand;
	}

	public void setPlayerRightCommand(boolean playerRightCommand) {
		this.playerRightCommand = playerRightCommand;
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setInvulnerabilityStartTime(long invulnerabilityStartTime) {
		this.invulnerabilityStartTime = invulnerabilityStartTime;
	}

	public void setInvulnerable(boolean isInvulnerable) {
		this.isInvulnerable = isInvulnerable;
	}

	public int getGamesNumber() {
		return gamesNumber;
	}

	public void setGamesNumber(int gamesNumber) {
		this.gamesNumber = gamesNumber;
	}

	public int getWinNumber() {
		return winNumber;
	}

	public void setWinNumber(int winNumber) {
		this.winNumber = winNumber;
	}

	public int getLoseNumber() {
		return loseNumber;
	}

	public void setLoseNumber(int loseNumber) {
		this.loseNumber = loseNumber;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
}
