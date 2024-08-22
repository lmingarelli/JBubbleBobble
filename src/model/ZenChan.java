package model;

import java.util.Random;

public class ZenChan extends Enemy {
	public ZenChan(int x, int y, boolean isRight) {
		super(x, y, 16, 16, 2, isRight, 55, 2, 1000, 3,
				"assets/sprites/zenchan/zenChanBubble1.png");

		this.leftImagePaths.add("assets/sprites/zenchan/zenChan1L.png");
		this.leftImagePaths.add("assets/sprites/zenchan/zenChan2L.png");
		this.leftImagePaths.add("assets/sprites/zenchan/zenChan3L.png");
		this.leftImagePaths.add("assets/sprites/zenchan/zenChan4L.png");

		this.imagePath = leftImagePaths.get(0);

		this.rightImagePaths.add("assets/sprites/zenchan/zenChan1R.png");
		this.rightImagePaths.add("assets/sprites/zenchan/zenChan2R.png");
		this.rightImagePaths.add("assets/sprites/zenchan/zenChan3R.png");
		this.rightImagePaths.add("assets/sprites/zenchan/zenChan4R.png");

		this.rightImagePath = rightImagePaths.get(0);

		this.enragedLeftImagePaths.add("assets/sprites/zenchan/zenChanAngry1L.png");
		this.enragedLeftImagePaths.add("assets/sprites/zenchan/zenChanAngry2L.png");
		this.enragedLeftImagePaths.add("assets/sprites/zenchan/zenChanAngry3L.png");
		this.enragedLeftImagePaths.add("assets/sprites/zenchan/zenChanAngry4L.png");

		this.enragedRightImagePaths
				.add("assets/sprites/zenchan/zenChanAngry1R.png");
		this.enragedRightImagePaths
				.add("assets/sprites/zenchan/zenChanAngry2R.png");
		this.enragedRightImagePaths
				.add("assets/sprites/zenchan/zenChanAngry3R.png");
		this.enragedRightImagePaths
				.add("assets/sprites/zenchan/zenChanAngry4R.png");
	}

	@Override
	public void updateModel(GameModel game) {
		this.updateCurrentImage(4);

		if(this.hasPopped){
			// Don't move
		}else if(this.isBubblified && !this.isEnraged){
			this.moveLikeABubble();
		}else{
			// Manage jumping
			this.jump(game, false);

			// Random jump
			int rand = new Random().nextInt(1000);
			if(!this.isFalling && !this.isJumping && rand < 10){
				this.jump(game, true);
			}

			// Manage falling
			this.fall(game);

			if(CollisionManager.getIntersectHeightEntityVSBlock(this, null) != 0){
				// If it's inside a block then let's keep moving
				// But if we hit a border, it changes its facing direction
				if(this.isFacingRight){
					// Right
					if(!CollisionManager.isZenchanRightCollisionBorder(this,
							this.moveSpeed, null)){
						this.moveRight();
					}else{
						this.moveLeft();
					}
				}else{
					// Left
					if(!CollisionManager.isZenchanLeftCollisionBorder(this,
							this.moveSpeed, null)){
						this.moveLeft();
					}else{
						this.moveRight();
					}
				}
			}else{
				int startingX = this.x;
				if(this.isFacingRight){
					this.moveRight(game);

					if(this.x == startingX){
						if(this.wallHit == true){
							this.wallHit = false;
							this.moveLeft(game);
							this.jump(game, true);
						}else{
							this.wallHit = true;
							this.moveLeft(game);
							this.isFacingRight = false;
						}
					}
				}else{
					this.moveLeft(game);

					if(this.x == startingX){
						if(wallHit == true){
							wallHit = false;
							this.moveRight(game);
							this.jump(game, true);
						}else{
							wallHit = true;
							this.moveRight(game);
							this.isFacingRight = true;
						}
					}
				}
			}
		}
	}

	@Override
	public void fall(GameModel gameModel) {
		if(!this.isJumping){
			if(!CollisionManager.isPlayerDownCollisionBlock(this, jumpSpeed)){
				if(this.isFalling == false && new Random().nextInt(100) < 10){
					this.jump(gameModel, true);
				}else{
					this.y += jumpSpeed;
					this.isFalling = true;
				}

			}else{
				this.isFalling = false;
			}
		}
	}

	public void moveRight() {
		this.x += moveSpeed;
		this.isFacingRight = true;

	}

	public void moveLeft() {
		this.x -= moveSpeed;
		this.isFacingRight = false;
	}
}
