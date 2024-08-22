package model;

public class Bubble extends GameObject {
	protected boolean isRight;
	protected int startX;
	protected int finalX;
	protected final int finalY = 48;
	protected boolean isOutOfInitalDash;
	protected boolean isFinalHeight;
	protected long popTime;
	protected long removeTime;
	protected int lifeSpan = 10000;
	protected final int poppedImageDuration = 1000;
	protected final long bornTime;
	protected final String popImagePath = "assets/sprites/misc/bubblePop.png";
	protected boolean hasPopped;

	protected static int bubbleTravelSpeed = 3;
	protected static int bubbleRaiseSpeed = 1;
	protected static int bubbleTravelDistanceX = 40;

	public Bubble(Player player) {
		super(0,
				player.getY(),
				16,
				16,
				"assets/sprites/misc/image_263.png");

		this.isOutOfInitalDash = false;
		this.bornTime = System.currentTimeMillis();
		this.popTime = this.bornTime + lifeSpan;
		this.isRight = player.isFacingRight();
		this.isFinalHeight = this.y <= finalY;
		this.hasPopped = false;

		if(this.isRight){
			this.x = player.getX() + player.getWidth();

			if(this.x > 209){
				this.x = 208;
			}

			this.startX = this.x;
			this.finalX = this.startX + bubbleTravelDistanceX;
		}else{
			this.x = player.getX() - player.getWidth();

			if(this.x < 31){
				this.x = 32;
			}

			this.startX = this.x;
			this.finalX = this.startX - bubbleTravelDistanceX;
		}
	}

	public Bubble() {
		super(0,
				0,
				16,
				16,
				"assets/sprites/misc/image_263.png");

		this.isOutOfInitalDash = false;
		this.bornTime = System.currentTimeMillis();
		this.popTime = this.bornTime + lifeSpan;
		this.isFinalHeight = this.y <= finalY;
		this.hasPopped = false;
	}

	public void moveRight(int speed) {
		if(!isOutOfInitalDash){
			if(this.x + speed <= this.finalX){
				this.x += speed;
				this.isRight = true;
			}else{
				isOutOfInitalDash = true;
			}
		}else{
			this.x += speed;
			this.isRight = true;
		}
	}

	public void moveLeft(int speed) {
		if(!isOutOfInitalDash){
			if(this.x - speed >= this.finalX){
				this.x -= speed;
				this.isRight = false;
			}else{
				isOutOfInitalDash = true;
			}
		}else{
			this.x -= speed;
			this.isRight = false;
		}
	}

	public void moveUp(int speed) {
		this.y -= speed;
	}

	public void moveDown(int speed) {
		this.y += speed;
	}

	public void pop() {
		if(this.imagePath != popImagePath){
			this.setImagePath(popImagePath);
		}
		this.hasPopped = true;
		this.removeTime = System.currentTimeMillis() + this.poppedImageDuration;
	}

	// Getter and setter methods -------------------------------------------------

	public boolean isRight() {
		return this.isRight;
	}

	public boolean isOutOfInitalDash() {
		return this.isOutOfInitalDash;
	}

	public long getPopTime() {
		return this.popTime;
	}

	public long getRemoveTime() {
		return this.removeTime;
	}

	public boolean isFinalHeight() {
		return isFinalHeight;
	}

	public void setFinalHeight(boolean isFinalHeight) {
		this.isFinalHeight = isFinalHeight;
	}

	public int getFinalY() {
		return finalY;
	}

	public boolean hasPopped() {
		return hasPopped;
	}

	public long getBornTime() {
		return bornTime;
	}

	public static int getBubbleTravelSpeed() {
		return Bubble.bubbleTravelSpeed;
	}

	public static void setBubbleTravelSpeed(int bubbleTravelSpeed) {
		Bubble.bubbleTravelSpeed = bubbleTravelSpeed;
	}

	public static int getBubbleRaiseSpeed() {
		return bubbleRaiseSpeed;
	}

	public static void setBubbleRaiseSpeed(int bubbleRaiseSpeed) {
		Bubble.bubbleRaiseSpeed = bubbleRaiseSpeed;
	}

	public static void setBubbleTravelDistanceX(int bubbleTravelDistanceX) {
		Bubble.bubbleTravelDistanceX = bubbleTravelDistanceX;
	}

	public static int getBubbleTravelDistanceX() {
		return Bubble.bubbleTravelDistanceX;
	}
}
