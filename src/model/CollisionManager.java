package model;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class CollisionManager {

	/*****************************************************************************
	 * 
	 * Player colliding methods
	 * 
	 ****************************************************************************/

	public static boolean isPlayerLeftCollisionBlock(LivingEntities player,
			int moveSpeed, boolean isJumping) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle playerBorder = player.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(isJumping && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed, blockBorder.y);

					if(playerBorder.intersects(blockBorder)){
						return true;
					}

				}
			}

		}
		return false;

	}

	public static boolean isPlayerRightCollisionBlock(LivingEntities player,
			int moveSpeed, boolean isJumping) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle playerBorder = player.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(isJumping && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed, blockBorder.y);

					if(playerBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isPlayerUpCollisionBlock(LivingEntities player,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle playerBorder = player.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()
						&& currentBlock.getY() == 0){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y + moveSpeed);

					if(playerBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isPlayerDownCollisionBlock(LivingEntities player,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle playerBorder = player.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y - moveSpeed);

					if(playerBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isPlayerCollisionEnemy(LivingEntities player,
			LivingEntities enemy) {

		return player.getBorders().intersects(enemy.getBorders());
	}

	/*****************************************************************************
	 * 
	 * Bubble colliding methods
	 * 
	 ****************************************************************************/

	public static boolean isBubbleCollisionBlock(GameObject bubble,
			int moveSpeed) {

		if(!isBubbleRightCollisionBlock(bubble, moveSpeed)
				&& !isBubbleLeftCollisionBlock(bubble, moveSpeed)
				&& !isBubbleUpCollisionBlock(bubble, moveSpeed)
				&& !isBubbleDownCollisionBlock(bubble, moveSpeed)){
			return false;
		}
		return true;
	}

	public static boolean isBubbleRightCollisionBlock(GameObject bubble,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle bubbleBorder = bubble.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed, blockBorder.y);

					// If player and current block are colliding
					if(bubbleBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isBubbleLeftCollisionBlock(GameObject bubble,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		// Bubble to block left collision
		Rectangle bubbleBorder = bubble.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed, blockBorder.y);

					// If player and current block are colliding
					if(bubbleBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isBubbleUpCollisionBlock(GameObject bubble,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle bubbleBorder = bubble.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null
						&& !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y + moveSpeed);

					if(bubbleBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isBubbleDownCollisionBlock(GameObject bubble,
			int moveSpeed) {

		Level levelObj = GameModel.getInstance().getCurrentLevelObject();

		Rectangle bubbleBorder = bubble.getBorders();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null
						&& !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y - moveSpeed);

					if(bubbleBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/*****************************************************************************
	 * 
	 * Monsta colliding methods
	 * 
	 ****************************************************************************/

	public static MonstaDirection getUpRightNextDirection(Monsta monsta,
			int moveSpeed, Rectangle rect) {

		MonstaDirection nextDirection = MonstaDirection.UPRIGHT;
		boolean xHit = false;
		boolean yHit = false;
		Rectangle monstaBorder = rect != null ? rect : monsta.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(monsta.isGhosting() && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x,
							blockBorder.y + moveSpeed);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						yHit = true;
					}

					blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed,
							blockBorder.y);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						xHit = true;
					}
				}
			}
		}

		if(xHit && yHit){
			nextDirection = MonstaDirection.DOWNLEFT;
		}else if(xHit){
			nextDirection = MonstaDirection.UPLEFT;
		}else if(yHit){
			nextDirection = MonstaDirection.DOWNRIGHT;
		}

		return nextDirection;
	}

	public static MonstaDirection getUpLeftNextDirection(Monsta monsta,
			int moveSpeed, Rectangle rect) {

		MonstaDirection nextDirection = MonstaDirection.UPLEFT;
		boolean xHit = false;
		boolean yHit = false;
		Rectangle monstaBorder = rect != null ? rect : monsta.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(monsta.isGhosting() && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed,
							blockBorder.y);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						xHit = true;
					}

					blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x,
							blockBorder.y + moveSpeed);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						yHit = true;
					}
				}
			}
		}

		if(xHit && yHit){
			nextDirection = MonstaDirection.DOWNRIGHT;
		}else if(xHit){
			nextDirection = MonstaDirection.UPRIGHT;
		}else if(yHit){
			nextDirection = MonstaDirection.DOWNLEFT;
		}

		return nextDirection;
	}

	public static MonstaDirection getDownLeftNextDirection(Monsta monsta,
			int moveSpeed, Rectangle rect) {

		MonstaDirection nextDirection = MonstaDirection.DOWNLEFT;
		boolean xHit = false;
		boolean yHit = false;
		Rectangle monstaBorder = rect != null ? rect : monsta.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(monsta.isGhosting() && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed,
							blockBorder.y);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						xHit = true;
					}

					blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x,
							blockBorder.y - moveSpeed);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						yHit = true;
					}
				}
			}
		}

		if(xHit && yHit){
			nextDirection = MonstaDirection.UPRIGHT;
		}else if(xHit){
			nextDirection = MonstaDirection.DOWNRIGHT;
		}else if(yHit){
			nextDirection = MonstaDirection.UPLEFT;
		}

		return nextDirection;
	}

	public static MonstaDirection getDownRightNextDirection(Monsta monsta,
			int moveSpeed, Rectangle rect) {

		MonstaDirection nextDirection = MonstaDirection.DOWNRIGHT;
		boolean xHit = false;
		boolean yHit = false;
		Rectangle monstaBorder = rect != null ? rect : monsta.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					if(monsta.isGhosting() && !currentBlock.isBlockLevelBorder()){
						continue;
					}

					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed,
							blockBorder.y);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						xHit = true;
					}

					blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x,
							blockBorder.y - moveSpeed);

					if(monstaBorder.intersects(blockBorder)){
						if(currentBlock.isBlockLevelBorder()){
							monsta.setHasHitBorders(true);
						}

						yHit = true;
					}
				}
			}
		}

		if(xHit && yHit){
			nextDirection = MonstaDirection.UPLEFT;
		}else if(xHit){
			nextDirection = MonstaDirection.DOWNLEFT;
		}else if(yHit){
			nextDirection = MonstaDirection.UPRIGHT;
		}

		return nextDirection;
	}

	/*****************************************************************************
	 * 
	 * Zenchan colliding methods
	 * 
	 ****************************************************************************/

	public static boolean isZenchanRightCollisionBorder(ZenChan zenchan,
			int moveSpeed, Rectangle rect) {

		Rectangle zenchanBorder = rect != null ? rect : zenchan.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed, blockBorder.y);

					if(zenchanBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isZenchanLeftCollisionBorder(ZenChan zenchan,
			int moveSpeed, Rectangle rect) {

		Rectangle zenchanBorder = rect != null ? rect : zenchan.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed, blockBorder.y);

					if(zenchanBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/*****************************************************************************
	 * 
	 * Thunders colliding methods
	 * 
	 ****************************************************************************/

	public static boolean isThunderHittingRightBorder(Thunder thunder) {
		Rectangle thunderBorder = thunder.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - 1, blockBorder.y);

					if(thunderBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isThunderHittingLeftBorder(Thunder thunder) {
		Rectangle thunderBorder = thunder.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()
						&& currentBlock.isBlockLevelBorder()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + 1, blockBorder.y);

					if(thunderBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/*****************************************************************************
	 * 
	 * Misc colliding methods
	 * 
	 ****************************************************************************/

	public static boolean isObjectCollidingWithFlames(GameObject flame,
			ArrayList<Fire> flames) {

		for(int i = 0; i < flames.size(); i++){
			Fire currentFlame = flames.get(i);

			if(flame.getBorders().intersects(currentFlame.getBorders())){
				return true;
			}
		}
		return false;
	}

	/*****************************************************************************
	 * 
	 * Misc colliding methods
	 * 
	 ****************************************************************************/

	public static boolean areEntitiesColliding(GameObject entity1,
			GameObject entity2) {

		return entity1.getBorders().intersects(entity2.getBorders());
	}

	public static boolean isEntityDownCollisionBlock(GameObject entity,
			int moveSpeed, Rectangle rect) {

		Rectangle entityBorder = rect != null ? rect : entity.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y - moveSpeed);

					if(entityBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isEntityRightCollisionBlock(GameObject entity,
			int moveSpeed, Rectangle rect) {

		Rectangle entityBorder = rect != null ? rect : entity.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x - moveSpeed, blockBorder.y);

					if(entityBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isEntityLeftCollisionBlock(GameObject entity,
			int moveSpeed, Rectangle rect) {

		Rectangle entityBorder = rect != null ? rect : entity.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x + moveSpeed, blockBorder.y);

					if(entityBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isEntityUpCollisionBlock(GameObject entity,
			int moveSpeed, Rectangle rect) {

		Rectangle entityBorder = rect != null ? rect : entity.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();
					blockBorder.setLocation(blockBorder.x, blockBorder.y + moveSpeed);

					if(entityBorder.intersects(blockBorder)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int getIntersectHeightEntityVSBlock(GameObject entity,
			Rectangle rect) {

		Rectangle entityBorder = rect != null ? rect : entity.getBorders();
		Level levelObj = GameModel.getInstance().getCurrentLevelObject();
		Block[][] matrix = levelObj.getMatrix();

		for(int y = 0; y < levelObj.gridDimension; y++){
			for(int x = 0; x < levelObj.gridDimension; x++){
				Block currentBlock = matrix[y][x];

				if(currentBlock != null && !currentBlock.isEmpty()){
					Rectangle blockBorder = currentBlock.getBorders();

					if(entityBorder.intersects(blockBorder)){
						return (int) entityBorder.intersection(blockBorder).getHeight();
					}
				}
			}
		}
		return 0;
	}
}
