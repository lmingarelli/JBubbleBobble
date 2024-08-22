package model;

import java.awt.Rectangle;

public class Fire extends GameObject {
	private long startTime;
	private boolean isFirstFlame;

	public static final int lifeSpan = 1500;
	private static final String imagePath = "assets/sprites/misc/fire.png";

	public Fire(ElementalBubble fireBubble) {
		super(fireBubble.getX(), fireBubble.getY(), 16, 8, imagePath);

		this.startTime = 0;
		this.isFirstFlame = true;
	}

	public Fire(int x, int y, boolean spawnRight) {
		super(x, y, 16, 8, imagePath);

		this.startTime = System.currentTimeMillis();
		this.isFirstFlame = false;
	}

	public boolean hasToFall() {
		Rectangle fireRect = new Rectangle(this.x, this.y, this.width, this.height);
		return CollisionManager.getIntersectHeightEntityVSBlock(null,
				fireRect) != 0
				|| !CollisionManager.isEntityDownCollisionBlock(null, 1, fireRect);
	}

	public void startTime() {
		this.startTime = System.currentTimeMillis();
	}

	public void moveDown() {
		this.y += 1;
	}

	// Getter and setter methods -------------------------------------------------

	public long getStartTime() {
		return startTime;
	}

	public boolean isFirstFlame() {
		return isFirstFlame;
	}

}
