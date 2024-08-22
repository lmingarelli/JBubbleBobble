package model;

public class LivingEntities extends GameObject {
	protected boolean isJumping;
	protected boolean isFalling;
	protected int moveSpeed;
	protected boolean isFacingRight;
	protected int jumpStartY;
	protected int jumpHeight;
	protected int jumpSpeed;
	protected String rightImagePath;
	protected int lives;

	public LivingEntities(
			int x,
			int y,
			int height,
			int width,
			int moveSpeed,
			boolean isFacingRight,
			String leftImagePath,
			String rightImagePath,
			int jumpHeight,
			int jumpSpeed,
			int lives) {

		super(x, y, height, width, leftImagePath);

		this.isJumping = false;
		this.isFalling = false;
		this.moveSpeed = moveSpeed;
		this.isFacingRight = isFacingRight;
		this.jumpHeight = jumpHeight;
		this.jumpSpeed = jumpSpeed;
		this.rightImagePath = rightImagePath;
		this.lives = lives;
	}

	protected void moveLeft(GameModel gameModel) {
		if(!CollisionManager.isPlayerLeftCollisionBlock(this,
				moveSpeed, isJumping)){
			this.x -= moveSpeed;
			this.isFacingRight = false;
		}
	}

	protected void moveRight(GameModel gameModel) {
		if(!CollisionManager.isPlayerRightCollisionBlock(this,
				moveSpeed, isJumping)){
			this.x += moveSpeed;
			this.isFacingRight = true;
		}
	}

	protected void jump(GameModel gameModel, boolean isCommand) {
		// If we are already jumping
		if(this.isJumping){
			// If currentJump is not at its max height and if there is room to jump
			if(this.y - jumpSpeed >= jumpStartY - jumpHeight
					&& !CollisionManager.isPlayerUpCollisionBlock(this,
							jumpSpeed)){
				this.y -= jumpSpeed;
			}else{
				this.isJumping = false;
				this.isFalling = true;
				this.fall(gameModel);
			}
		}else if(isCommand && this.isFalling == false && this.isJumping == false){
			// If is a command and the player is not falling or jumping, then jump
			this.isJumping = true;
			this.jumpStartY = this.y;
			this.jump(gameModel, false);
		}
	}

	protected void fall(GameModel gameModel) {
		if(!this.isJumping){
			if(CollisionManager.getIntersectHeightEntityVSBlock(this, null) > 0
					|| !CollisionManager.isPlayerDownCollisionBlock(this,
							jumpSpeed)){
				this.y += jumpSpeed;
				this.isFalling = true;
			}else{
				this.isFalling = false;
			}
		}
	}

	public void loseLife() {
		this.lives -= 1;
	}

	// Getter and setter methods -----------------------------------------------

	@Override
	public String getImagePath() {
		if(this.isFacingRight){
			return rightImagePath;
		}else{
			return imagePath;
		}
	}

	public String getSingleImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePathL, String imagePathR) {
		this.imagePath = imagePathL;

		this.rightImagePath = imagePathR != null ? imagePathR : imagePathL;
	}

	public boolean isFacingRight() {
		return this.isFacingRight;
	}

	public boolean isDead() {
		return this.lives <= 0;
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public void setRightImagePath(String rightImagePath) {
		this.rightImagePath = rightImagePath;
	}

}
