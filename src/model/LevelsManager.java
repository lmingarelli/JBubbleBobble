package model;

public abstract class LevelsManager {
	private static final String BLOCK_IMAGE_PATH_1 = "assets/blocks/normal_blocks/block1.png";
	private static final String BLOCK_IMAGE_PATH_2 = "assets/blocks/normal_blocks/block2.png";
	private static final String BLOCK_IMAGE_PATH_3 = "assets/blocks/normal_blocks/block3.png";
	private static final String BLOCK_IMAGE_PATH_4 = "assets/blocks/normal_blocks/block4.png";
	private static final String BLOCK_IMAGE_PATH_5 = "assets/blocks/normal_blocks/block5.png";
	private static final String BLOCK_IMAGE_PATH_6 = "assets/blocks/normal_blocks/block6.png";
	private static final String BLOCK_IMAGE_PATH_7 = "assets/blocks/normal_blocks/block7.png";
	private static final String BLOCK_IMAGE_PATH_8 = "assets/blocks/normal_blocks/block8.png";

	private static int gridDimension = 16;
	private static int tileDimension = 16;

	public static Level getFirstLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_1);

		for(int y = 6; y < 13; y += 3){
			level.grid[2][y] = new Block(2 * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_1, false);

			for(int x = 5; x < 11; x++){
				level.grid[x][y] = new Block(x * tileDimension,
						y * tileDimension, tileDimension, tileDimension,
						BLOCK_IMAGE_PATH_1, false);
			}

			level.grid[13][y] = new Block(13 * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_1, false);
		}

		level.addEnemy(new ZenChan(96, 80, false));
		level.addEnemy(new ZenChan(144, 80, true));
		level.addEnemy(new ZenChan(96, 128, false));
		level.addEnemy(new ZenChan(144, 128, true));

		return level;
	}

	public static Level getSecondLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_2);

		int y = 3;
		for(int x = 6; x < 10; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);
		}

		y = 6;
		for(int x = 4; x < 7; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);

			level.grid[x + 5][y] = new Block((x + 5) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);
		}

		y = 9;
		for(int x = 4; x < 12; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);
		}

		y = 12;
		for(int x = 3; x < 6; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);

			level.grid[x + 7][y] = new Block((x + 7) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);
		}

		for(int x = 7; x < 9; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_2, false);
		}

		level.addEnemy(new ZenChan(112, 32, false));
		level.addEnemy(new ZenChan(128, 32, true));
		level.addEnemy(new ZenChan(80, 80, false));
		level.addEnemy(new ZenChan(160, 80, true));

		return level;
	}

	public static Level getThirdLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_3);

		int x;
		int y = 3;

		for(x = 3; x < 7; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 6][y] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
		}

		x = 3;
		for(y = 4; y < 10; y++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);

			level.grid[x + 9][y] = new Block((x + 9) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
		}

		y = 6;
		for(x = 3; x < 7; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 6][y] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
		}

		y = 9;
		for(x = 3; x < 7; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 6][y] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
		}

		y = 12;
		for(x = 2; x < 4; x++){
			level.grid[x][y] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 3][y] = new Block((x + 3) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 7][y] = new Block((x + 7) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
			level.grid[x + 10][y] = new Block((x + 10) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_3, false);
		}

		level.addEnemy(new ZenChan(80, 32, false));
		level.addEnemy(new ZenChan(80, 80, true));
		level.addEnemy(new ZenChan(80, 128, false));
		level.addEnemy(new ZenChan(160, 32, true));
		level.addEnemy(new ZenChan(160, 80, false));
		level.addEnemy(new ZenChan(160, 128, true));

		return level;
	}

	public static Level getFourthLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_4);

		int x = 4;
		int y = 1;

		level.grid[y][x] = new Block(x * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_4, false);
		level.grid[y][x + 7] = new Block((x + 7) * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_4, false);

		y = 2;
		for(x = 4; x < 6; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 6] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		y = 4;
		for(x = 3; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 6] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		x = 6;
		for(y = 4; y < 8; y++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 3] = new Block((x + 3) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		x = 3;
		for(y = 4; y < 8; y++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 9] = new Block((x + 9) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		y = 7;
		for(x = 3; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 6] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		y = 9;
		for(x = 6; x < 10; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		x = 3;
		for(y = 10; y < 14; y++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 9] = new Block((x + 9) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		y = 10;
		level.grid[y][4] = new Block(4 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_4, false);
		level.grid[y][11] = new Block(11 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_4, false);

		y = 13;
		for(x = 4; x < 6; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 3] = new Block((x + 3) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
			level.grid[y][x + 6] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_4, false);
		}

		level.addEnemy(new ZenChan(96, 48, false));
		level.addEnemy(new ZenChan(144, 48, true));
		level.addEnemy(new ZenChan(64, 96, true));
		level.addEnemy(new ZenChan(176, 96, false));
		level.addEnemy(new Monsta(112, 80, true));
		level.addEnemy(new Monsta(128, 80, false));

		return level;
	}

	public static Level getFifthLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_5);

		int x;
		int y = 4;

		y = 3;
		level.grid[y][3] = new Block(3 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_5, false);
		level.grid[y][12] = new Block(12 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_5, false);

		y = 4;
		for(x = 3; x < 13; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
		}

		y = 7;
		for(x = 4; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
			level.grid[y][x + 5] = new Block((x + 5) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
		}

		y = 9;
		level.grid[y][3] = new Block(3 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_5, false);
		level.grid[y][12] = new Block(12 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_5, false);

		y = 10;
		for(x = 3; x < 13; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
		}

		y = 13;
		for(x = 4; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
			level.grid[y][x + 5] = new Block((x + 5) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_5, false);
		}

		level.addEnemy(new ZenChan(96, 96, true));
		level.addEnemy(new ZenChan(144, 96, false));
		level.addEnemy(new ZenChan(80, 144, true));
		level.addEnemy(new ZenChan(160, 144, false));
		level.addEnemy(new Monsta(80, 48, true));
		level.addEnemy(new Monsta(160, 48, false));

		return level;
	}

	public static Level getSixthLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_6);

		int x;
		int y = 3;

		for(x = 2; x < 11; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_6, false);
		}

		y = 6;
		for(x = 3; x < 14; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_6, false);
		}

		y = 9;
		for(x = 3; x < 13; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_6, false);
		}

		y = 12;
		for(x = 3; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_6, false);
			level.grid[y][x + 6] = new Block((x + 6) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_6, false);
		}

		level.addEnemy(new ZenChan(64, 128, true));
		level.addEnemy(new ZenChan(128, 128, false));
		level.addEnemy(new ZenChan(176, 128, true));
		level.addEnemy(new Monsta(96, 80, true));
		level.addEnemy(new Monsta(160, 80, false));
		level.addEnemy(new Monsta(80, 32, true));
		level.addEnemy(new Monsta(128, 32, false));

		return level;
	}

	public static Level getSeventhLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_7);

		int x;
		int y = 2;

		for(x = 2; x < 4; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
			level.grid[y][x + 10] = new Block((x + 10) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
		}

		y = 5;
		for(x = 2; x < 5; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
			level.grid[y][x + 9] = new Block((x + 9) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
		}

		y = 8;
		for(x = 2; x < 6; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
			level.grid[y][x + 8] = new Block((x + 8) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
		}

		y = 11;
		for(x = 2; x < 7; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
			level.grid[y][x + 7] = new Block((x + 7) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
		}

		y = 13;
		for(x = 4; x < 12; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_7, false);
		}

		level.addEnemy(new Monsta(48, 64, true));
		level.addEnemy(new Monsta(192, 64, true));
		level.addEnemy(new Monsta(48, 160, true));
		level.addEnemy(new Monsta(192, 160, true));
		level.addEnemy(new Pulpul(96, 32));
		level.addEnemy(new Pulpul(144, 32));
		level.addEnemy(new Pulpul(112, 80));
		level.addEnemy(new Pulpul(128, 80));

		return level;
	}

	public static Level getEighthLevel() {
		Level level = new Level(BLOCK_IMAGE_PATH_8);

		int x = 3;
		int y;

		for(y = 2; y < 9; y++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_8, false);
			level.grid[y][x + 9] = new Block((x + 9) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_8, false);
		}

		y = 4;
		level.grid[y][5] = new Block(5 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_8, false);
		level.grid[y][10] = new Block(10 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_8, false);

		y = 7;
		level.grid[y][5] = new Block(5 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_8, false);
		level.grid[y][10] = new Block(10 * tileDimension,
				y * tileDimension, tileDimension, tileDimension,
				BLOCK_IMAGE_PATH_8, false);

		y = 10;
		for(x = 4; x < 12; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_8, false);
		}

		y = 13;
		for(x = 2; x < 6; x++){
			level.grid[y][x] = new Block(x * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_8, false);
			level.grid[y][x + 8] = new Block((x + 8) * tileDimension,
					y * tileDimension, tileDimension, tileDimension,
					BLOCK_IMAGE_PATH_8, false);
		}

		level.addEnemy(new ZenChan(80, 96, true));
		level.addEnemy(new ZenChan(160, 96, false));
		level.addEnemy(new ZenChan(48, 192, true));
		level.addEnemy(new ZenChan(192, 192, false));
		level.addEnemy(new Monsta(80, 48, true));
		level.addEnemy(new Monsta(160, 48, true));
		level.addEnemy(new Pulpul(96, 144));
		level.addEnemy(new Pulpul(144, 144));

		return level;
	}

}
