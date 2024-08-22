package model;

import java.util.Random;

public class ElementalBubble extends Bubble {
	private static final String fireBubbleImagePath = "assets/sprites/bubbles/fireBubble.png";
	private static final String thunderBubbleImagePath = "assets/sprites/bubbles/thunderBubble.png";
	private boolean isThunder;
	private boolean isFire;

	public ElementalBubble(boolean isThunder) {
		super();

		this.y = 224;
		this.isThunder = isThunder;
		this.isFire = !isThunder;
		this.isFinalHeight = false;
		this.popTime = 0;

		// Randomizing the X
		int randX;
		do{
			randX = new Random().nextInt(12);
			randX = randX * 16;
		}while (CollisionManager.isBubbleCollisionBlock(
				new GameObject(randX, this.y, 16, 16, null), 0));

		this.x = randX;

		// Setting the image path
		this.imagePath = isThunder ? thunderBubbleImagePath : fireBubbleImagePath;
	}

	// Getter and setter methods -------------------------------------------------

	public boolean isThunder() {
		return isThunder;
	}
}
