package model;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends LivingEntities {
	protected boolean isBubblified;
	protected int pointsGiven;
	protected int bubbleTravelSpeed;
	protected int bubbleRaiseSpeed;
	protected boolean isFinalHeight;
	protected final int finalY = 48;
	protected boolean hasPopped;
	protected boolean isEnraged;
	protected boolean wallHit;

	protected static final String popImagePath = "assets/sprites/misc/pon.png";
	protected String bubblifiedImagePath;
	protected ArrayList<String> leftImagePaths = new ArrayList<String>();
	protected ArrayList<String> rightImagePaths = new ArrayList<String>();
	protected ArrayList<String> enragedLeftImagePaths = new ArrayList<String>();
	protected ArrayList<String> enragedRightImagePaths = new ArrayList<String>();
	protected int currentImage;

	protected int bubblifyDuration;
	protected long bubblifyStartTime;
	protected long popTime;
	protected long bubblePoppedDuration;

	public Enemy(
			int x,
			int y,
			int height,
			int width,
			int moveSpeed,
			boolean isFacingRight,
			int jumpHeight,
			int jumpSpeed,
			int pointsGiven,
			int bubbleTravelSpeed,
			String bubblifiedImagePath) {

		super(
				x,
				y,
				height,
				width,
				moveSpeed,
				isFacingRight,
				null,
				null,
				jumpHeight,
				jumpSpeed,
				1);

		this.isBubblified = false;
		this.bubblifyDuration = 15000;
		this.bubbleTravelSpeed = 3;
		this.bubbleRaiseSpeed = 1;
		this.isFinalHeight = false;
		this.bubblePoppedDuration = 1000;
		this.pointsGiven = pointsGiven;
		this.bubbleTravelSpeed = bubbleTravelSpeed;

		this.bubblifiedImagePath = bubblifiedImagePath;
		this.currentImage = 0;
	}

	public void updateModel(GameModel game) {}

	public void bubblify() {
		if(!this.isBubblified){
			this.bubblifyStartTime = System.currentTimeMillis();
			this.isBubblified = true;

			this.imagePath = this.rightImagePath = this.bubblifiedImagePath;
		}
	}

	public void pop() {
		this.setImagePath(popImagePath);
		this.setRightImagePath(popImagePath);
		this.popTime = System.currentTimeMillis();
		this.hasPopped = true;
	}

	public void enrage() {
		this.setImagePath(
				this.enragedLeftImagePaths.get(0),
				this.enragedRightImagePaths.get(0));
		this.isBubblified = false;
		this.moveSpeed = this.moveSpeed + 1;
		this.isEnraged = true;
	}

	public void moveLikeABubble() {
		if(!this.isFinalHeight){
			this.y -= this.bubbleRaiseSpeed;

			if(this.y <= this.finalY){
				this.isFinalHeight = true;
			}
		}else{
			int randomInt = new Random().nextInt(100);

			if(randomInt < 88){ // 88% DO NOTHING
				// Do nothing
			}else if(randomInt < 89){ // 1% LEFT
				if(!CollisionManager.isBubbleLeftCollisionBlock(this,
						this.bubbleTravelSpeed)){
					this.x -= this.bubbleTravelSpeed;
					this.isFacingRight = true;
				}
			}else if(randomInt < 90){ // 1% RIGHT
				if(!CollisionManager.isBubbleRightCollisionBlock(this,
						this.bubbleTravelSpeed)){
					this.x += this.bubbleTravelSpeed;
					this.isFacingRight = true;
				}
			}else if(randomInt < 95){ // 5% DOWN
				if(!CollisionManager.isBubbleDownCollisionBlock(this,
						this.bubbleTravelSpeed)){
					this.y += this.bubbleTravelSpeed;
				}
			}else{ // 5% UP
				if(!CollisionManager.isBubbleUpCollisionBlock(this,
						this.bubbleTravelSpeed)){
					this.y -= this.bubbleTravelSpeed;
				}
			}
		}
	}

	public void updateCurrentImage(int numberOfImages) {
		if(!this.isBubblified && !this.hasPopped){
			if(!this.isEnraged){
				this.imagePath = this.leftImagePaths.get(currentImage / 10);
				this.rightImagePath = this.rightImagePaths.get(currentImage / 10);
			}else{
				this.imagePath = this.enragedLeftImagePaths.get(currentImage / 10);
				this.rightImagePath = this.enragedRightImagePaths
						.get(currentImage / 10);
			}
		}

		this.currentImage = this.currentImage == ((numberOfImages * 10) - 1)
				? 0
				: this.currentImage + 1;
	}

	// Getter and setter methods -------------------------------------------------

	public boolean isBubblified() {
		return this.isBubblified;
	}

	public boolean hasPopped() {
		return this.hasPopped;
	}

	public boolean isEnraged() {
		return this.isEnraged;
	}

	public int getBubblifyDuration() {
		return bubblifyDuration;
	}

	public void setBubblifyDuration(int bubblifyDuration) {
		this.bubblifyDuration = bubblifyDuration;
	}

}
