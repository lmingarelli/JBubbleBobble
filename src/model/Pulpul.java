package model;

import java.util.ArrayList;
import java.util.Random;

public class Pulpul extends Enemy {
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private ArrayList<String> angryImagePaths = new ArrayList<String>();
	private int currentImage;

	public Pulpul(int x, int y) {
		super(x, y, 16, 16, 5, false, 0, 0, 3000, 3,
				"assets/sprites/pulpul/bubblePulpul.png");

		this.imagePaths.add("assets/sprites/pulpul/pulpul1.png");
		this.imagePaths.add("assets/sprites/pulpul/pulpul2.png");
		this.imagePaths.add("assets/sprites/pulpul/pulpul3.png");
		this.imagePaths.add("assets/sprites/pulpul/pulpul4.png");

		this.angryImagePaths.add("assets/sprites/pulpul/angryPulpul1.png");
		this.angryImagePaths.add("assets/sprites/pulpul/angryPulpul2.png");
		this.angryImagePaths.add("assets/sprites/pulpul/angryPulpul3.png");
		this.angryImagePaths.add("assets/sprites/pulpul/angryPulpul4.png");

		this.currentImage = 0;
		this.imagePath = this.imagePaths.get(currentImage);
	}

	@Override
	public void updateModel(GameModel game) {
		this.currentImage = this.currentImage == 3
				? 0
				: this.currentImage + 1;

		if(!this.isBubblified){
			if(!this.isEnraged){
				this.imagePath = this.imagePaths.get(this.currentImage);
			}else{
				this.imagePath = this.angryImagePaths.get(this.currentImage);
			}
		}

		if(this.hasPopped){
			// Don't move
		}else if(this.isBubblified && !this.isEnraged){
			this.moveLikeABubble();
		}else{
			// Move enemy
			int randInt = new Random().nextInt(100);

			if(randInt < 80){ // 40% Do nothing
				// Do nothing
			}else if(randInt < 85){ // 15% Right
				if(!CollisionManager.isEntityRightCollisionBlock(this, this.moveSpeed,
						null)
						|| CollisionManager.getIntersectHeightEntityVSBlock(this,
								null) > 0){
					this.x += this.moveSpeed;
				}
			}else if(randInt < 90){ // 15% Left
				if(!CollisionManager.isEntityLeftCollisionBlock(this, this.moveSpeed,
						null)
						|| CollisionManager.getIntersectHeightEntityVSBlock(this,
								null) > 0){
					this.x -= this.moveSpeed;
				}
			}else if(randInt < 95){ // 15% Up
				if(!CollisionManager.isEntityUpCollisionBlock(this, this.moveSpeed,
						null)
						|| CollisionManager.getIntersectHeightEntityVSBlock(this,
								null) > 0){
					this.y -= this.moveSpeed;
				}
			}else if(randInt < 100){ // 15% Down
				if(!CollisionManager.isEntityDownCollisionBlock(this, this.moveSpeed,
						null)
						|| CollisionManager.getIntersectHeightEntityVSBlock(this,
								null) > 0){
					this.y += this.moveSpeed;
				}
			}
		}

	}

	@Override
	public void bubblify() {
		if(!this.isBubblified){
			this.bubblifyStartTime = System.currentTimeMillis();
			this.isBubblified = true;

			this.imagePath = this.bubblifiedImagePath;
		}
	}

	@Override
	public void enrage() {
		this.currentImage = 0;
		this.imagePath = this.angryImagePaths.get(this.currentImage);
		this.isBubblified = false;
		this.moveSpeed = 5;
		this.isEnraged = true;
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}
}
