package model;

public class Thunder extends GameObject {
	private boolean isRight;

	private static final String imagePath = "assets/sprites/misc/thunder.png";

	public Thunder(ElementalBubble thunderBubble, boolean isRight) {
		super(thunderBubble.getX(), thunderBubble.getY(), 16, 16, imagePath);

		this.x = isRight
				? thunderBubble.getX() + this.width
				: thunderBubble.getX() - this.width;
	}

	public boolean tryMoveRight() {
		boolean isMovePossible = true;

		if(CollisionManager.isThunderHittingRightBorder(this)){
			isMovePossible = false;
		}else{
			this.x += 1;
		}

		return isMovePossible;
	}

	public boolean tryMoveLeft() {
		boolean isMovePossible = true;

		if(CollisionManager.isThunderHittingLeftBorder(this)){
			isMovePossible = false;
		}else{
			this.x -= 1;
		}

		return isMovePossible;
	}

}
