package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Level extends Grid {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	private ArrayList<Fire> flames = new ArrayList<Fire>();
	private PowerUp powerup;
	private Player player;
	private GameModel gameModel;
	private boolean enemiesFreezed;
	private boolean stopSpreadFlames;
	private Thunder rightThunder;
	private Thunder leftThunder;

	private static int mostLeftFlameX;
	private static int mostRightFlameX;
	private static int maxFlame = 10;

	public Level(String blockImagePath) {
		super(blockImagePath);

		this.gameModel = GameModel.getInstance();
		this.player = this.gameModel.getPlayer();
		mostLeftFlameX = 0;
		mostRightFlameX = 0;
	}

	public void updateModel() {
		checkCollisionPlayerVSItems();
		moveItems();

		// If all enemies died move to next level
		if(enemies.size() == 0){
			gameModel.loadNextLevel();
			return;
		}

		moveEnemies();
		checkCollisionPlayerVSBubbles();
		checkCollisionPlayerVSEnemies();
		checkCollisionEnemyVSBubble();
		checkCollisionPlayerVSElementalBubble();
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public void moveEnemies() {
		if(this.enemiesFreezed){
			return;
		}

		for(int i = 0; i < enemies.size(); i++){
			Enemy currentEnemy = enemies.get(i);

			if(currentEnemy.isBubblified()
					&& !currentEnemy.isEnraged()
					&& System.currentTimeMillis() >= currentEnemy.bubblifyStartTime
							+ currentEnemy.bubblifyDuration){

				currentEnemy.enrage();
			}else if(currentEnemy.hasPopped()
					&& System.currentTimeMillis() >= currentEnemy.popTime
							+ currentEnemy.bubblePoppedDuration){

				enemies.remove(i);
				this.player.score += currentEnemy.pointsGiven;
				addBonus(currentEnemy);
			}else{
				currentEnemy.updateModel(gameModel);
			}
		}
	}

	public void checkCollisionPlayerVSEnemies() {
		if(!this.player.isInvulnerable()){
			enemies.stream()
					.filter(enemy -> !enemy.isBubblified
							&& CollisionManager.isPlayerCollisionEnemy(player, enemy))
					.forEach(enemyHittingPlayer -> {
						this.player.loseLife();

						// Logging
						System.out.println("Player hit by an enemy");
					});
		}
	}

	public void checkCollisionEnemyVSBubble() {
		List<Bubble> unpoppedBubbles = gameModel.getBubblesManager().getBubbles()
				.stream()
				.filter(bubble -> !bubble.hasPopped())
				.collect(Collectors.toList());

		this.enemies.stream()
				.filter(currentEnemy -> !currentEnemy.isBubblified)
				.forEach(enemy -> {
					unpoppedBubbles.forEach(bubble -> {
						if(CollisionManager.areEntitiesColliding(enemy, bubble)){
							gameModel.getBubblesManager().removeBubble(bubble);

							if(enemy.isEnraged()){
								enemy.pop();
							}else{
								enemy.bubblify();
							}
						}
					});
				});

		// Checking enemies vs flames
		for(int i = 0; i < enemies.size(); i++){
			Enemy currentEnemy = enemies.get(i);

			for(int j = 0; j < flames.size(); j++){
				Fire currentFlame = flames.get(j);

				if(CollisionManager.areEntitiesColliding(currentEnemy, currentFlame)){
					currentEnemy.pop();
					break;
				}
			}
		}

		// Checking enemies vs thunders
		for(int i = 0; i < enemies.size(); i++){
			Enemy currentEnemy = enemies.get(i);

			if(this.rightThunder != null
					&& CollisionManager.areEntitiesColliding(currentEnemy, rightThunder)){
				enemies.remove(i);
				break;
			}

			if(this.leftThunder != null
					&& CollisionManager.areEntitiesColliding(currentEnemy, leftThunder)){
				enemies.remove(i);
				break;
			}
		}
	}

	public void checkCollisionPlayerVSBubbles() {
		// Checking bubbles
		ArrayList<Bubble> bubbles = gameModel.getBubblesManager().getBubbles();
		bubbles.stream()
				.filter(bubble -> !bubble.hasPopped()
						&& System.currentTimeMillis() >= bubble.getBornTime() + 500
						&& CollisionManager.areEntitiesColliding(player,
								bubble))
				.forEach(bubbleToPop -> {
					for(int j = 0; j < bubbles.size(); j++){
						Bubble checkBubble = bubbles.get(j);

						if(!checkBubble.hasPopped()
								&& CollisionManager.areEntitiesColliding(checkBubble,
										bubbleToPop)){
							checkBubble.pop();
						}
					}

					bubbleToPop.pop();
				});

		// Checking bubblified enemies
		enemies.stream()
				.filter(enemy -> enemy.isBubblified()
						&& !enemy.hasPopped()
						&& CollisionManager.areEntitiesColliding(player,
								enemy))
				.forEach(enemyToPop -> {
					for(int j = 0; j < enemies.size(); j++){
						Enemy checkEnemyBubbles = enemies.get(j);

						if(!checkEnemyBubbles.equals(enemyToPop)
								&& checkEnemyBubbles.isBubblified()
								&& !enemyToPop.hasPopped()
								&& CollisionManager.areEntitiesColliding(checkEnemyBubbles,
										enemyToPop)){
							checkEnemyBubbles.pop();
						}
					}

					enemyToPop.pop();
				});
	}

	public void checkCollisionPlayerVSElementalBubble() {
		ElementalBubble fireBubble = gameModel.getBubblesManager().getFireBubble();
		ElementalBubble thunderBubble = gameModel.getBubblesManager()
				.getThunderBubble();

		if(fireBubble != null
				&& !fireBubble.hasPopped()
				&& CollisionManager.areEntitiesColliding(player,
						fireBubble)){

			// Player and fire bubble collision
			fireBubble.pop();
			this.flames.add(new Fire(fireBubble));
			mostLeftFlameX = fireBubble.getX() - 8;
			mostRightFlameX = fireBubble.getX() + 8;
		}

		if(thunderBubble != null
				&& !thunderBubble.hasPopped()
				&& CollisionManager.areEntitiesColliding(player,
						thunderBubble)){

			// Player and thunder bubble collision
			thunderBubble.pop();
			this.rightThunder = new Thunder(thunderBubble, true);
			this.leftThunder = new Thunder(thunderBubble, false);
		}
	}

	public void addBonus(Enemy currentEnemy) {
		bonuses.add(new Bonus(currentEnemy.getX(), currentEnemy.getY()));
	}

	public PowerUp addPowerUp() {
		this.powerup = new PowerUp();
		return this.powerup;
	}

	public void checkCollisionPlayerVSItems() {
		// Bonuses
		for(int i = 0; i < bonuses.size(); i++){
			Bonus currentBonus = bonuses.get(i);

			if(CollisionManager.areEntitiesColliding(this.player, currentBonus)){
				this.player.score += currentBonus.getPoints();
				bonuses.remove(i);
			}
		}

		// Player power-up
		if(this.powerup != null
				&& !this.powerup.isPickedUp()
				&& CollisionManager.areEntitiesColliding(this.player, this.powerup)){

			this.gameModel.applyPowerUp();

			this.powerup.setPickedUp(true);
			this.powerup.setHasToBeShowed(false);
			this.powerup.setUseStartTime(System.currentTimeMillis());
		}
	}

	public void moveItems() {
		// Moving bonuses

		for(int i = 0; i < bonuses.size(); i++){
			Bonus currentBonus = bonuses.get(i);

			if(currentBonus.hasToFall()){
				currentBonus.dropDownBonus();
			}
		}

		// Moving Power-Up

		if(this.powerup != null){
			if(this.powerup.hasToFall()){
				this.powerup.dropDownPowerUp();
			}
		}

		// Moving flames
		moveFlames();

		// Moving thunders
		if(this.rightThunder != null && !rightThunder.tryMoveRight()){
			rightThunder = null;
		}

		if(this.leftThunder != null && !leftThunder.tryMoveLeft()){
			leftThunder = null;
		}
	}

	private void moveFlames() {
		for(int i = 0; i < flames.size(); i++){
			Fire currentFlame = flames.get(i);

			// Flame falling
			if(currentFlame.hasToFall()){
				currentFlame.moveDown();
			}else if(currentFlame.getStartTime() > 0
					&& System.currentTimeMillis() >= currentFlame.getStartTime()
							+ Fire.lifeSpan){

				// Removing flames based on time
				flames.remove(i);
				maxFlame += 1;

				if(maxFlame >= 10){
					stopSpreadFlames = false;
					this.flames = new ArrayList<Fire>();
				}
			}
		}

		if(flames.size() > 0
				&& flames.get(0) != null
				&& flames.get(0).isFirstFlame() == true
				&& !flames.get(0).hasToFall()){

			Fire motherFlame = flames.get(0);
			if(motherFlame.getStartTime() == 0){
				motherFlame.startTime();
			}

			if(maxFlame > 0 && stopSpreadFlames == false){

				// Spreading flames
				int randInt = new Random().nextInt(100);

				// At 50% create a flame on the right (if there is room)
				if(randInt < 50
						&& !CollisionManager.isBubbleCollisionBlock(
								new GameObject(mostRightFlameX,
										motherFlame.getY(),
										motherFlame.getHeight(),
										motherFlame.getWidth(),
										null),
								0)
						&& !CollisionManager.isObjectCollidingWithFlames(
								new GameObject(mostRightFlameX,
										motherFlame.getY(),
										motherFlame.getHeight(),
										motherFlame.getWidth(),
										null),
								flames)){

					flames.add(
							new Fire(
									mostRightFlameX,
									motherFlame.getY(),
									true));
					maxFlame -= 1;
					mostRightFlameX += 8;

					// Otherwise, if there is room, try to create a flame on the left
				}else if(!CollisionManager.isBubbleCollisionBlock(
						new GameObject(mostLeftFlameX,
								motherFlame.getY(),
								motherFlame.getHeight(),
								motherFlame.getWidth(),
								null),
						0)
						&& !CollisionManager.isObjectCollidingWithFlames(
								new GameObject(mostLeftFlameX,
										motherFlame.getY(),
										motherFlame.getHeight(),
										motherFlame.getWidth(),
										null),
								flames)){

					flames.add(
							new Fire(
									mostLeftFlameX,
									motherFlame.getY(),
									false));
					maxFlame -= 1;
					mostLeftFlameX -= 8;
				}else{
					// Otherwise stop creating flames
					stopSpreadFlames = true;
				}

			}
		}
	}

	public void extendEnemyEnragementTime(int extendAmount) {
		for(int i = 0; i < enemies.size(); i++){
			Enemy currentEnemy = enemies.get(i);
			currentEnemy
					.setBubblifyDuration(
							currentEnemy.getBubblifyDuration() + extendAmount);
		}
	}

	public void killAllEnemies() {
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).pop();
		}
	}

	// Getter and setter methods -------------------------------------------------

	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	public ArrayList<Bonus> getBonuses() {
		return this.bonuses;
	}

	public PowerUp getPowerUp() {
		return this.powerup;
	}

	public void setPowerup(PowerUp powerup) {
		this.powerup = powerup;
	}

	public void setEnemiesFreezed(boolean enemiesFreezed) {
		this.enemiesFreezed = enemiesFreezed;
	}

	public ArrayList<Fire> getFlames() {
		return flames;
	}

	public Thunder getRightThunder() {
		return rightThunder;
	}

	public Thunder getLeftThunder() {
		return leftThunder;
	}

}
