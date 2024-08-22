package model;

import java.awt.Rectangle;
import java.util.Random;

public class PowerUp extends GameObject {
	public enum PowerUpType {
		PLAYERSPEED,
		ONESHOT,
		RAGEDELAYED,
		STOPENEMIES,
		HEALTH,
		LUCK,
		BUBBLESPEED,
		BUBBLETRAVEL,
		BUBBLERATE,
		INVULNERABILITY,
	}

	private boolean hasToFall;
	private PowerUpType type;
	private int duration;
	private long useStartTime;
	private boolean hasToBeShowed;
	private boolean pickedUp;

	public PowerUp() {
		super(0, 0, 16, 16, null);

		this.duration = 0;
		this.hasToBeShowed = true;
		this.pickedUp = false;

		int randomInt = new Random().nextInt(100);
		String imagePath = "assets/sprites/items/hearth.png";

		if(randomInt < 10){ // 10%
			// Keep standard
			this.type = PowerUpType.HEALTH;
		}else if(randomInt < 20){ // 10%
			imagePath = "assets/sprites/items/blueCandy.png";
			this.type = PowerUpType.BUBBLESPEED;
			this.duration = 5000;
		}else if(randomInt < 30){ // 10%
			imagePath = "assets/sprites/items/yellowCandy.png";
			this.type = PowerUpType.BUBBLERATE;
			this.duration = 5000;
		}else if(randomInt < 40){ // 10%
			imagePath = "assets/sprites/items/pinkCandy.png";
			this.type = PowerUpType.BUBBLETRAVEL;
			this.duration = 5000;
		}else if(randomInt < 50){ // 10%
			imagePath = "assets/sprites/items/shoe.png";
			this.type = PowerUpType.PLAYERSPEED;
			this.duration = 8000;
		}else if(randomInt < 60){ // 10%
			imagePath = "assets/sprites/items/tome.png";
			this.type = PowerUpType.RAGEDELAYED;
		}else if(randomInt < 70){ // 10%
			imagePath = "assets/sprites/items/cross.png";
			this.type = PowerUpType.LUCK;
			this.duration = 10000;
		}else if(randomInt < 80){ // 10%
			imagePath = "assets/sprites/items/monsterHearth.png";
			this.type = PowerUpType.INVULNERABILITY;
			this.duration = 5000;
		}else if(randomInt < 90){ // 10%
			imagePath = "assets/sprites/items/clock.png";
			this.type = PowerUpType.STOPENEMIES;
			this.duration = 2000;
		}else if(randomInt < 100){ // 10%
			imagePath = "assets/sprites/items/bomb.png";
			this.type = PowerUpType.ONESHOT;
		}

		this.imagePath = imagePath;

		// Randomizing X
		do{
			randomInt = new Random().nextInt(16);
		}while (randomInt < 2 || randomInt > 13);

		this.x = randomInt * 16;

		// Randomizing Y
		do{
			randomInt = new Random().nextInt(16);
		}while (randomInt < 2 || randomInt > 13);

		this.y = randomInt * 16;

		// If is inside of a block or if it's not on top of a block, then has to
		// fall
		Rectangle rect = new Rectangle(this.x, this.y, 16, 16);
		this.hasToFall = CollisionManager.getIntersectHeightEntityVSBlock(null,
				rect) != 0
				|| !CollisionManager.isEntityDownCollisionBlock(null, 1, rect);
	}

	public void dropDownPowerUp() {
		// Putting the bonus on the floor
		Rectangle rect = new Rectangle(this.x, this.y, 16, 16);

		// If the bonus does spawn inside a block, let the block out from the bottom
		if(CollisionManager.getIntersectHeightEntityVSBlock(this, null) != 0
				|| !CollisionManager.isEntityDownCollisionBlock(null, 1, rect)){
			this.y += 1;
		}else{
			this.hasToFall = false;
		}
	}

	// Getter and setter methods -------------------------------------------------

	public boolean hasToFall() {
		return this.hasToFall;
	}

	public void setHasToFall(boolean hasToFall) {
		this.hasToFall = hasToFall;
	}

	public PowerUpType getType() {
		return type;
	}

	public void setType(PowerUpType type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isHasToFall() {
		return hasToFall;
	}

	public boolean isHasToBeShowed() {
		return hasToBeShowed;
	}

	public void setHasToBeShowed(boolean hasToBeShowed) {
		this.hasToBeShowed = hasToBeShowed;
	}

	public long getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(long powerUpUseStartTime) {
		this.useStartTime = powerUpUseStartTime;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
}
