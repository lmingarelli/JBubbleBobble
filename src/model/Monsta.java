package model;

import java.util.Random;

public class Monsta extends Enemy {
	private MonstaDirection currentDirection;
	private boolean hasHitBorders;
	private boolean isGhosting;

	public Monsta(int x, int y, boolean isRight) {
		super(x, y, 16, 16, 2, isRight, 0, 0, 2000, 3,
				"assets/sprites/monsta/monstaBubble.png");

		this.currentDirection = isRight ? MonstaDirection.UPRIGHT
				: MonstaDirection.UPLEFT;
		this.isGhosting = false;

		this.leftImagePaths.add("assets/sprites/monsta/monsta1L.png");
		this.leftImagePaths.add("assets/sprites/monsta/monsta2L.png");

		this.imagePath = leftImagePaths.get(0);

		this.rightImagePaths.add("assets/sprites/monsta/monsta1R.png");
		this.rightImagePaths.add("assets/sprites/monsta/monsta2R.png");

		this.rightImagePath = rightImagePaths.get(0);

		this.enragedLeftImagePaths.add("assets/sprites/monsta/angryMonsta1L.png");
		this.enragedLeftImagePaths.add("assets/sprites/monsta/angryMonsta1L.png");

		this.enragedRightImagePaths.add("assets/sprites/monsta/angryMonsta1R.png");
		this.enragedRightImagePaths.add("assets/sprites/monsta/angryMonsta2R.png");
	}

	@Override
	public void updateModel(GameModel game) {
		this.updateCurrentImage(2);

		if(this.hasPopped){
			// Don't move
		}else if(this.isBubblified && !this.isEnraged){
			this.moveLikeABubble();
		}else{
			// Move enemy
			MonstaDirection nextDirection = this.currentDirection;

			if(this.isGhosting){
				nextDirection = moveGhostMode(nextDirection);
			}else{
				nextDirection = moveMonsta(nextDirection);
			}

			switch(nextDirection) {
				case MonstaDirection.UPRIGHT:
					this.x += this.moveSpeed;
					this.y -= this.moveSpeed;
					this.currentDirection = MonstaDirection.UPRIGHT;
					this.isFacingRight = true;
					break;

				case MonstaDirection.UPLEFT:
					this.x -= this.moveSpeed;
					this.y -= this.moveSpeed;
					this.currentDirection = MonstaDirection.UPLEFT;
					this.isFacingRight = false;
					break;

				case MonstaDirection.DOWNRIGHT:
					this.x += this.moveSpeed;
					this.y += this.moveSpeed;
					this.currentDirection = MonstaDirection.DOWNRIGHT;
					this.isFacingRight = true;
					break;

				case MonstaDirection.DOWNLEFT:
					this.x -= this.moveSpeed;
					this.y += this.moveSpeed;
					this.currentDirection = MonstaDirection.DOWNLEFT;
					this.isFacingRight = false;
					break;
			}
		}
	}

	public MonstaDirection moveGhostMode(MonstaDirection nextDirection) {
		switch(this.currentDirection) {
			case MonstaDirection.UPRIGHT:
				nextDirection = CollisionManager.getUpRightNextDirection(this,
						this.moveSpeed, null);
				break;

			case MonstaDirection.UPLEFT:
				nextDirection = CollisionManager.getUpLeftNextDirection(this,
						this.moveSpeed, null);
				break;
			case MonstaDirection.DOWNRIGHT:
				nextDirection = CollisionManager.getDownRightNextDirection(this,
						this.moveSpeed, null);
				break;
			case MonstaDirection.DOWNLEFT:
				nextDirection = CollisionManager.getDownLeftNextDirection(this,
						this.moveSpeed, null);
				break;
		}

		if(CollisionManager.getIntersectHeightEntityVSBlock(this, null) == 0){
			this.isGhosting = false;
		}

		return nextDirection;
	}

	public MonstaDirection moveMonsta(MonstaDirection nextDirection) {
		switch(this.currentDirection) {
			case MonstaDirection.UPRIGHT:
				nextDirection = CollisionManager
						.getUpRightNextDirection(this, this.moveSpeed,
								null);
				break;

			case MonstaDirection.UPLEFT:
				nextDirection = CollisionManager
						.getUpLeftNextDirection(this, this.moveSpeed,
								null);
				break;
			case MonstaDirection.DOWNRIGHT:
				nextDirection = CollisionManager
						.getDownRightNextDirection(this, this.moveSpeed,
								null);
				break;
			case MonstaDirection.DOWNLEFT:
				nextDirection = CollisionManager
						.getDownLeftNextDirection(this, this.moveSpeed,
								null);
				break;
		}

		if(this.currentDirection != nextDirection){
			int randInt = new Random().nextInt(100);

			if(randInt < 10 && !this.hasHitBorders){
				nextDirection = this.currentDirection;
				this.isGhosting = true;
			}

			this.hasHitBorders = false;
		}

		return nextDirection;
	}

	public void setHasHitBorders(boolean hasHitBorders) {
		this.hasHitBorders = hasHitBorders;
	}

	public boolean isGhosting() {
		return isGhosting;
	}

	public void setGhosting(boolean isGhosting) {
		this.isGhosting = isGhosting;
	}
}
