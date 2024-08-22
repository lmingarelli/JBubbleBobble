package model;

public class Grid {
	public static final int tileDimension = 16;
	public static final int gridDimension = 16;

	protected Block[][] grid = new Block[gridDimension][gridDimension];

	public Grid(String blockImagePath) {
		for(int y = 0; y < gridDimension; y++){
			for(int x = 0; x < gridDimension; x++){
				grid[y][x] = new Block(0, 0, 0, 0, null, true);
			}
		}

		fillColumn(0, blockImagePath);
		fillColumn(1, blockImagePath);
		fillColumn(14, blockImagePath);
		fillColumn(15, blockImagePath);

		fillRow(0, blockImagePath);
		fillRow(15, blockImagePath);
	}

	private void fillColumn(int x, String blockImagePath) {
		for(int y = 0; y < gridDimension; y++){
			grid[y][x] = new Block(x * tileDimension, y * tileDimension,
					tileDimension, tileDimension, blockImagePath, false);
		}
	}

	private void fillRow(int y, String blockImagePath) {
		for(int x = 0; x < gridDimension; x++){
			grid[y][x] = new Block(x * tileDimension, y * tileDimension,
					tileDimension, tileDimension, blockImagePath, false);
		}
	}

	public Block[][] getMatrix() {
		return grid;
	}
}
