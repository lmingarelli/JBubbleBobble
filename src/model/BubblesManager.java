package model;

import java.util.ArrayList;
import java.util.Random;

public class BubblesManager {
	private ArrayList<Bubble> bubbles;
	private ElementalBubble fireBubble;
	private ElementalBubble thunderBubble;

	private long lastBubbleShotTime;
	private int recoilTime;

	public BubblesManager() {
		this.recoilTime = 200;
		this.lastBubbleShotTime = 0;
		this.bubbles = new ArrayList<Bubble>();
	}

	public void add(Bubble bubble) {
		if(System.currentTimeMillis() >= lastBubbleShotTime + recoilTime){
			bubbles.add(bubble);
			lastBubbleShotTime = System.currentTimeMillis();
		}
	}

	public void remove(int i) {
		bubbles.remove(i);
	}

	public void updateBubbles(Level currentLevelObject) {
		if(fireBubble == null || thunderBubble == null){
			spawnElementalBubble();
		}

		if(fireBubble != null){
			this.moveElementalBubble(fireBubble, currentLevelObject);
		}

		if(thunderBubble != null){
			this.moveElementalBubble(thunderBubble, currentLevelObject);
		}

		int currentBubbleTravelSpeed = Bubble.getBubbleTravelSpeed();
		int currentBubbleRaiseSpeed = Bubble.getBubbleRaiseSpeed();

		for(int i = 0; i < bubbles.size(); i++){
			Bubble currentBubble = bubbles.get(i);
			long currentTime = System.currentTimeMillis();

			// If has popped
			if(currentBubble.hasPopped()){
				if(currentTime >= currentBubble.getRemoveTime()){
					// Bubble has to disappear
					this.remove(i);
				}
			}else{
				if(currentTime >= currentBubble.getPopTime()){
					currentBubble.pop();
				}else{
					// Moving bubbles
					if(!currentBubble.isOutOfInitalDash()){
						if(currentBubble.isRight()){
							if(!CollisionManager.isBubbleRightCollisionBlock(currentBubble,
									currentBubbleTravelSpeed)){

								currentBubble.moveRight(currentBubbleTravelSpeed);
							}else{
								currentBubble.pop();
							}
						}else{
							if(!CollisionManager.isBubbleLeftCollisionBlock(currentBubble,
									currentBubbleTravelSpeed)){

								currentBubble.moveLeft(currentBubbleTravelSpeed);
							}else{
								currentBubble.pop();
							}
						}
					}else if(!currentBubble.isFinalHeight()){
						if(currentBubble.getY() > currentBubble.getFinalY()){
							currentBubble.moveUp(currentBubbleRaiseSpeed);
						}else{
							currentBubble.setFinalHeight(true);
						}
					}else{
						moveBubbleAfterInitialPart(currentBubble, currentLevelObject);
					}
				}
			}
		}
	}

	public void moveBubbleAfterInitialPart(Bubble currentBubble,
			Level currentLevelObject) {

		int currentBubbleTravelSpeed = Bubble.getBubbleTravelSpeed();
		int randomInt = new Random().nextInt(100);

		if(randomInt < 88){ // 88% DO NOTHING
			// Do nothing
		}else if(randomInt < 89){ // 1% LEFT
			if(!CollisionManager.isBubbleLeftCollisionBlock(currentBubble,
					currentBubbleTravelSpeed)){
				currentBubble.moveLeft(currentBubbleTravelSpeed);
			}
		}else if(randomInt < 90){ // 1% RIGHT
			if(!CollisionManager.isBubbleRightCollisionBlock(currentBubble,
					currentBubbleTravelSpeed)){
				currentBubble.moveRight(currentBubbleTravelSpeed);
			}
		}else if(randomInt < 95){ // 5% DOWN
			if(!CollisionManager.isBubbleDownCollisionBlock(currentBubble,
					currentBubbleTravelSpeed)){
				currentBubble.moveDown(currentBubbleTravelSpeed);
			}
		}else{ // 5% UP
			if(!CollisionManager.isBubbleUpCollisionBlock(currentBubble,
					currentBubbleTravelSpeed)){
				currentBubble.moveUp(currentBubbleTravelSpeed);
			}
		}
	}

	private void spawnElementalBubble() {
		int randInt = new Random().nextInt(10000);

		if(randInt < 10){ // 0,10% Chance of spawning an elemental bubble

			if(fireBubble == null && thunderBubble != null){
				// If the level already has a thunder bubble and doesn't have a fire
				// bubble, then spawn a fire bubble
				this.fireBubble = new ElementalBubble(false);
			}else if(fireBubble != null && thunderBubble == null){
				// If opposite, spawn a thunder bubble
				this.thunderBubble = new ElementalBubble(true);
			}else{
				// If no elemental bubble is present, let's go 50/50 on which spawn
				randInt = new Random().nextInt(100);

				if(randInt < 50){
					this.fireBubble = new ElementalBubble(false);
				}else{
					this.thunderBubble = new ElementalBubble(true);
				}
			}
		}
	}

	private void moveElementalBubble(ElementalBubble bubble,
			Level currentLevelObject) {

		int currentBubbleRaiseSpeed = Bubble.getBubbleRaiseSpeed();
		long currentTime = System.currentTimeMillis();

		// If has popped
		if(bubble.hasPopped()){
			if(currentTime >= bubble.getRemoveTime()){
				// Bubble has to disappear
				if(bubble.isThunder()){
					this.thunderBubble = null;
				}else{
					this.fireBubble = null;
				}
			}
		}else{
			if(!bubble.isFinalHeight()){
				if(bubble.getY() > bubble.getFinalY()){
					bubble.moveUp(currentBubbleRaiseSpeed);
				}else{
					bubble.setFinalHeight(true);
				}
			}else{
				moveBubbleAfterInitialPart(bubble, currentLevelObject);
			}
		}
	}

	// Getter and setter methods -------------------------------------------------

	public void bubblesSpeedUp() {
		Bubble.setBubbleTravelSpeed(Bubble.getBubbleTravelSpeed() + 1);
		Bubble.setBubbleRaiseSpeed(Bubble.getBubbleRaiseSpeed() + 1);
	}

	public void bubblesSpeedDown() {
		Bubble.setBubbleTravelSpeed(Bubble.getBubbleTravelSpeed() - 1);
		Bubble.setBubbleRaiseSpeed(Bubble.getBubbleRaiseSpeed() - 1);
	}

	public void bubblesRateUp() {
		this.recoilTime = 100;
	}

	public void bubblesRateDown() {
		this.recoilTime = 200;
	}

	public void bubblesTravelUp() {
		Bubble.setBubbleTravelDistanceX(Bubble.getBubbleTravelDistanceX() + 20);
	}

	public void bubblesTravelDown() {
		Bubble.setBubbleTravelDistanceX(Bubble.getBubbleTravelDistanceX() - 20);
	}

	public ArrayList<Bubble> getBubbles() {
		return this.bubbles;
	}

	public void removeBubble(Bubble bubble) {
		this.bubbles.remove(bubble);
	}

	public void resetBubblesArray() {
		this.bubbles = new ArrayList<Bubble>();
	}

	public void resetElementalBubbles() {
		this.fireBubble = null;
		this.thunderBubble = null;
	}

	public ElementalBubble getFireBubble() {
		return fireBubble;
	}

	public ElementalBubble getThunderBubble() {
		return thunderBubble;
	}

}
