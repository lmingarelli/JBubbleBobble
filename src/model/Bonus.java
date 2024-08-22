package model;

import java.awt.Rectangle;
import java.util.Random;

public class Bonus extends GameObject {
	private int points;
	private boolean hasToFall;

	private static boolean isLucky;

	public Bonus(int x, int y) {
		super(x, y, 16, 16, null);

		int randomInt = new Random().nextInt(100);
		String imagePath = "assets/sprites/items/points/sushi500.png";
		int points = 500;

		if(Bonus.isLucky){
			if(randomInt < 5){ // 5%
				// Keep standard
			}else if(randomInt < 10){ // 5%
				imagePath = "assets/sprites/items/points/onion500.png";
			}else if(randomInt < 15){ // 5%
				imagePath = "assets/sprites/items/points/pretzel1000.png";
				points = 1000;
			}else if(randomInt < 20){ // 5%
				imagePath = "assets/sprites/items/points/hamburger1500.png";
				points = 1500;
			}else if(randomInt < 35){ // 15%
				imagePath = "assets/sprites/items/points/cake3000.png";
				points = 3000;
			}else if(randomInt < 50){ // 15%
				imagePath = "assets/sprites/items/points/diamond5000.png";
				points = 5000;
			}else if(randomInt < 65){ // 15%
				imagePath = "assets/sprites/items/points/gem7000.png";
				points = 7000;
			}else if(randomInt < 80){ // 15%
				imagePath = "assets/sprites/items/points/purpleNecklace8500.png";
				points = 8500;
			}else if(randomInt < 100){ // 20%
				imagePath = "assets/sprites/items/points/treasure10000.png";
				points = 1000;
			}
		}else{
			if(randomInt < 30){ // 30%
				// Keep standard
			}else if(randomInt < 60){ // 30%
				imagePath = "assets/sprites/items/points/onion500.png";
			}else if(randomInt < 75){ // 15%
				imagePath = "assets/sprites/items/points/pretzel1000.png";
				points = 1000;
			}else if(randomInt < 85){ // 10%
				imagePath = "assets/sprites/items/points/hamburger1500.png";
				points = 1500;
			}else if(randomInt < 90){ // 5%
				imagePath = "assets/sprites/items/points/cake3000.png";
				points = 3000;
			}else if(randomInt < 94){ // 4%
				imagePath = "assets/sprites/items/points/diamond5000.png";
				points = 5000;
			}else if(randomInt < 97){ // 3%
				imagePath = "assets/sprites/items/points/gem7000.png";
				points = 7000;
			}else if(randomInt < 99){ // 2%
				imagePath = "assets/sprites/items/points/purpleNecklace8500.png";
				points = 8500;
			}else if(randomInt < 100){ // 1%
				imagePath = "assets/sprites/items/points/treasure10000.png";
				points = 1000;
			}
		}

		this.imagePath = imagePath;
		this.points = points;

		Rectangle bonusRect = new Rectangle(x, y, 16, 16);
		this.hasToFall = CollisionManager.getIntersectHeightEntityVSBlock(null,
				bonusRect) != 0
				|| !CollisionManager.isEntityDownCollisionBlock(null, 1, bonusRect);
	}

	public void dropDownBonus() {
		// Putting the bonus on the floor
		Rectangle bonusRect = new Rectangle(this.x, this.y, 16, 16);

		// If the bonus does spawn inside a block, let the block out from the bottom
		if(CollisionManager.getIntersectHeightEntityVSBlock(this, null) != 0
				|| !CollisionManager.isEntityDownCollisionBlock(null, 1, bonusRect)){
			this.y += 1;
		}else{
			this.hasToFall = false;
		}
	}

	// Getter and setter methods -------------------------------------------------

	public int getPoints() {
		return points;
	}

	public boolean hasToFall() {
		return this.hasToFall;
	}

	public static void setLucky(boolean isLucky) {
		Bonus.isLucky = isLucky;
	}
}